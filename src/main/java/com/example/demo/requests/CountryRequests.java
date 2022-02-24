package com.example.demo.requests;

import com.example.demo.models.Country;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class CountryRequests {

    @Autowired
    private final WebClient webClient;

    public Country getCountry(String countryName) {
        return webClient
                .get()
                .uri("/country/" + countryName)
                .retrieve()
                .bodyToMono(Country.class)
                .block();
    }
}
