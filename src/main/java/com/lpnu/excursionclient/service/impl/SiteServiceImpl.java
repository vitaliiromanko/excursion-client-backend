package com.lpnu.excursionclient.service.impl;

import com.lpnu.excursionclient.dto.response.site.GetSiteCarouselExcursionItemResponse;
import com.lpnu.excursionclient.exception.InvalidSiteCarouselExcursionPhotoOrderException;
import com.lpnu.excursionclient.model.excursion.Excursion;
import com.lpnu.excursionclient.model.excursion.ExcursionPhotoData;
import com.lpnu.excursionclient.model.site.SiteCarouselExcursion;
import com.lpnu.excursionclient.repository.site.SiteCarouselExcursionRepository;
import com.lpnu.excursionclient.service.SiteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SiteServiceImpl implements SiteService {
    private final SiteCarouselExcursionRepository siteCarouselExcursionRepository;

    @Override
    public List<GetSiteCarouselExcursionItemResponse> getSiteCarouselExcursions() {
        return siteCarouselExcursionRepository.findAll().stream()
                .sorted(Comparator.comparing(SiteCarouselExcursion::getOrderCarouselExcursion))
                .map(this::convertToGetSiteSliderExcursionItemResponse)
                .collect(Collectors.toList());
    }

    private GetSiteCarouselExcursionItemResponse convertToGetSiteSliderExcursionItemResponse(
            SiteCarouselExcursion siteCarouselExcursion
    ) {
        Excursion excursion = siteCarouselExcursion.getExcursion();
        return new GetSiteCarouselExcursionItemResponse(
                excursion.getId().toString(),
                excursion.getName(),
                excursion.getDescription(),
                excursion.getPrice(),
                excursion.getDiscount(),
                excursion.getExcursionPhotoDataSet().stream()
                        .filter(excursionPhotoData -> excursionPhotoData.getOrderPhoto()
                                .equals(siteCarouselExcursion.getOrderExcursionPhoto()))
                        .map(ExcursionPhotoData::getPhoto)
                        .findFirst()
                        .orElseThrow(InvalidSiteCarouselExcursionPhotoOrderException::new)
        );
    }
}
