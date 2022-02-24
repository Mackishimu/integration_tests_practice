package com.epam.probation.repository;

import com.epam.probation.models.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    @Query(
            value = "select avg(rating) " +
                    "from countries " +
                    "where region = ?1",
            nativeQuery = true
    )
    double getAvgRegionRating(Long regionId);

    Optional<Country> findByName(String name);

    boolean existsByName(String name);

}
