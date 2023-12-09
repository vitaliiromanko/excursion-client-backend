package com.lpnu.excursionclient.dto.request.review;

import com.lpnu.excursionclient.dto.PageDetails;
import lombok.Getter;
import org.springframework.data.domain.Sort;

@Getter
public class GetExcursionReviewsListPage implements PageDetails {
    private final int number;
    private final int size;
    private final Sort.Direction sortDirection;
    private final String sortBy;

    public static GetExcursionReviewsListPage of(GetExcursionReviewsListRequest getExcursionReviewsListRequest) {
        return new GetExcursionReviewsListPage(
                getExcursionReviewsListRequest.getPage_number(),
                getExcursionReviewsListRequest.getPage_size(),
                getExcursionReviewsListRequest.getSort_direction(),
                getExcursionReviewsListRequest.getSort_by()
        );
    }

    public static GetExcursionReviewsListPage of() {
        return new GetExcursionReviewsListPage(0, 10, Sort.Direction.DESC, "creationDate");
    }

    private GetExcursionReviewsListPage(int number, int size, Sort.Direction sortDirection, String sortBy) {
        this.number = number;
        this.size = size;
        this.sortDirection = sortDirection;
        this.sortBy = sortBy;
    }
}
