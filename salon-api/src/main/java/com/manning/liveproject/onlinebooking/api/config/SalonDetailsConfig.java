package com.manning.liveproject.onlinebooking.api.config;

import com.manning.liveproject.onlinebooking.api.model.SalonDetails;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SalonDetailsConfig {

    @Bean
    @ConfigurationProperties(prefix = "com.manning.liveproject.salon-details")
    public SalonDetails salonDetails() {
        return new SalonDetails();
    }
}
