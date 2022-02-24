package com.epam.probation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.util.concurrent.ExecutorService;
import java.util.function.Predicate;

@SpringBootApplication
@EnableCaching
public class WebForTestsApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebForTestsApplication.class, args);
    }

}
