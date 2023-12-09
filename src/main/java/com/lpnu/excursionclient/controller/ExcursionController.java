package com.lpnu.excursionclient.controller;

import com.lpnu.excursionclient.dto.request.excursion.*;
import com.lpnu.excursionclient.dto.response.excursion.GetExcursionDetailsResponse;
import com.lpnu.excursionclient.dto.response.excursion.GetFilterExcursionListResponse;
import com.lpnu.excursionclient.dto.response.excursion.GetFilterResponse;
import com.lpnu.excursionclient.dto.response.excursion.GetSearchExcursionListResponse;
import com.lpnu.excursionclient.service.ExcursionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/excursion")
@RequiredArgsConstructor
public class ExcursionController {
    private final ExcursionService excursionService;

    @GetMapping("/search")
    public ResponseEntity<GetSearchExcursionListResponse> getSearchExcursions(
            GetSearchExcursionListRequest getSearchExcursionListRequest
    ) {
        return ResponseEntity.ok().body(new GetSearchExcursionListResponse(
                excursionService.getSearchExcursions(
                        GetSearchExcursionListPage.of(getSearchExcursionListRequest),
                        GetSearchExcursionListSearchCriteria.of(getSearchExcursionListRequest)
                )));
    }

    @GetMapping("/filter")
    public ResponseEntity<GetFilterExcursionListResponse> getFilterExcursions(
            GetFilterExcursionListRequest getFilterExcursionListRequest
    ) {
        return ResponseEntity.ok().body(new GetFilterExcursionListResponse(
                excursionService.getFilterExcursions(
                        GetFilterExcursionListPage.of(getFilterExcursionListRequest),
                        GetFilterExcursionListSearchCriteria.of(getFilterExcursionListRequest)
                )));
    }

    @GetMapping("/filter-data")
    public ResponseEntity<GetFilterResponse> getFilterData() {
        return ResponseEntity.ok().body(excursionService.getFilterData());
    }

    @GetMapping("/details")
    public ResponseEntity<GetExcursionDetailsResponse> getExcursionDetails(
            @RequestParam("excursion_id") String excursionId
    ) {
        return ResponseEntity.ok().body(excursionService.getExcursionDetails(excursionId));
    }
}
