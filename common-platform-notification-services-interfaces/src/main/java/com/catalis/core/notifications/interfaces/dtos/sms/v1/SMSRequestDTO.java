package com.catalis.core.notifications.interfaces.dtos.sms.v1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SMSRequestDTO {
    private String phoneNumber;
    private String message;
}