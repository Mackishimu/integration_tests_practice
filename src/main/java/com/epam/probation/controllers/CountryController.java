package com.epam.probation.controllers;

import com.epam.probation.exceptions.CountryNotFoundException;
import com.epam.probation.models.Country;
import com.epam.probation.models.CountryDto;
import com.epam.probation.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("country")
public class CountryController {

    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/rating/region/avg/{regionId}")
    double avgRegionRating(
            @PathVariable long regionId
    ) {
        return countryService.getAvgRegionRating(regionId);
    }

    @GetMapping("/{name}")
    Country getByName(@PathVariable String name) {
        return countryService.findByName(
                name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase()
        );
    }

    @PostMapping("/new")
    Country saveNewCountry(
            @RequestBody CountryDto countryDto
    ) {
        return countryService.save(countryDto);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    void countryException(CountryNotFoundException e) {

    }

}
