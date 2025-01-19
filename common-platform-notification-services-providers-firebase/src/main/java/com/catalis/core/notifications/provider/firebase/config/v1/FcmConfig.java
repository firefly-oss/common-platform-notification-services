package com.catalis.core.notifications.provider.firebase.config.v1;

import com.catalis.core.notifications.provider.firebase.properties.v1.FcmProperties;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
public class FcmConfig {
    @Bean
    public FirebaseApp firebaseApp(FcmProperties fcmProperties) throws IOException {
        // 1. Read the environment variable with the JSON content:
        // We are doing that to avoid store the credentials at disk
        if (fcmProperties.getCredentials() == null || fcmProperties.getCredentials().isBlank()) {
            throw new IllegalStateException(
                    "The environment variable '" + fcmProperties.getCredentials() + "' is not defined or is empty."
            );
        }

        // 2. Convert the JSON to an InputStream:
        ByteArrayInputStream serviceAccountStream =
                new ByteArrayInputStream(fcmProperties.getCredentials().getBytes(StandardCharsets.UTF_8));

        // 3. Build Firebase options:
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccountStream))
                .build();

        // 4. Initialize the app (if not already initialized):
        return FirebaseApp.initializeApp(options, "catalis-vc");
    }

    @Bean
    public FirebaseMessaging firebaseMessaging(FirebaseApp firebaseApp) {
        return FirebaseMessaging.getInstance(firebaseApp);
    }
}