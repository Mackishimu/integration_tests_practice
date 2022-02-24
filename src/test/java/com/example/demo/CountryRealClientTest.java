package com.example.demo;

import com.example.demo.requests.CountryRequests;
import com.example.demo.models.Country;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class CountryRealClientTest {

    @Autowired
    private CountryRequests countryRequests;

    @Test
    @DisplayName("get correct country real")
    void getGivenCountryCorrect() {
        //given
        String countryName = "Kazakhstan";
        //when
        Country country = countryRequests.getCountry(countryName);
        //then
        assertThat(country.getId()).isNotEqualTo(null);
        assertThat(country.getName()).isEqualTo(countryName);
        assertThat(country.getRegionName()).isEqualTo("Eurasia");
    }
}
