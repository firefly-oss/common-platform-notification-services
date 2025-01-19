package com.catalis.core.notifications.interfaces.dtos.push.v1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PushNotificationRequest {
    private String token;
    private String title;
    private String body;
    private Map<String, String> data;
}