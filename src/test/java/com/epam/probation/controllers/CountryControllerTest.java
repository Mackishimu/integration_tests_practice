package com.epam.probation.controllers;

import com.epam.probation.exceptions.CountryNotFoundException;
import com.epam.probation.models.Country;
import com.epam.probation.models.Region;
import com.epam.probation.service.CountryService;
import com.epam.probation.utils.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class CountryControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CountryService countryService;
    private final ObjectMapper objectMapper;

    @Autowired
    CountryControllerTest(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Test
    @DisplayName("avg rating request returns 200")
    void getAvgRatingIsReturned() throws Exception {
        //given
        long regionId = 189;
        given(countryService.getAvgRegionRating(regionId)).willReturn(3.0);
        //then
        mockMvc.perform(get("/country/rating/region/avg/" + regionId)).andExpect(status().isOk());
    }

    @Test
    @DisplayName("correct get country request")
    void shouldReturnCountry() throws Exception {
        //given
        Region europe = new Region("Europe");
        String countryName = "Belarus";
        double rating = 2.1;
        Country belarus = Utils.newCountry(countryName, europe, rating);
        given(countryService.findByName(countryName)).willReturn(belarus);
        //then
        MvcResult mvcResult = mockMvc
                .perform(get("/country/" + countryName))
                .andExpect(status().isOk())
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        assertThat(jsonString(belarus)).isEqualTo(result);
    }

    @Test
    @DisplayName("incorrect country name throws bad request")
    void getCountryThrowsException() throws Exception {
        //given
        String countryName = "Georgia";
        given(countryService.findByName(countryName)).willThrow(CountryNotFoundException.class);
        //then
        mockMvc.perform(get("/country/" + countryName))
                .andExpect(status().isBadRequest());
    }

    private String jsonString(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

}
