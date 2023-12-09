package com.lpnu.excursionclient.controller;

import com.lpnu.excursionclient.dto.response.site.GetSiteCarouselExcursionListResponse;
import com.lpnu.excursionclient.service.SiteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/site")
@RequiredArgsConstructor
public class SiteController {
    private final SiteService siteService;

    @GetMapping("/carousel-excursions")
    public ResponseEntity<GetSiteCarouselExcursionListResponse> getSiteCarouselExcursions() {
        return ResponseEntity.ok().body(new GetSiteCarouselExcursionListResponse(
                siteService.getSiteCarouselExcursions()
        ));
    }
}
