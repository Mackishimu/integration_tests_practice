package com.example.demo;

import com.example.demo.models.Country;
import com.example.demo.requests.CountryRequests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureWireMock(port = 8090)
public class CountryMockClientTest {

    @Autowired
    private CountryRequests countryRequests;

    @Test
    @DisplayName("get correct country mock")
    void getGivenCountryCorrect() {
        //given
        String countryName = "Kazakhstan";
        stubFor(
                get("/country/" + countryName).willReturn(
                        okJson(
                                "{" +
                                        "\"id\":1," +
                                        "\"name\":\"Kazakhstan\"," +
                                        "\"rating\":3.0," +
                                        "\"region\":" +
                                        "{" +
                                        "\"id\":6," +
                                        "\"name\":\"Eurasia\"" +
                                        "}" +
                                        "}"
                        )
                )
        );
        //when
        Country country = countryRequests.getCountry(countryName);
        //then
        assertThat(country.getId()).isNotEqualTo(null);
        assertThat(country.getName()).isEqualTo(countryName);
        assertThat(country.getRegionName()).isEqualTo("Eurasia");
    }
}
