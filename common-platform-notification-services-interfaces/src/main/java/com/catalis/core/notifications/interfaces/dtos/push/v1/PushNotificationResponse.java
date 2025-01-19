package com.catalis.core.notifications.interfaces.dtos.push.v1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PushNotificationResponse {
    private String messageId;
    private boolean success;
    private String errorMessage;
}