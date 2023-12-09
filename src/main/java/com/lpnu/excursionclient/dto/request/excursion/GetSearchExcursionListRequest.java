package com.lpnu.excursionclient.dto.request.excursion;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@ToString
public class GetSearchExcursionListRequest {
    private Integer page_number = 0;
    private Integer page_size = 12;
    private String sort_by = "rating";
    private Sort.Direction sort_direction = Sort.Direction.DESC;
    private String search;
}
