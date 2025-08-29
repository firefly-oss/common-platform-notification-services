package com.firefly.core.notifications.interfaces.dtos.email.v1;

import com.firefly.core.notifications.interfaces.enums.EmailStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailResponseDTO {
    private String messageId;
    private EmailStatusEnum status;
    private String errorMessage;
    private long timestamp;

    public static EmailResponseDTO success(String messageId) {
        return EmailResponseDTO.builder()
                .messageId(messageId)
                .status(EmailStatusEnum.SENT)
                .timestamp(System.currentTimeMillis())
                .build();
    }

    public static EmailResponseDTO error(String errorMessage) {
        return EmailResponseDTO.builder()
                .status(EmailStatusEnum.FAILED)
                .errorMessage(errorMessage != null ? errorMessage : "Unknown error")
                .timestamp(System.currentTimeMillis())
                .build();
    }
}