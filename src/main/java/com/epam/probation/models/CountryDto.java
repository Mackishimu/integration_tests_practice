package com.epam.probation.models;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CountryDto {

    private String countryName;
    private double rating;
    private Long regionId;

}
