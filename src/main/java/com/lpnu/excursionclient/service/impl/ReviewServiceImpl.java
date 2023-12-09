package com.lpnu.excursionclient.service.impl;

import com.lpnu.excursionclient.dto.request.review.CreateReviewRequest;
import com.lpnu.excursionclient.dto.request.review.GetExcursionReviewsListPage;
import com.lpnu.excursionclient.dto.request.review.GetExcursionReviewsListSearchCriteria;
import com.lpnu.excursionclient.dto.response.review.CreateReviewResponse;
import com.lpnu.excursionclient.dto.response.review.ExcursionReview;
import com.lpnu.excursionclient.dto.response.review.ReviewClient;
import com.lpnu.excursionclient.enums.review.EReviewStatus;
import com.lpnu.excursionclient.exception.InvalidExcursionIdException;
import com.lpnu.excursionclient.exception.InvalidReviewStatusNameException;
import com.lpnu.excursionclient.model.client.Client;
import com.lpnu.excursionclient.model.excursion.Excursion;
import com.lpnu.excursionclient.model.review.Review;
import com.lpnu.excursionclient.repository.excursion.ExcursionRepository;
import com.lpnu.excursionclient.repository.review.ReviewCriteriaRepository;
import com.lpnu.excursionclient.repository.review.ReviewRepository;
import com.lpnu.excursionclient.repository.review.ReviewStatusRepository;
import com.lpnu.excursionclient.service.ClientService;
import com.lpnu.excursionclient.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.OptionalDouble;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewCriteriaRepository reviewCriteriaRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewStatusRepository reviewStatusRepository;
    private final ExcursionRepository excursionRepository;
    private final ClientService clientService;

    @Override
    public Page<ExcursionReview> getExcursionReviews(
            GetExcursionReviewsListPage getExcursionReviewsListPage,
            GetExcursionReviewsListSearchCriteria getExcursionReviewsListSearchCriteria
    ) {
        return reviewCriteriaRepository.findReviewsByExcursion(
                getExcursionReviewsListPage, getExcursionReviewsListSearchCriteria
        ).map(this::convertToExcursionReview);
    }

    @Override
    public CreateReviewResponse createReview(CreateReviewRequest createReviewRequest, String email) {
        Client client = clientService.checkClientNotActivated(email);

        Excursion excursion = excursionRepository.findById(UUID.fromString(createReviewRequest.excursionId()))
                .orElseThrow(InvalidExcursionIdException::new);

        reviewRepository.save(
                Review.of(
                        createReviewRequest.comment(),
                        createReviewRequest.rating(),
                        reviewStatusRepository.findByName(EReviewStatus.ACTIVE.getName())
                                .orElseThrow(InvalidReviewStatusNameException::new),
                        client,
                        excursion

                )
        );

        excursion.setRating(getNewRating(excursion.getReviews()));

        excursion = excursionRepository.save(excursion);

        return new CreateReviewResponse(
                reviewCriteriaRepository.findReviewsByExcursion(
                        GetExcursionReviewsListPage.of(), GetExcursionReviewsListSearchCriteria.of(excursion.getId())
                ).map(this::convertToExcursionReview),
                getActiveReviewCount(excursion),
                excursion.getRating()
        );
    }

    @Override
    public Integer getActiveReviewCount(Excursion excursion) {
        return excursion.getReviews().stream()
                .filter(review -> review.getReviewStatus().getName().equals(EReviewStatus.ACTIVE.getName()))
                .toList().size();
    }

    private ExcursionReview convertToExcursionReview(Review review) {
        return new ExcursionReview(
                review.getId().toString(),
                review.getComment(),
                convertToReviewClient(review.getClient()),
                review.getRating(),
                review.getCreationDate(),
                review.getReviewStatus().getName()
        );
    }

    private ReviewClient convertToReviewClient(Client client) {
        return new ReviewClient(
                client.getId().toString(),
                client.getFirstName(),
                client.getLastName(),
                client.getClientPhotoData() == null ? null : client.getClientPhotoData().getPhoto()
        );
    }

    private Double getNewRating(Set<Review> reviews) {
        OptionalDouble rating = reviews.stream()
                .filter(r -> r.getReviewStatus().getName().equals(EReviewStatus.ACTIVE.getName()))
                .mapToInt(Review::getRating)
                .average();

        if (rating.isPresent()) {
            return rating.getAsDouble();
        } else {
            return 0.0;
        }
    }
}
