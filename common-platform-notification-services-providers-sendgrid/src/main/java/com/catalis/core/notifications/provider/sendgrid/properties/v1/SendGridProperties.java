package com.catalis.core.notifications.provider.sendgrid.properties.v1;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "sendgrid")
public class SendGridProperties {
    private String apiKey;
}
