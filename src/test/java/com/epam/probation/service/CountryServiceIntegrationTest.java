package com.epam.probation.service;

import com.epam.probation.exceptions.CountryNotFoundException;
import com.epam.probation.models.Country;
import com.epam.probation.models.CountryDto;
import com.epam.probation.models.Region;
import com.epam.probation.repository.CountryRepository;
import com.epam.probation.repository.RegionRepository;
import com.epam.probation.utils.Utils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;

import static com.epam.probation.utils.Utils.newCountry;
import static com.epam.probation.utils.Utils.newCountryDto;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@SpringBootTest(webEnvironment = NONE)
class CountryServiceIntegrationTest {

    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private CountryService countryService;

    @AfterEach
    void tearDown() {
        countryRepository.deleteAll();
        regionRepository.deleteAll();
    }

    @Test
    @DisplayName("get avg rating from service layer")
    @Transactional
    void getAvgRegionRating() {
        //given
        Region europe = saveNewRegion("Europe");
        Long regionId = europe.getId();

        double rating1 = 2.5;
        double rating2 = 3.0;

        countryRepository.save(newCountry("Macedonia", europe, rating1));
        countryRepository.save(newCountry("Poland", europe, rating2));

        //when
        double avgRegionRating = countryService.getAvgRegionRating(regionId);

        //then
        assertThat(avgRegionRating).isEqualTo(Utils.getAvg(rating1, rating2));
    }

    @Test
    @DisplayName("incorrect id throws exception")
    void shouldThrowIncorrectIdException() {
        //then
        assertThatThrownBy(() -> countryService.getAvgRegionRating(1L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("no such id");
    }

    @Test
    @DisplayName("finding country by correct name")
    void shouldFindCountryByName() {
        //given
        Region africa = saveNewRegion("Africa");
        String countryName = "Niger";
        Country savedCountry = countryRepository.saveAndFlush(newCountry(countryName, africa, 1.2));

        //when
        Country searchedCountry = countryService.findByName(countryName);

        //then
        assertThat(savedCountry).isEqualToComparingFieldByFieldRecursively(searchedCountry);
    }

    @Test
    @DisplayName("incorrect country name exception")
    void shouldThrowIncorrectCountryNameException() {
        assertThatThrownBy(() -> countryService.findByName("Velikobritaniya"))
                .isInstanceOf(CountryNotFoundException.class)
                .hasMessageContaining("No such country name");
    }

    @Test
    @DisplayName("saving unique country")
    void shouldSaveUniqueCountry() {
        //given
        Region australiaAndOceania = saveNewRegion("Australia and Oceania");
        String countryName = "New Zealand";

        //when
        Country savedCountry = countryService.save(
                newCountryDto(countryName, australiaAndOceania.getId(), 3.7)
        );

        //then
        assertThat(savedCountry.getId()).isNotEqualTo(null);
    }

    @Test
    @DisplayName("saving not unique country throws exception")
    void shouldThrowNonUniqueCountrySaveException() {
        //given
        Region asia = saveNewRegion("Asia");
        String countryName = "North Korea";
        Country country = newCountry(countryName, asia, 1.3);
        countryRepository.save(country);
        CountryDto dto = newCountryDto(countryName, asia.getId(), country.getRating());

        //then
        assertThatThrownBy(() -> countryService.save(dto))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Country " + countryName + "already exists");
    }

    private Region saveNewRegion(String name) {
        return regionRepository.saveAndFlush(new Region(name));
    }

}