package com.lpnu.excursionclient.service;

import com.lpnu.excursionclient.dto.request.review.CreateReviewRequest;
import com.lpnu.excursionclient.dto.request.review.GetExcursionReviewsListPage;
import com.lpnu.excursionclient.dto.request.review.GetExcursionReviewsListSearchCriteria;
import com.lpnu.excursionclient.dto.response.review.CreateReviewResponse;
import com.lpnu.excursionclient.dto.response.review.ExcursionReview;
import com.lpnu.excursionclient.model.excursion.Excursion;
import org.springframework.data.domain.Page;

public interface ReviewService {
    Page<ExcursionReview> getExcursionReviews(
            GetExcursionReviewsListPage getExcursionReviewsListPage,
            GetExcursionReviewsListSearchCriteria getExcursionReviewsListSearchCriteria
    );

    CreateReviewResponse createReview(CreateReviewRequest createReviewRequest, String email);

    Integer getActiveReviewCount(Excursion excursion);
}
