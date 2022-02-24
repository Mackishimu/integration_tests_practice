package com.epam.probation.service;

import com.epam.probation.exceptions.CountryNotFoundException;
import com.epam.probation.models.Country;
import com.epam.probation.models.CountryDto;
import com.epam.probation.models.Region;
import com.epam.probation.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;
    private final RegionService regionService;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository, RegionService regionService) {
        this.countryRepository = countryRepository;
        this.regionService = regionService;
    }

    @Override
    public double getAvgRegionRating(Long id) {
        try {
            return countryRepository.getAvgRegionRating(id);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "no such id"
            );
        }
    }

    @Override
    @Cacheable("countries")
    public Country findByName(String name) {
        return countryRepository
                .findByName(name)
                .orElseThrow(CountryNotFoundException::new);
    }

    @Override
    public Country save(CountryDto countryDto) {
        if (countryRepository.existsByName(countryDto.getCountryName()))
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Country " + countryDto.getCountryName() + "already exists"
            );
        Region region = regionService.getRegion(countryDto.getRegionId());
        Country country = Country.builder()
                .name(countryDto.getCountryName())
                .region(region)
                .rating(countryDto.getRating())
                .build();
        return countryRepository.saveAndFlush(country);
    }

}
