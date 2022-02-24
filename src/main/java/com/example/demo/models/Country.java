package com.example.demo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Country {
    private Long id;
    private String name;
    private double rating;
    private Region region;

    public String getRegionName() {
        return region.getName();
    }
}
