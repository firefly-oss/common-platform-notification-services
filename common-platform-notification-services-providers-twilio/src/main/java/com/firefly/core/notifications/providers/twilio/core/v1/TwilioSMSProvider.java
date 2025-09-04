/*
 * Copyright 2025 Firefly Software Solutions Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.firefly.core.notifications.providers.twilio.core.v1;

import com.firefly.core.notifications.interfaces.dtos.sms.v1.SMSRequestDTO;
import com.firefly.core.notifications.interfaces.dtos.sms.v1.SMSResponseDTO;
import com.firefly.core.notifications.interfaces.interfaces.providers.sms.v1.SMSProvider;
import com.firefly.core.notifications.providers.twilio.properties.v1.TwilioProperties;
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