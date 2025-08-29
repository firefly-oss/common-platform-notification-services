package com.firefly.core.notifications.core.services.email.v1;

import com.firefly.core.notifications.interfaces.dtos.email.v1.EmailRequestDTO;
import com.firefly.core.notifications.interfaces.dtos.email.v1.EmailResponseDTO;
import com.firefly.core.notifications.interfaces.interfaces.providers.email.v1.EmailProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private EmailProvider emailProvider;

    @Override
    public Mono<EmailResponseDTO> sendEmail(EmailRequestDTO request) {
        return emailProvider.sendEmail(request);
    }
}
