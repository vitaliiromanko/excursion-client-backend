package com.lpnu.excursionclient.service;

import com.lpnu.excursionclient.dto.request.excursion.*;
import com.lpnu.excursionclient.dto.response.excursion.GetExcursionDetailsResponse;
import com.lpnu.excursionclient.dto.response.excursion.GetExcursionItemResponse;
import com.lpnu.excursionclient.dto.response.excursion.GetFilterResponse;
import com.lpnu.excursionclient.model.excursion.Excursion;
import org.springframework.data.domain.Page;

public interface ExcursionService {
    Page<GetExcursionItemResponse> getSearchExcursions(
            GetSearchExcursionListPage getSearchExcursionListPage,
            GetSearchExcursionListSearchCriteria getSearchExcursionListSearchCriteria
    );

    Page<GetExcursionItemResponse> getFilterExcursions(
            GetFilterExcursionListPage getFilterExcursionListPage,
            GetFilterExcursionListSearchCriteria getFilterExcursionListSearchCriteria
    );

    GetFilterResponse getFilterData();

    GetExcursionDetailsResponse getExcursionDetails(String excursionId);

    String getExcursionFirstPhoto(Excursion excursion);
}
