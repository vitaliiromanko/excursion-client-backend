package com.lpnu.excursionclient.dto.request.review;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@ToString
public class GetExcursionReviewsListRequest {
    private Integer page_number = 0;
    private Integer page_size = 10;
    private String sort_by = "creationDate";
    private Sort.Direction sort_direction = Sort.Direction.DESC;

    private String excursion_id;
    private String status;
}
