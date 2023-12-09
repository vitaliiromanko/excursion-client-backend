package com.lpnu.excursionclient.service;

import com.lpnu.excursionclient.dto.response.site.GetSiteCarouselExcursionItemResponse;

import java.util.List;

public interface SiteService {
    List<GetSiteCarouselExcursionItemResponse> getSiteCarouselExcursions();
}
