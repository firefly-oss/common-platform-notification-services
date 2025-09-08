/*
 * Copyright 2025 Firefly Software Solutions Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.firefly.core.notifications.provider.firebase.config.v1;

import com.firefly.core.notifications.provider.firebase.properties.v1.FcmProperties;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

//@Configuration
public class FcmConfig {
    //@Bean
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
        return FirebaseApp.initializeApp(options, "firefly-vc");
    }

    //@Bean
    public FirebaseMessaging firebaseMessaging(FirebaseApp firebaseApp) {
        return FirebaseMessaging.getInstance(firebaseApp);
    }
}