package com.catalis.core.notifications.core.services.push.v1;

import com.catalis.core.notifications.interfaces.dtos.push.v1.PushNotificationRequest;
import com.catalis.core.notifications.interfaces.dtos.push.v1.PushNotificationResponse;
import com.catalis.core.notifications.interfaces.interfaces.providers.push.v1.PushProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

//@Service
public class PushServiceImpl implements PushService {

    //@Autowired
    private PushProvider pushProvider;

    @Override
    public Mono<PushNotificationResponse> sendPush(PushNotificationRequest request) {
        return pushProvider.sendPush(request);
    }
}
