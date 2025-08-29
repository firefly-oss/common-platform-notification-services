package com.firefly.core.notifications.core.services.push.v1;

import com.firefly.core.notifications.interfaces.dtos.push.v1.PushNotificationRequest;
import com.firefly.core.notifications.interfaces.dtos.push.v1.PushNotificationResponse;
import reactor.core.publisher.Mono;

public interface PushService {
    Mono<PushNotificationResponse> sendPush(PushNotificationRequest request);
}
