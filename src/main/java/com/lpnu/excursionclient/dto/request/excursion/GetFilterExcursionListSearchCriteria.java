package com.lpnu.excursionclient.dto.request.excursion;

import com.lpnu.excursionclient.dto.filter.DurationRequestRange;
import com.lpnu.excursionclient.dto.filter.MaxPeopleNumberRange;
import com.lpnu.excursionclient.dto.filter.PriceRange;
import com.lpnu.excursionclient.dto.filter.StartDateRange;
import com.lpnu.excursionclient.util.ConvertFilterExcursionParamsUtils;
import lombok.Getter;

import java.util.Set;

@Getter
public class GetFilterExcursionListSearchCriteria {
    private final Set<String> categories;
    private final PriceRange priceRange;
    private final DurationRequestRange durationRequestRange;
    private final Set<String> movementTypes;
    private final Set<String> topicTypes;
    private final Set<String> conductingTypes;
    private final MaxPeopleNumberRange maxPeopleNumberRange;
    private final StartDateRange startDateRange;
    private final Set<String> organizers;

    public static GetFilterExcursionListSearchCriteria of(GetFilterExcursionListRequest getFilterExcursionListRequest) {
        return new GetFilterExcursionListSearchCriteria(getFilterExcursionListRequest);
    }

    private GetFilterExcursionListSearchCriteria(GetFilterExcursionListRequest getFilterExcursionListRequest) {
        this.categories = ConvertFilterExcursionParamsUtils.getCategoriesFromParam(getFilterExcursionListRequest.getCategory());
        this.priceRange = ConvertFilterExcursionParamsUtils.getPriceRangeFromParam(getFilterExcursionListRequest.getPrice());
        this.durationRequestRange = ConvertFilterExcursionParamsUtils.getDurationRangeFromParam(getFilterExcursionListRequest.getDuration());
        this.movementTypes = ConvertFilterExcursionParamsUtils.getMovementTypesFromParam(getFilterExcursionListRequest.getMovement_type());
        this.topicTypes = ConvertFilterExcursionParamsUtils.getTopicTypesFromParam(getFilterExcursionListRequest.getTopic_type());
        this.conductingTypes = ConvertFilterExcursionParamsUtils.getConductingTypesFromParam(getFilterExcursionListRequest.getConducting_type());
        this.maxPeopleNumberRange = ConvertFilterExcursionParamsUtils.getMaxPeopleNumberRangeFromParam(getFilterExcursionListRequest.getMax_people_number());
        this.startDateRange = ConvertFilterExcursionParamsUtils.getStartDateRangeFromParam(getFilterExcursionListRequest.getStart_date());
        this.organizers = ConvertFilterExcursionParamsUtils.getOrganizersFromParam(getFilterExcursionListRequest.getOrganizer());
    }
}
