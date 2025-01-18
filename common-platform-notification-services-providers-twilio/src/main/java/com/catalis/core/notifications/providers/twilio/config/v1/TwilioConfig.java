package com.catalis.core.notifications.providers.twilio.config.v1;

import com.catalis.core.notifications.providers.twilio.properties.v1.TwilioProperties;
import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TwilioConfig {

    @Autowired
    private TwilioProperties twilioProperties;

    @PostConstruct
    public void initTwilio() {
        Twilio.init(
                twilioProperties.getAccountSid(),
                twilioProperties.getAuthToken()
        );
    }
}