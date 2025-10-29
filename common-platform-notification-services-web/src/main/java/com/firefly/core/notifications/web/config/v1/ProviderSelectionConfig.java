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

package com.firefly.core.notifications.web.config.v1;

import com.firefly.core.notifications.interfaces.interfaces.providers.email.v1.EmailProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Configuration
public class ProviderSelectionConfig {

    @Autowired
    private NotificationsSelectionProperties props;

    @Bean
    @Primary
    public EmailProvider delegatingEmailProvider(Map<String, EmailProvider> providers) {
        if (providers == null || providers.isEmpty()) {
            throw new IllegalStateException("No EmailProvider beans found. Add a provider adapter dependency and configuration.");
        }
        if (providers.size() == 1) {
            Map.Entry<String, EmailProvider> only = providers.entrySet().iterator().next();
            log.info("Using only available EmailProvider bean: {}", only.getKey());
            return only.getValue();
        }

        String desired = Optional.ofNullable(props.getEmail().getProvider())
                .map(s -> s.trim().toLowerCase(Locale.ROOT))
                .orElse(null);

        if (desired == null || desired.isBlank()) {
            throw new IllegalStateException("Multiple EmailProvider beans found " + providers.keySet() +
                    ". Please set 'notifications.email.provider' to one of: resend, sendgrid, or a bean name.");
        }

        // Try exact bean name first
        if (providers.containsKey(desired)) {
            log.info("Using EmailProvider bean by exact name: {}", desired);
            return providers.get(desired);
        }

        // Try alias match by substring (resend -> resendEmailProvider, sendgrid -> sendGridEmailProvider)
        String alias = desired;
        EmailProvider matched = providers.entrySet().stream()
                .filter(e -> e.getKey().toLowerCase(Locale.ROOT).contains(alias))
                .map(Map.Entry::getValue)
                .reduce((a, b) -> {
                    throw new IllegalStateException("'notifications.email.provider=" + desired + "' matched multiple beans: " + providers.keySet());
                })
                .orElse(null);

        if (matched != null) {
            log.info("Using EmailProvider bean by alias '{}': matched.", desired);
            return matched;
        }

        throw new IllegalStateException("Could not resolve EmailProvider for 'notifications.email.provider=" + desired + "'. Available beans: " + providers.keySet());
    }
}
