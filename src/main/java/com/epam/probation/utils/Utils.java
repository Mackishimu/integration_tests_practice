package com.epam.probation.utils;

import com.epam.probation.models.Country;
import com.epam.probation.models.CountryDto;
import com.epam.probation.models.Region;

public class Utils {

    public static double getAvg(double... numbers) {
        double sum = 0;
        for (double number : numbers) {
            sum += number;
        }
        return sum / numbers.length;
    }

    public static Country newCountry(String name, Region region, double rating) {
        return Country.builder().name(name).region(region).rating(rating).build();
    }

    public static CountryDto newCountryDto(String countryName, Long regionId, double rating) {
        return CountryDto.builder().countryName(countryName).regionId(regionId).rating(rating).build();
    }
}
