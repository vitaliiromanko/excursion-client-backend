package com.lpnu.excursionclient.dto.request.excursion;

import com.lpnu.excursionclient.dto.PageDetails;
import lombok.Getter;
import org.springframework.data.domain.Sort;

@Getter
public class GetSearchExcursionListPage implements PageDetails {
    private final int number;
    private final int size;
    private final Sort.Direction sortDirection;
    private final String sortBy;

    public static GetSearchExcursionListPage of(GetSearchExcursionListRequest getSearchExcursionListRequest) {
        return new GetSearchExcursionListPage(getSearchExcursionListRequest);
    }

    private GetSearchExcursionListPage(GetSearchExcursionListRequest getSearchExcursionListRequest) {
        this.number = getSearchExcursionListRequest.getPage_number();
        this.size = getSearchExcursionListRequest.getPage_size();
        this.sortDirection = getSearchExcursionListRequest.getSort_direction();
        this.sortBy = getSearchExcursionListRequest.getSort_by();
    }
}
