package com.catalis.core.notifications.core.services.email.v1;

import com.catalis.core.notifications.interfaces.dtos.email.v1.EmailRequestDTO;
import com.catalis.core.notifications.interfaces.dtos.email.v1.EmailResponseDTO;
import reactor.core.publisher.Mono;

public interface EmailService {
    Mono<EmailResponseDTO> sendEmail(EmailRequestDTO request);
}
