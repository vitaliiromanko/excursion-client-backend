package com.lpnu.excursionclient.dto.request.excursion;

import com.lpnu.excursionclient.dto.PageDetails;
import lombok.Getter;
import org.springframework.data.domain.Sort;

@Getter
public class GetFilterExcursionListPage implements PageDetails {
    private final int number;
    private final int size;
    private final Sort.Direction sortDirection;
    private final String sortBy;

    public static GetFilterExcursionListPage of(GetFilterExcursionListRequest getFilterExcursionListRequest) {
        return new GetFilterExcursionListPage(getFilterExcursionListRequest);
    }

    private GetFilterExcursionListPage(GetFilterExcursionListRequest getFilterExcursionListRequest) {
        this.number = getFilterExcursionListRequest.getPage_number();
        this.size = getFilterExcursionListRequest.getPage_size();
        this.sortDirection = getFilterExcursionListRequest.getSort_direction();
        this.sortBy = getFilterExcursionListRequest.getSort_by();
    }
}
