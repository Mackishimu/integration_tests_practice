package com.epam.probation.repository;

import com.epam.probation.models.Country;
import com.epam.probation.models.Region;
import com.epam.probation.utils.Utils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DataJpaTest
class CountryRepositoryIntegrationTest {

    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private RegionRepository regionRepository;


    @Test
    @DisplayName("avg region rating correct")
    void getAvgRegionRating() {
        //given
        Region asia = regionRepository.saveAndFlush(
                new Region("Asia")
        );
        Long regionId = asia.getId();

        double rating1 = 2;
        double rating2 = 4;

        countryRepository.save(
                Country.builder().name("Vietnam").region(asia).rating(rating1).build()
        );
        countryRepository.save(
                Country.builder().name("China").region(asia).rating(rating2).build()
        );

        //when
        double avgRegionRating = countryRepository.getAvgRegionRating(regionId);

        //then
        assertThat(avgRegionRating).isEqualTo(Utils.getAvg(rating1, rating2));
    }

    @Test
    @DisplayName("avg region rating wrong id throws exception")
    void wrongRegionIdThrowsException() {
        //given
        Region asia = regionRepository.saveAndFlush(
                new Region("Asia")
        );
        Long regionId = asia.getId();

        //then
        assertThatThrownBy(() -> countryRepository.getAvgRegionRating(regionId + 1));
    }

    @Test
    @DisplayName("finding by correct name")
    void findByExistingName() {
        //given
        Region asia = regionRepository.saveAndFlush(
                new Region("Asia")
        );
        String countryName = "Vietnam";
        Country savedCountry = countryRepository.saveAndFlush(
                Country.builder().name(countryName).region(asia).rating(2.0).build()
        );

        //when
        Optional<Country> searchedCountry = countryRepository.findByName(countryName);

        //then
        assertThat(searchedCountry).isEqualTo(Optional.of(savedCountry));
    }

    @Test
    @DisplayName("finding by non-existent name")
    void findByNonExistedName() {
        //given
        regionRepository.save(new Region("Asia"));

        //when
        Optional<Country> searchedCountry = countryRepository.findByName("Kongo");

        //then
        assertThat(searchedCountry).isEqualTo(Optional.empty());
    }

    @Test
    @DisplayName("finding by existing and not existing names")
    void findCausesByNames() {
        //given
        Region asia = regionRepository.saveAndFlush(
                new Region("Eurasia")
        );
        String countryName = "Kazakhstan";
        countryRepository.save(
                Country.builder().name(countryName).region(asia).rating(3.0).build()
        );

        //when
        boolean existsByExistingName = countryRepository.existsByName(countryName);
        boolean existsByNonExistedName = countryRepository.existsByName(countryName + "a");

        //then
        assertThat(existsByExistingName).isEqualTo(true);
        assertThat(existsByNonExistedName).isEqualTo(false);
    }
}