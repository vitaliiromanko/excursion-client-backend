package com.lpnu.excursionclient.dto.request.excursion;

import lombok.Getter;

@Getter
public class GetSearchExcursionListSearchCriteria {
    private final String search;

    public static GetSearchExcursionListSearchCriteria of(GetSearchExcursionListRequest getSearchExcursionListRequest) {
        return new GetSearchExcursionListSearchCriteria(getSearchExcursionListRequest);
    }

    private GetSearchExcursionListSearchCriteria(GetSearchExcursionListRequest getSearchExcursionListRequest) {
        this.search = getSearchExcursionListRequest.getSearch();
    }
}
