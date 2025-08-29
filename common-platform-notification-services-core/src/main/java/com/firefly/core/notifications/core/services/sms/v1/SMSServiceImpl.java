package com.firefly.core.notifications.core.services.sms.v1;

import com.firefly.core.notifications.interfaces.dtos.sms.v1.SMSRequestDTO;
import com.firefly.core.notifications.interfaces.dtos.sms.v1.SMSResponseDTO;
import com.firefly.core.notifications.interfaces.interfaces.providers.sms.v1.SMSProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SMSServiceImpl implements SMSService {

    @Autowired
    private SMSProvider smsProvider;

    @Override
    public Mono<SMSResponseDTO> sendSMS(SMSRequestDTO request) {
        return Mono.fromCallable(() -> smsProvider.sendSMS(request))
                .onErrorResume(error -> Mono.just(SMSResponseDTO.error(error.getMessage())));
    }
}