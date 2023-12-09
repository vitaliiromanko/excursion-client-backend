package com.lpnu.excursionclient.controller;

import com.lpnu.excursionclient.dto.request.review.CreateReviewRequest;
import com.lpnu.excursionclient.dto.request.review.GetExcursionReviewsListPage;
import com.lpnu.excursionclient.dto.request.review.GetExcursionReviewsListRequest;
import com.lpnu.excursionclient.dto.request.review.GetExcursionReviewsListSearchCriteria;
import com.lpnu.excursionclient.dto.response.review.CreateReviewResponse;
import com.lpnu.excursionclient.dto.response.review.GetExcursionReviewsListResponse;
import com.lpnu.excursionclient.service.ReviewService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity<GetExcursionReviewsListResponse> getExcursionReviews(
            GetExcursionReviewsListRequest getExcursionReviewsListRequest
    ) {
        return ResponseEntity.ok().body(new GetExcursionReviewsListResponse(
                reviewService.getExcursionReviews(
                        GetExcursionReviewsListPage.of(getExcursionReviewsListRequest),
                        GetExcursionReviewsListSearchCriteria.of(getExcursionReviewsListRequest)
                )));
    }

    @PostMapping
    public ResponseEntity<CreateReviewResponse> createReview(
            @Valid @RequestBody CreateReviewRequest createReviewRequest,
            Authentication authentication
    ) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/review").toUriString());
        return ResponseEntity.created(uri).body(
                reviewService.createReview(
                        createReviewRequest,
                        (String) authentication.getPrincipal()
                )
        );
    }
}
