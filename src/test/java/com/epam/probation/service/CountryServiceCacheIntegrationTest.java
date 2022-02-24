package com.epam.probation.service;

import com.epam.probation.models.Country;
import com.epam.probation.models.Region;
import com.epam.probation.repository.CountryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@SpringBootTest(webEnvironment = NONE)
class CountryServiceCacheIntegrationTest {

    @Autowired
    private CountryService countryService;
    @MockBean
    private CountryRepository beanCountryRepository;

    @Test
    @DisplayName("cached country calls from repository once")
    void shouldCallCachedCountryOnce() {
        // given
        String countryName = "Switzerland";
        Region region = Region.builder().id(1L).name("Europe").build();
        Country country = Country.builder().id(1L).name(countryName).region(region).rating(4.7).build();

        given(beanCountryRepository.findByName(countryName)).willReturn(Optional.of(country));

        // when
        for (int i = 0; i < 5; i++) {
            countryService.findByName(countryName);
        }

        // then
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(beanCountryRepository, times(1)).findByName(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue()).isEqualTo(countryName);
    }

}