package com.firefly.core.notifications.core.services.sms.v1;

import com.firefly.core.notifications.interfaces.dtos.sms.v1.SMSRequestDTO;
import com.firefly.core.notifications.interfaces.dtos.sms.v1.SMSResponseDTO;
import reactor.core.publisher.Mono;

public interface SMSService {
    Mono<SMSResponseDTO> sendSMS(SMSRequestDTO request);
}