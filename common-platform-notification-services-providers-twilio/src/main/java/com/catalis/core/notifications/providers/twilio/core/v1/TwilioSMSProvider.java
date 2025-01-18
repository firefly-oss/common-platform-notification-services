package com.catalis.core.notifications.providers.twilio.core.v1;

import com.catalis.core.notifications.interfaces.dtos.sms.v1.SMSRequestDTO;
import com.catalis.core.notifications.interfaces.dtos.sms.v1.SMSResponseDTO;
import com.catalis.core.notifications.interfaces.interfaces.providers.sms.v1.SMSProvider;
import com.catalis.core.notifications.providers.twilio.properties.v1.TwilioProperties;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class TwilioSMSProvider implements SMSProvider {

    @Autowired
    private TwilioProperties twilioProperties;

    @Override
    public SMSResponseDTO sendSMS(SMSRequestDTO request) {
        // Validate request
        if (request == null) {
            return SMSResponseDTO.error("Request cannot be null");
        }

        // Check if phoneNumber is actually being set
        if (request.getPhoneNumber() == null) {
            return SMSResponseDTO.error("Phone number is null");
        }

        if (request.getPhoneNumber().trim().isEmpty()) {
            return SMSResponseDTO.error("Phone number cannot be empty");
        }

        if (!StringUtils.hasText(request.getMessage())) {
            return SMSResponseDTO.error("Message cannot be empty");
        }

        if (!StringUtils.hasText(twilioProperties.getPhoneNumber())) {
            return SMSResponseDTO.error("Sender phone number not configured");
        }

        // Send message
        Message message = Message.creator(
                new PhoneNumber(request.getPhoneNumber().trim()),
                new PhoneNumber(twilioProperties.getPhoneNumber().trim()),
                request.getMessage().trim()
        ).create();

        return SMSResponseDTO.success(message.getSid());
    }
}