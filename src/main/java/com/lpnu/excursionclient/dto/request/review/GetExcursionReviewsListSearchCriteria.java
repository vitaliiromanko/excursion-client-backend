package com.lpnu.excursionclient.dto.request.review;

import com.lpnu.excursionclient.exception.InvalidExcursionIdException;
import lombok.Getter;

import java.util.UUID;

@Getter
public class GetExcursionReviewsListSearchCriteria {
    private final UUID excursionId;
    private final String status;

    public static GetExcursionReviewsListSearchCriteria of(GetExcursionReviewsListRequest getExcursionReviewsListRequest) {
        UUID excursionId;
        if (getExcursionReviewsListRequest.getExcursion_id() == null) {
            throw new InvalidExcursionIdException();
        }

        try {
            excursionId = UUID.fromString(getExcursionReviewsListRequest.getExcursion_id());
        } catch (IllegalArgumentException exception) {
            throw new InvalidExcursionIdException();
        }

        return new GetExcursionReviewsListSearchCriteria(excursionId, getExcursionReviewsListRequest.getStatus());
    }

    public static GetExcursionReviewsListSearchCriteria of(UUID excursionId) {
        return new GetExcursionReviewsListSearchCriteria(excursionId, "АКТИВНИЙ");
    }

    private GetExcursionReviewsListSearchCriteria(UUID excursionId, String status) {
        this.excursionId = excursionId;
        this.status = status;
    }
}
