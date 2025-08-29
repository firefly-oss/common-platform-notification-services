package com.firefly.core.notifications.providers.twilio.properties.v1;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "twilio.config")
public class TwilioProperties {
    private String accountSid;
    private String authToken;
    private String phoneNumber;
}