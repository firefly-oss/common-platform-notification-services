package com.catalis.core.notifications.interfaces.interfaces.providers.push.v1;

import com.catalis.core.notifications.interfaces.dtos.push.v1.PushNotificationRequest;
import com.catalis.core.notifications.interfaces.dtos.push.v1.PushNotificationResponse;
import reactor.core.publisher.Mono;

public interface PushProvider {
    Mono<PushNotificationResponse> sendPush(PushNotificationRequest request);
}