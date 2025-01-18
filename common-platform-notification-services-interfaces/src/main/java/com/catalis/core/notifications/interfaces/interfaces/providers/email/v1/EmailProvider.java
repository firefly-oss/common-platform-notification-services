package com.catalis.core.notifications.interfaces.interfaces.providers.email.v1;

import com.catalis.core.notifications.interfaces.dtos.email.v1.EmailRequestDTO;
import com.catalis.core.notifications.interfaces.dtos.email.v1.EmailResponseDTO;
import reactor.core.publisher.Mono;

public interface EmailProvider {
    /**
     * Send an email using the provider's infrastructure
     * @param request Email request containing message details
     * @return Response containing delivery status and message ID
     */
    Mono<EmailResponseDTO> sendEmail(EmailRequestDTO request);
}
