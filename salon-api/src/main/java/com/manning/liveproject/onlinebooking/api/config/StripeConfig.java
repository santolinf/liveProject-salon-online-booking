package com.manning.liveproject.onlinebooking.api.config;

import com.manning.liveproject.onlinebooking.api.model.StripeSecret;
import com.stripe.Stripe;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class StripeConfig {

    @Bean
    @ConfigurationProperties(prefix = "stripe.secret")
    public StripeSecret stripeSecret() {
        return new StripeSecret();
    }

    @PostConstruct
    public void setSecretKey() {
        Stripe.apiKey = stripeSecret().getKey();
    }
}
