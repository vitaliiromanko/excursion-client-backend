package com.lpnu.excursionclient.repository.excursion;

import com.lpnu.excursionclient.dto.filter.DurationRequestRange;
import com.lpnu.excursionclient.dto.filter.MaxPeopleNumberRange;
import com.lpnu.excursionclient.dto.filter.PriceRange;
import com.lpnu.excursionclient.dto.filter.StartDateRange;
import com.lpnu.excursionclient.dto.PageDetails;
import com.lpnu.excursionclient.dto.request.excursion.GetFilterExcursionListSearchCriteria;
import com.lpnu.excursionclient.dto.request.excursion.GetSearchExcursionListSearchCriteria;
import com.lpnu.excursionclient.enums.excursion.EExcursionConductingType;
import com.lpnu.excursionclient.enums.excursion.EExcursionDurationUnit;
import com.lpnu.excursionclient.enums.excursion.EExcursionItemStatus;
import com.lpnu.excursionclient.enums.excursion.EExcursionStatus;
import com.lpnu.excursionclient.model.excursion.Excursion;
import com.lpnu.excursionclient.model.excursion.ExcursionItem;
import com.lpnu.excursionclient.model.excursion.ExcursionTopicType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ExcursionCriteriaRepository {
    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public ExcursionCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<Excursion> findAllBySearch(PageDetails pageDetails,
                                           GetSearchExcursionListSearchCriteria getSearchExcursionListSearchCriteria) {
        CriteriaQuery<Excursion> criteriaQuery = criteriaBuilder.createQuery(Excursion.class);
        Root<Excursion> excursionRoot = criteriaQuery.from(Excursion.class);
        Predicate predicate = getFindAllBySearchPredicate(excursionRoot, getSearchExcursionListSearchCriteria, criteriaQuery);
        criteriaQuery.where(predicate);
        setOrder(pageDetails, criteriaQuery, excursionRoot);

        TypedQuery<Excursion> typedQuery = getTypedQuery(criteriaQuery, pageDetails);

        Pageable pageable = getPageable(pageDetails);

        long excursionCount = getFindAllBySearchCount(getSearchExcursionListSearchCriteria);

        return new PageImpl<>(typedQuery.getResultList(), pageable, excursionCount);
    }

    public Page<Excursion> findAllByFilter(PageDetails pageDetails,
                                           GetFilterExcursionListSearchCriteria getFilterExcursionListSearchCriteria) {
        CriteriaQuery<Excursion> criteriaQuery = criteriaBuilder.createQuery(Excursion.class);
        Root<Excursion> excursionRoot = criteriaQuery.from(Excursion.class);
        Predicate predicate = getFindAllByFilterPredicate(excursionRoot, getFilterExcursionListSearchCriteria, criteriaQuery);
        criteriaQuery.where(predicate);
        setOrder(pageDetails, criteriaQuery, excursionRoot);

        TypedQuery<Excursion> typedQuery = getTypedQuery(criteriaQuery, pageDetails);

        Pageable pageable = getPageable(pageDetails);

        long excursionCount = getFindAllByFilterCount(getFilterExcursionListSearchCriteria);

        return new PageImpl<>(typedQuery.getResultList(), pageable, excursionCount);
    }

    private Predicate getFindAllBySearchPredicate(
            Root<Excursion> excursionRoot,
            GetSearchExcursionListSearchCriteria getSearchExcursionListSearchCriteria,
            CriteriaQuery<?> criteriaQuery
    ) {
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(getGeneralPredicate(excursionRoot, criteriaQuery));

        if (Objects.nonNull(getSearchExcursionListSearchCriteria.getSearch())) {
            predicates.add(
                    criteriaBuilder.like(excursionRoot.get("name"),
                            "%" + getSearchExcursionListSearchCriteria.getSearch() + "%")
            );
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private Predicate getFindAllByFilterPredicate(
            Root<Excursion> excursionRoot,
            GetFilterExcursionListSearchCriteria getFilterExcursionListSearchCriteria,
            CriteriaQuery<?> criteriaQuery
    ) {
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(getGeneralPredicate(excursionRoot, criteriaQuery));

        if (Objects.nonNull(getFilterExcursionListSearchCriteria.getCategories())) {
            predicates.add(getFilterCategoryPredicate(excursionRoot, getFilterExcursionListSearchCriteria.getCategories()));
        }

        if (Objects.nonNull(getFilterExcursionListSearchCriteria.getPriceRange())) {
            predicates.add(getFilterPriceRangePredicate(excursionRoot, getFilterExcursionListSearchCriteria.getPriceRange()));
        }

        if (Objects.nonNull(getFilterExcursionListSearchCriteria.getDurationRequestRange())) {
            predicates.add(getFilterDurationRangePredicate(excursionRoot, getFilterExcursionListSearchCriteria.getDurationRequestRange()));
        }

        if (Objects.nonNull(getFilterExcursionListSearchCriteria.getMovementTypes())) {
            predicates.add(getFilterMovementTypePredicate(excursionRoot, getFilterExcursionListSearchCriteria.getMovementTypes()));
        }

        if (Objects.nonNull(getFilterExcursionListSearchCriteria.getTopicTypes())) {
            predicates.add(getFilterTopicTypePredicate(excursionRoot, getFilterExcursionListSearchCriteria.getTopicTypes(), criteriaQuery));
        }

        if (Objects.nonNull(getFilterExcursionListSearchCriteria.getConductingTypes())) {
            predicates.add(getFilterConductingTypePredicate(excursionRoot, getFilterExcursionListSearchCriteria.getConductingTypes()));
        }

        if (Objects.nonNull(getFilterExcursionListSearchCriteria.getMaxPeopleNumberRange())) {
            predicates.add(getFilterMaxPeopleNumberRangePredicate(excursionRoot, getFilterExcursionListSearchCriteria.getMaxPeopleNumberRange()));
        }

        if (Objects.nonNull(getFilterExcursionListSearchCriteria.getStartDateRange())) {
            predicates.add(getFilterStartDateRangeOrIndividualPredicate(excursionRoot, getFilterExcursionListSearchCriteria.getStartDateRange()));
        }

        if (Objects.nonNull(getFilterExcursionListSearchCriteria.getOrganizers())) {
            predicates.add(getFilterOrganizerPredicate(excursionRoot, getFilterExcursionListSearchCriteria.getOrganizers()));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private Predicate getGeneralPredicate(Root<Excursion> excursionRoot, CriteriaQuery<?> criteriaQuery) {
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(criteriaBuilder.equal(excursionRoot.get("excursionStatus").get("name"),
                EExcursionStatus.ACTIVE.getName()));
        predicates.add(criteriaBuilder.equal(excursionRoot.get("organizerBlocked"), false));
        predicates.add(criteriaBuilder.equal(excursionRoot.get("allowedShow"), true));
        predicates.add(getGeneralExcursionConductingPredicate(excursionRoot, criteriaQuery));

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private Predicate getGeneralExcursionConductingPredicate(Root<Excursion> excursionRoot, CriteriaQuery<?> criteriaQuery) {
        List<Predicate> excursionItemPredicates = new ArrayList<>();

        excursionItemPredicates.add(criteriaBuilder.equal(excursionRoot.get("excursionConductingType").get("name"),
                EExcursionConductingType.INDIVIDUAL.getName()));
        excursionItemPredicates.add(getGeneralExcursionPlannedConductingPredicate(excursionRoot, criteriaQuery));

        return criteriaBuilder.or(excursionItemPredicates.toArray(new Predicate[0]));
    }

    private Predicate getGeneralExcursionPlannedConductingPredicate(Root<Excursion> excursionRoot, CriteriaQuery<?> criteriaQuery) {
        List<Predicate> plannedExcursionPredicate = new ArrayList<>();

        plannedExcursionPredicate.add(criteriaBuilder.equal(excursionRoot.get("excursionConductingType").get("name"),
                EExcursionConductingType.PLANNED.getName()));

        Subquery<UUID> subquery = criteriaQuery.subquery(UUID.class);
        Root<Excursion> subExcursion = subquery.from(Excursion.class);

        Join<Excursion, ExcursionItem> join = subExcursion.join("excursionItems");
        subquery.select(subExcursion.get("id")).where(criteriaBuilder.equal(join.get("excursionItemStatus").get("name"),
                EExcursionItemStatus.RECRUITMENT_OF_PARTICIPANTS.getName()));
        plannedExcursionPredicate.add(criteriaBuilder.in(excursionRoot.get("id")).value(subquery));

        return criteriaBuilder.and(plannedExcursionPredicate.toArray(new Predicate[0]));
    }

    private Predicate getFilterCategoryPredicate(Root<Excursion> excursionRoot, Set<String> categories) {
        List<Predicate> predicates = new ArrayList<>();

        for (String category : categories) {
            Expression<String> categoryUuid = criteriaBuilder.function("BIN_TO_UUID", String.class,
                    excursionRoot.get("category").get("id"));

            predicates.add(criteriaBuilder.equal(categoryUuid, category));
        }

        return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
    }

    private Predicate getFilterPriceRangePredicate(Root<Excursion> excursionRoot, PriceRange priceRange) {
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(criteriaBuilder.greaterThanOrEqualTo(excursionRoot.get("activePrice"), priceRange.start()));

        predicates.add(criteriaBuilder.lessThanOrEqualTo(excursionRoot.get("activePrice"), priceRange.end()));

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private Predicate getFilterDurationRangePredicate(Root<Excursion> excursionRoot, DurationRequestRange durationRequestRange) {
        List<Predicate> predicates = new ArrayList<>();

        if (durationRequestRange.start().unit().equals(
                EExcursionDurationUnit.HOURS.getName()) && durationRequestRange.end().unit().equals(
                EExcursionDurationUnit.HOURS.getName())) {
            predicates.add(criteriaBuilder.equal(excursionRoot.get("excursionDurationUnit").get("name"),
                    EExcursionDurationUnit.HOURS.getName()));

            predicates.add(criteriaBuilder.greaterThanOrEqualTo(excursionRoot.get("duration"), durationRequestRange.start().duration()));

            predicates.add(criteriaBuilder.lessThanOrEqualTo(excursionRoot.get("duration"), durationRequestRange.end().duration()));
        } else if (durationRequestRange.start().unit().equals(
                EExcursionDurationUnit.DAYS.getName()) && durationRequestRange.end().unit().equals(
                EExcursionDurationUnit.DAYS.getName())) {
            predicates.add(criteriaBuilder.equal(excursionRoot.get("excursionDurationUnit").get("name"),
                    EExcursionDurationUnit.DAYS.getName()));

            predicates.add(criteriaBuilder.greaterThanOrEqualTo(excursionRoot.get("duration"), durationRequestRange.start().duration()));

            predicates.add(criteriaBuilder.lessThanOrEqualTo(excursionRoot.get("duration"), durationRequestRange.end().duration()));
        } else if (durationRequestRange.start().unit().equals(
                EExcursionDurationUnit.HOURS.getName()) && durationRequestRange.end().unit().equals(
                EExcursionDurationUnit.DAYS.getName())) {
            Expression<Double> firstExcursion = criteriaBuilder.selectCase()
                    .when(criteriaBuilder.equal(excursionRoot.get("excursionDurationUnit").get("name"),
                                    EExcursionDurationUnit.HOURS.getName()),
                            excursionRoot.get("duration"))
                    .when(criteriaBuilder.equal(excursionRoot.get("excursionDurationUnit").get("name"),
                                    EExcursionDurationUnit.DAYS.getName()),
                            criteriaBuilder.prod(excursionRoot.get("duration"), 24))
                    .as(Double.class);
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(firstExcursion, durationRequestRange.start().duration()));

            Expression<Double> secondExpression = criteriaBuilder.selectCase()
                    .when(criteriaBuilder.equal(excursionRoot.get("excursionDurationUnit").get("name"),
                                    EExcursionDurationUnit.HOURS.getName()),
                            criteriaBuilder.quot(excursionRoot.get("duration"), 24))
                    .when(criteriaBuilder.equal(excursionRoot.get("excursionDurationUnit").get("name"),
                                    EExcursionDurationUnit.DAYS.getName()),
                            excursionRoot.get("duration"))
                    .as(Double.class);
            predicates.add(criteriaBuilder.lessThanOrEqualTo(secondExpression, durationRequestRange.end().duration()));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private Predicate getFilterMovementTypePredicate(Root<Excursion> excursionRoot, Set<String> movementTypes) {
        List<Predicate> predicates = new ArrayList<>();

        for (String movementType : movementTypes) {
            Expression<String> movementTypeUuid = criteriaBuilder.function("BIN_TO_UUID", String.class,
                    excursionRoot.get("excursionMovementType").get("id"));
            predicates.add(criteriaBuilder.equal(movementTypeUuid, movementType));
        }

        return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
    }

    private Predicate getFilterTopicTypePredicate(
            Root<Excursion> excursionRoot,
            Set<String> topicTypes,
            CriteriaQuery<?> criteriaQuery
    ) {
        List<Predicate> predicates = new ArrayList<>();


        for (String topicType : topicTypes) {
            Subquery<UUID> subquery = criteriaQuery.subquery(UUID.class);
            Root<Excursion> subExcursion = subquery.from(Excursion.class);
            Join<ExcursionTopicType, Excursion> subExcursionTopicType = subExcursion.join("excursionTopicTypes");

            Expression<String> topicTypeUuid = criteriaBuilder.function("BIN_TO_UUID", String.class,
                    subExcursionTopicType.get("id"));
            subquery.select(subExcursion.get("id")).where(criteriaBuilder.equal(topicTypeUuid, topicType));

            predicates.add(criteriaBuilder.in(excursionRoot.get("id")).value(subquery));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private Predicate getFilterConductingTypePredicate(Root<Excursion> excursionRoot, Set<String> conductingTypes) {
        List<Predicate> predicates = new ArrayList<>();

        for (String conductingType : conductingTypes) {
            Expression<String> conductingTypeUuid = criteriaBuilder.function("BIN_TO_UUID", String.class,
                    excursionRoot.get("excursionConductingType").get("id"));
            predicates.add(criteriaBuilder.equal(conductingTypeUuid, conductingType));
        }

        return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
    }

    private Predicate getFilterMaxPeopleNumberRangePredicate(Root<Excursion> excursionRoot, MaxPeopleNumberRange maxPeopleNumberRange) {
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(criteriaBuilder.isNotNull(excursionRoot.get("maxPeopleNumber")));

        predicates.add(criteriaBuilder.greaterThanOrEqualTo(excursionRoot.get("maxPeopleNumber"), maxPeopleNumberRange.start()));

        predicates.add(criteriaBuilder.lessThanOrEqualTo(excursionRoot.get("maxPeopleNumber"), maxPeopleNumberRange.end()));

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private Predicate getFilterStartDateRangeOrIndividualPredicate(Root<Excursion> excursionRoot, StartDateRange startDateRange) {
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(excursionRoot.get("excursionConductingType").get("name"),
                EExcursionConductingType.INDIVIDUAL.getName()));
        predicates.add(getFilterStartDateRangePredicate(excursionRoot, startDateRange));
        return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
    }

    private Predicate getFilterStartDateRangePredicate(Root<Excursion> excursionRoot, StartDateRange startDateRange) {
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(criteriaBuilder.equal(excursionRoot.get("excursionConductingType").get("name"),
                EExcursionConductingType.PLANNED.getName()));

        Join<Excursion, ExcursionItem> join = excursionRoot.join("excursionItems");

        predicates.add(criteriaBuilder.isNotNull(join.get("startDate")));

        predicates.add(criteriaBuilder.greaterThanOrEqualTo(join.get("startDate"), startDateRange.start()));

        predicates.add(criteriaBuilder.lessThanOrEqualTo(join.get("startDate"), startDateRange.end()));

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private Predicate getFilterOrganizerPredicate(Root<Excursion> excursionRoot, Set<String> organizers) {
        List<Predicate> predicates = new ArrayList<>();

        for (String organizer : organizers) {
            Expression<String> organizerUuid = criteriaBuilder.function("BIN_TO_UUID", String.class,
                    excursionRoot.get("organizer").get("id"));

            predicates.add(criteriaBuilder.equal(organizerUuid, organizer));
        }

        return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(
            PageDetails pageDetails,
            CriteriaQuery<Excursion> criteriaQuery,
            Root<Excursion> excursionRoot
    ) {
        if (!pageDetails.getSortBy().equals("duration")) {
            if (pageDetails.getSortDirection().isAscending()) {
                criteriaQuery.orderBy(
                        criteriaBuilder.asc(excursionRoot.get(pageDetails.getSortBy())),
                        criteriaBuilder.asc(excursionRoot.get("name"))
                );
            } else {
                criteriaQuery.orderBy(
                        criteriaBuilder.desc(excursionRoot.get(pageDetails.getSortBy())),
                        criteriaBuilder.asc(excursionRoot.get("name"))
                );
            }
        } else {
            if (pageDetails.getSortDirection().isAscending()) {
                criteriaQuery.orderBy(
                        criteriaBuilder.asc(excursionRoot.get("excursionDurationUnit").get("name")),
                        criteriaBuilder.asc(excursionRoot.get(pageDetails.getSortBy())),
                        criteriaBuilder.asc(excursionRoot.get("name"))
                );
            } else {
                criteriaQuery.orderBy(
                        criteriaBuilder.desc(excursionRoot.get("excursionDurationUnit").get("name")),
                        criteriaBuilder.desc(excursionRoot.get(pageDetails.getSortBy())),
                        criteriaBuilder.asc(excursionRoot.get("name"))
                );
            }
        }
    }

    private TypedQuery<Excursion> getTypedQuery(CriteriaQuery<Excursion> criteriaQuery, PageDetails pageDetails) {
        TypedQuery<Excursion> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(pageDetails.getNumber() * pageDetails.getSize());
        typedQuery.setMaxResults(pageDetails.getSize());
        return typedQuery;
    }

    private Pageable getPageable(PageDetails pageDetails) {
        List<Sort.Order> orders = new ArrayList<>();

        if (pageDetails.getSortBy().equals("duration")) {
            orders.add(new Sort.Order(pageDetails.getSortDirection(), "excursionDurationUnit.name"));
        }

        orders.add(new Sort.Order(pageDetails.getSortDirection(), pageDetails.getSortBy()));
        orders.add(new Sort.Order(Sort.Direction.ASC, "name"));

        Sort sort = Sort.by(orders);
        return PageRequest.of(pageDetails.getNumber(), pageDetails.getSize(), sort);
    }

    private long getFindAllBySearchCount(GetSearchExcursionListSearchCriteria getSearchExcursionListSearchCriteria) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Excursion> countRoot = countQuery.from(Excursion.class);
        Predicate predicate = getFindAllBySearchPredicate(countRoot, getSearchExcursionListSearchCriteria, countQuery);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }

    private long getFindAllByFilterCount(GetFilterExcursionListSearchCriteria getFilterExcursionListSearchCriteria) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Excursion> countRoot = countQuery.from(Excursion.class);
        Predicate predicate = getFindAllByFilterPredicate(countRoot, getFilterExcursionListSearchCriteria, countQuery);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
