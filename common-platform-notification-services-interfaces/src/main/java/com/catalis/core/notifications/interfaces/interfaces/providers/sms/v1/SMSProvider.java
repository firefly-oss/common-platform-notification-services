package com.catalis.core.notifications.interfaces.interfaces.providers.sms.v1;

import com.catalis.core.notifications.interfaces.dtos.sms.v1.SMSRequestDTO;
import com.catalis.core.notifications.interfaces.dtos.sms.v1.SMSResponseDTO;

public interface SMSProvider {

    /**
     * Send SMS using the provider's infrastructure
     *
     * @param request SMS request containing message details
     * @return Response containing delivery status and message ID
     */
    SMSResponseDTO sendSMS(SMSRequestDTO request);
}