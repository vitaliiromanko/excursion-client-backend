package com.lpnu.excursionclient.service.impl;

import com.lpnu.excursionclient.dto.filter.*;
import com.lpnu.excursionclient.dto.request.excursion.GetFilterExcursionListPage;
import com.lpnu.excursionclient.dto.request.excursion.GetFilterExcursionListSearchCriteria;
import com.lpnu.excursionclient.dto.request.excursion.GetSearchExcursionListPage;
import com.lpnu.excursionclient.dto.request.excursion.GetSearchExcursionListSearchCriteria;
import com.lpnu.excursionclient.dto.response.excursion.GetExcursionDetailsResponse;
import com.lpnu.excursionclient.dto.response.excursion.GetExcursionItemResponse;
import com.lpnu.excursionclient.dto.response.excursion.GetFilterResponse;
import com.lpnu.excursionclient.dto.response.excursion.details.*;
import com.lpnu.excursionclient.enums.excursion.EExcursionConductingType;
import com.lpnu.excursionclient.enums.excursion.EExcursionItemStatus;
import com.lpnu.excursionclient.exception.ExcursionNotAllowedToShowException;
import com.lpnu.excursionclient.exception.ExcursionOrganizerBlockedException;
import com.lpnu.excursionclient.exception.InvalidExcursionIdException;
import com.lpnu.excursionclient.exception.InvalidExcursionPhotosException;
import com.lpnu.excursionclient.model.category.Category;
import com.lpnu.excursionclient.model.excursion.*;
import com.lpnu.excursionclient.model.organizer.Organizer;
import com.lpnu.excursionclient.model.organizer.OrganizerContactPerson;
import com.lpnu.excursionclient.model.organizer.OrganizerContactPhoneNumber;
import com.lpnu.excursionclient.repository.category.CategoryRepository;
import com.lpnu.excursionclient.repository.excursion.*;
import com.lpnu.excursionclient.repository.organizer.OrganizerRepository;
import com.lpnu.excursionclient.service.ExcursionService;
import com.lpnu.excursionclient.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.Collator;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExcursionServiceImpl implements ExcursionService {
    private final ExcursionRepository excursionRepository;
    private final ExcursionMovementTypeRepository excursionMovementTypeRepository;
    private final ExcursionTopicTypeRepository excursionTopicTypeRepository;
    private final ExcursionConductingTypeRepository excursionConductingTypeRepository;
    private final ExcursionItemRepository excursionItemRepository;
    private final OrganizerRepository organizerRepository;
    private final CategoryRepository categoryRepository;
    private final ExcursionCriteriaRepository excursionCriteriaRepository;
    private final ReviewService reviewService;

    private final Collator COLLATOR = Collator.getInstance(Locale.forLanguageTag("uk"));

    @Override
    public Page<GetExcursionItemResponse> getSearchExcursions(
            GetSearchExcursionListPage getSearchExcursionListPage,
            GetSearchExcursionListSearchCriteria getSearchExcursionListSearchCriteria
    ) {
        return excursionCriteriaRepository.findAllBySearch(
                getSearchExcursionListPage, getSearchExcursionListSearchCriteria
        ).map(this::convertToGetExcursionItemResponse);
    }

    @Override
    public Page<GetExcursionItemResponse> getFilterExcursions(
            GetFilterExcursionListPage getFilterExcursionListPage,
            GetFilterExcursionListSearchCriteria getFilterExcursionListSearchCriteria
    ) {
        return excursionCriteriaRepository.findAllByFilter(
                getFilterExcursionListPage, getFilterExcursionListSearchCriteria
        ).map(this::convertToGetExcursionItemResponse);
    }

    @Override
    public GetFilterResponse getFilterData() {
        List<Category> categories = categoryRepository.getFilterCategoryList();
        List<ExcursionMovementType> movementTypes = excursionMovementTypeRepository.findAll();
        List<ExcursionTopicType> topicTypes = excursionTopicTypeRepository.findAll();
        List<ExcursionConductingType> conductingTypes = excursionConductingTypeRepository.findAll();
        List<Organizer> organizers = organizerRepository.getFilterOrganizerList();

        return new GetFilterResponse(
                categories.isEmpty() ? Collections.emptyList() : categories.stream()
                        .sorted(Comparator.comparing(Category::getName, COLLATOR))
                        .map(this::convertToCategoryItem)
                        .collect(Collectors.toList()),
                new PriceRange(
                        excursionRepository.minActivePrice() == null ? BigDecimal.ZERO : excursionRepository.minActivePrice(),
                        excursionRepository.maxActivePrice() == null ? BigDecimal.ZERO : excursionRepository.maxActivePrice()
                ),
                new DurationResponseRange(
                        new DurationResponseItem(
                                excursionRepository.minHourDuration() == null ? 0.0 : excursionRepository.minHourDuration(),
                                excursionRepository.maxHourDuration() == null ? 0.0 : excursionRepository.maxHourDuration()
                        ),
                        new DurationResponseItem(
                                excursionRepository.minDayDuration() == null ? 0.0 : excursionRepository.minDayDuration(),
                                excursionRepository.maxDayDuration() == null ? 0.0 : excursionRepository.maxDayDuration()
                        )
                ),
                movementTypes.isEmpty() ? Collections.emptyList() : movementTypes.stream()
                        .sorted(Comparator.comparing(ExcursionMovementType::getName, COLLATOR))
                        .map(this::convertToMovementTypeItem)
                        .collect(Collectors.toList()),
                topicTypes.isEmpty() ? Collections.emptyList() : topicTypes.stream()
                        .sorted(Comparator.comparing(ExcursionTopicType::getName, COLLATOR))
                        .map(this::convertToTopicTypeItem)
                        .collect(Collectors.toList()),
                conductingTypes.isEmpty() ? Collections.emptyList() : conductingTypes.stream()
                        .sorted(Comparator.comparing(ExcursionConductingType::getName, COLLATOR))
                        .map(this::convertToConductingTypeItem)
                        .collect(Collectors.toList()),
                new MaxPeopleNumberRange(
                        excursionRepository.minMaxPeopleNumber() == null ? 0 : excursionRepository.minMaxPeopleNumber(),
                        excursionRepository.maxMaxPeopleNumber() == null ? 0 : excursionRepository.maxMaxPeopleNumber()
                ),
                new StartDateRange(
                        excursionItemRepository.minStartDate() == null ? LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES) : excursionItemRepository.minStartDate(),
                        excursionItemRepository.maxStartDate() == null ? LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES) : excursionItemRepository.maxStartDate()
                ),
                organizers.isEmpty() ? Collections.emptyList() : organizers.stream()
                        .sorted(Comparator.comparing(Organizer::getName, COLLATOR))
                        .map(this::convertToOrganizerItem)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public GetExcursionDetailsResponse getExcursionDetails(String excursionId) {
        Excursion excursion = excursionRepository.findById(UUID.fromString(excursionId))
                .orElseThrow(InvalidExcursionIdException::new);

        if (excursion.getOrganizerBlocked()) {
            throw new ExcursionOrganizerBlockedException();
        }

        if (!excursion.getAllowedShow()) {
            throw new ExcursionNotAllowedToShowException();
        }

        return new GetExcursionDetailsResponse(
                excursion.getId().toString(),
                excursion.getName(),
                excursion.getDescription(),
                excursion.getPrice(),
                excursion.getDiscount(),
                getSortedExcursionPhotos(excursion),
                excursion.getExcursionStatus().getName(),
                excursion.getDuration(),
                excursion.getExcursionDurationUnit().getName(),
                convertToMovementType(excursion.getExcursionMovementType()),
                getExcursionTopicTypes(excursion),
                convertToConductingType(excursion.getExcursionConductingType()),
                excursion.getMaxPeopleNumber(),
                excursion.getRating(),
                getCreatedExcursionItemList(excursion),
                reviewService.getActiveReviewCount(excursion),
                convertToExcursionCategory(excursion.getCategory()),
                convertToExcursionOrganizer(excursion.getOrganizer())
        );
    }

    @Override
    public String getExcursionFirstPhoto(Excursion excursion) {
        return excursion.getExcursionPhotoDataSet().stream()
                .min(Comparator.comparing(ExcursionPhotoData::getOrderPhoto))
                .orElseThrow(InvalidExcursionPhotosException::new)
                .getPhoto();
    }

    private GetExcursionItemResponse convertToGetExcursionItemResponse(Excursion excursion) {
        return new GetExcursionItemResponse(
                excursion.getId().toString(),
                excursion.getName(),
                excursion.getDescription(),
                excursion.getPrice(),
                excursion.getDiscount(),
                getExcursionFirstPhoto(excursion),
                excursion.getRating(),
                reviewService.getActiveReviewCount(excursion),
                excursion.getMaxPeopleNumber(),
                excursion.getDuration(),
                excursion.getExcursionDurationUnit().getName()
        );
    }

    private CategoryItem convertToCategoryItem(Category category) {
        return new CategoryItem(category.getId().toString(), category.getName());
    }

    private MovementTypeItem convertToMovementTypeItem(ExcursionMovementType excursionMovementType) {
        return new MovementTypeItem(excursionMovementType.getId().toString(), excursionMovementType.getName());
    }

    private TopicTypeItem convertToTopicTypeItem(ExcursionTopicType excursionTopicType) {
        return new TopicTypeItem(excursionTopicType.getId().toString(), excursionTopicType.getName());
    }

    private ConductingTypeItem convertToConductingTypeItem(ExcursionConductingType excursionConductingType) {
        return new ConductingTypeItem(excursionConductingType.getId().toString(), excursionConductingType.getName());
    }

    private OrganizerItem convertToOrganizerItem(Organizer organizer) {
        return new OrganizerItem(organizer.getId().toString(), organizer.getName());
    }

    private List<String> getSortedExcursionPhotos(Excursion excursion) {
        return excursion.getExcursionPhotoDataSet().stream()
                .sorted(Comparator.comparing(ExcursionPhotoData::getOrderPhoto))
                .map(ExcursionPhotoData::getPhoto)
                .collect(Collectors.toList());
    }

    private MovementType convertToMovementType(ExcursionMovementType excursionMovementType) {
        return new MovementType(
                excursionMovementType.getId().toString(),
                excursionMovementType.getName()
        );
    }

    private List<TopicType> getExcursionTopicTypes(Excursion excursion) {
        return excursion.getExcursionTopicTypes().stream()
                .sorted(Comparator.comparing(ExcursionTopicType::getName, COLLATOR))
                .map(this::convertToTopicType)
                .collect(Collectors.toList());
    }

    private TopicType convertToTopicType(ExcursionTopicType excursionTopicType) {
        return new TopicType(
                excursionTopicType.getId().toString(),
                excursionTopicType.getName()
        );
    }

    private ConductingType convertToConductingType(ExcursionConductingType excursionConductingType) {
        return new ConductingType(
                excursionConductingType.getId().toString(),
                excursionConductingType.getName()
        );
    }

    private List<CreatedExcursionItem> getCreatedExcursionItemList(Excursion excursion) {
        if (excursion.getExcursionConductingType().getName().equals(EExcursionConductingType.PLANNED.getName())) {
            return excursion.getExcursionItems().stream()
                    .filter(excursionItem -> excursionItem.getExcursionItemStatus().getName().equals(
                            EExcursionItemStatus.RECRUITMENT_OF_PARTICIPANTS.getName()))
                    .sorted(Comparator.comparing(ExcursionItem::getStartDate))
                    .map(this::convertToCreatedExcursionItem)
                    .collect(Collectors.toList());
        } else {
            return null;
        }
    }

    private CreatedExcursionItem convertToCreatedExcursionItem(ExcursionItem excursionItem) {
        return new CreatedExcursionItem(
                excursionItem.getId().toString(),
                excursionItem.getStartDate()
        );
    }

    private ExcursionCategory convertToExcursionCategory(Category category) {
        return new ExcursionCategory(
                category.getId().toString(),
                category.getName()
        );
    }

    private ExcursionOrganizer convertToExcursionOrganizer(Organizer organizer) {
        return new ExcursionOrganizer(
                organizer.getId().toString(),
                organizer.getName(),
                organizer.getOrganizerLogoData() == null ? null : organizer.getOrganizerLogoData().getLogo(),
                organizer.getOrganizerContactPersonSet().stream()
                        .sorted(Comparator.comparing(OrganizerContactPerson::getFullName, COLLATOR))
                        .map(this::convertToExcursionOrganizerContactPerson)
                        .collect(Collectors.toList())
        );
    }

    private ExcursionOrganizerContactPerson convertToExcursionOrganizerContactPerson(
            OrganizerContactPerson organizerContactPerson
    ) {
        return new ExcursionOrganizerContactPerson(
                organizerContactPerson.getId().toString(),
                organizerContactPerson.getFirstName(),
                organizerContactPerson.getLastName(),
                organizerContactPerson.getEmail(),
                organizerContactPerson.getOrganizerContactPhoneNumbers().stream()
                        .map(OrganizerContactPhoneNumber::getPhoneNumber)
                        .collect(Collectors.toList())
        );
    }
}
