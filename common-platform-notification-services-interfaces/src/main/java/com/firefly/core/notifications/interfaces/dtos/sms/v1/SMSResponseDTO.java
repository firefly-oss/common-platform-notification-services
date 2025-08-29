package com.firefly.core.notifications.interfaces.dtos.sms.v1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SMSResponseDTO {
    private String messageId;      // Provider's message ID
    private String status;         // Delivery status
    private String errorMessage;   // Error message if any
    private long timestamp;        // When the message was sent

    public static SMSResponseDTO success(String messageId) {
        return SMSResponseDTO.builder()
                .messageId(messageId)
                .status("SENT")
                .timestamp(System.currentTimeMillis())
                .build();
    }

    public static SMSResponseDTO error(String errorMessage) {
        return SMSResponseDTO.builder()
                .status("FAILED")
                .errorMessage(errorMessage)
                .timestamp(System.currentTimeMillis())
                .build();
    }
}