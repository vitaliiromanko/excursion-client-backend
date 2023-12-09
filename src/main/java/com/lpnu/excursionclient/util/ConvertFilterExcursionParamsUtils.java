package com.lpnu.excursionclient.util;

import com.lpnu.excursionclient.dto.filter.*;

import java.math.BigDecimal;
import java.text.Collator;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public final class ConvertFilterExcursionParamsUtils {
    private static final String PARAM_ARRAY_DELIMITER = ",";
    private static final String PARAM_RANGE_DELIMITER = "-";
    private static final String PARAM_DATE_RANGE_DELIMITER = "--";
    private static final String PARAM_DURATION_DELIMITER = ":";

    private static final Collator COLLATOR = Collator.getInstance(Locale.forLanguageTag("uk"));

    public static Set<String> getCategoriesFromParam(String param) {
        if (param == null || param.isEmpty()) {
            return null;
        }

        return getSortedAlphabeticallyStringsFromParam(param);
    }

    public static PriceRange getPriceRangeFromParam(String param) {
        if (param == null || param.isEmpty()) {
            return null;
        }

        List<BigDecimal> prices = getListFromParam(param, PARAM_RANGE_DELIMITER).stream()
                .map(BigDecimal::new)
                .sorted()
                .toList();
        return new PriceRange(prices.get(0), prices.get(1));
    }

    public static DurationRequestRange getDurationRangeFromParam(String param) {
        if (param == null || param.isEmpty()) {
            return null;
        }

        List<String> strDurations = getListFromParam(param, PARAM_RANGE_DELIMITER);
        List<DurationRequestItem> durationRequestItems = new ArrayList<>();

        for (String strDuration : strDurations) {
            String[] arrDuration = strDuration.split(PARAM_DURATION_DELIMITER);
            durationRequestItems.add(new DurationRequestItem(
                    Double.valueOf(arrDuration[0]),
                    arrDuration[1]
            ));
        }

        List<DurationRequestItem> sortedDurationRequestItems = durationRequestItems.stream()
                .sorted(Comparator.comparing(DurationRequestItem::unit, COLLATOR)
                        .thenComparing(DurationRequestItem::duration))
                .toList();

        return new DurationRequestRange(sortedDurationRequestItems.get(0), sortedDurationRequestItems.get(1));
    }

    public static Set<String> getMovementTypesFromParam(String param) {
        if (param == null || param.isEmpty()) {
            return null;
        }

        return getSortedAlphabeticallyStringsFromParam(param);
    }

    public static Set<String> getTopicTypesFromParam(String param) {
        if (param == null || param.isEmpty()) {
            return null;
        }

        return getSortedAlphabeticallyStringsFromParam(param);
    }

    public static Set<String> getConductingTypesFromParam(String param) {
        if (param == null || param.isEmpty()) {
            return null;
        }

        return getSortedAlphabeticallyStringsFromParam(param);
    }

    public static MaxPeopleNumberRange getMaxPeopleNumberRangeFromParam(String param) {
        if (param == null || param.isEmpty()) {
            return null;
        }

        List<Integer> maxPeopleNumbers = getListFromParam(param, PARAM_RANGE_DELIMITER).stream()
                .map(Integer::valueOf)
                .sorted()
                .toList();
        return new MaxPeopleNumberRange(maxPeopleNumbers.get(0), maxPeopleNumbers.get(1));
    }

    public static StartDateRange getStartDateRangeFromParam(String param) {
        if (param == null || param.isEmpty()) {
            return null;
        }

        List<LocalDateTime> startDates = getListFromParam(param, PARAM_DATE_RANGE_DELIMITER).stream()
                .map(s -> LocalDateTime.parse(s, DateTimeFormatter.ISO_DATE_TIME))
                .sorted()
                .toList();
        return new StartDateRange(startDates.get(0), startDates.get(1));
    }

    public static Set<String> getOrganizersFromParam(String param) {
        if (param == null || param.isEmpty()) {
            return null;
        }

        return getSortedAlphabeticallyStringsFromParam(param);
    }

    private static Set<String> getSortedAlphabeticallyStringsFromParam(String param) {
        return getListFromParam(param, PARAM_ARRAY_DELIMITER).stream()
                .sorted(Comparator.comparing(s -> s, COLLATOR))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private static List<String> getListFromParam(String param, String delimiter) {
        return new ArrayList<>(Arrays.asList(param.split(delimiter)));
    }

    private ConvertFilterExcursionParamsUtils() {
    }
}
