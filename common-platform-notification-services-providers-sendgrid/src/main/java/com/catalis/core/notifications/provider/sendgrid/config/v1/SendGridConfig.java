package com.catalis.core.notifications.provider.sendgrid.config.v1;

import com.catalis.core.notifications.provider.sendgrid.properties.v1.SendGridProperties;
import com.sendgrid.SendGrid;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SendGridConfig {

    @Bean
    public SendGrid sendGrid(SendGridProperties properties) {
        return new SendGrid(properties.getApiKey());
    }
}