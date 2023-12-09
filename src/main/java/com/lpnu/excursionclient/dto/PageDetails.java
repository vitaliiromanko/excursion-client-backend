package com.lpnu.excursionclient.dto;

import org.springframework.data.domain.Sort;

public interface PageDetails {
    int getNumber();

    int getSize();

    Sort.Direction getSortDirection();

    String getSortBy();
}
