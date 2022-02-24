package com.epam.probation.service;

import com.epam.probation.models.Country;
import com.epam.probation.models.CountryDto;

public interface CountryService {

    double getAvgRegionRating(Long id);

    Country findByName(String name);

    Country save(CountryDto country);

}
