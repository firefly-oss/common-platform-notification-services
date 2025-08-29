package com.firefly.core.notifications.provider.firebase.core.v1;

import com.firefly.core.notifications.interfaces.dtos.push.v1.PushNotificationRequest;
import com.firefly.core.notifications.interfaces.dtos.push.v1.PushNotificationResponse;
import com.firefly.core.notifications.interfaces.interfaces.providers.push.v1.PushProvider;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

//@Service
public class FcmPushProvider implements PushProvider {

    //@Autowired
    private FirebaseMessaging firebaseMessaging;

    @Override
    public Mono<PushNotificationResponse> sendPush(PushNotificationRequest request) {
        return Mono.fromCallable(() -> {
            try {
                // Build the notification
                Notification notification = Notification.builder()
                        .setTitle(request.getTitle())
                        .setBody(request.getBody())
                        .build();

                // Build the message
                Message.Builder messageBuilder = Message.builder()
                        .setToken(request.getToken())
                        .setNotification(notification);

                // Add additional data if present
                if (request.getData() != null && !request.getData().isEmpty()) {
                    messageBuilder.putAllData(request.getData());
                }

                // Finalize the message
                Message message = messageBuilder.build();

                // Send the message synchronously (blocking)
                String messageId = firebaseMessaging.send(message);

                // Return a successful response
                return PushNotificationResponse.builder()
                        .messageId(messageId)
                        .success(true)
                        .errorMessage(null)
                        .build();
            } catch (Exception e) {
                // Capture any errors and return a failure response
                return PushNotificationResponse.builder()
                        .messageId(null)
                        .success(false)
                        .errorMessage(e.getMessage())
                        .build();
            }
        });
    }
}