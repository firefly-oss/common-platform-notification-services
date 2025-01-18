package com.catalis.core.notifications.provider.sendgrid.core.v1;

import com.catalis.core.notifications.interfaces.dtos.email.v1.EmailAttachmentDTO;
import com.catalis.core.notifications.interfaces.dtos.email.v1.EmailRequestDTO;
import com.catalis.core.notifications.interfaces.dtos.email.v1.EmailResponseDTO;
import com.catalis.core.notifications.interfaces.interfaces.providers.email.v1.EmailProvider;
import com.catalis.core.notifications.provider.sendgrid.properties.v1.SendGridProperties;
import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Attachments;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;

@Slf4j
@Component
public class SendGridEmailProvider implements EmailProvider {

    @Autowired
    private SendGridProperties properties; // e.g. may hold defaultFrom, API key, etc.

    @Autowired
    private SendGrid sendGrid;

    @Override
    public Mono<EmailResponseDTO> sendEmail(EmailRequestDTO request) {
        return Mono.fromCallable(() -> {
            try {
                // Build the Mail object
                Mail mail = buildMail(request);

                // Create and configure the SendGrid request
                Request sendGridRequest = new Request();
                sendGridRequest.setMethod(Method.POST);
                sendGridRequest.setEndpoint("mail/send");
                sendGridRequest.setBody(mail.build());

                // Send the email
                Response response = sendGrid.api(sendGridRequest);

                log.debug("SendGrid response - Status Code: {}, Body: {}",
                        response.getStatusCode(), response.getBody());

                // Parse the response
                if (response.getStatusCode() >= 200 && response.getStatusCode() < 300) {
                    // Attempt to retrieve X-Message-Id from headers if present
                    String messageId = getMessageIdFromHeaders(response.getHeaders());
                    return EmailResponseDTO.success(messageId);
                } else {
                    return EmailResponseDTO.error("SendGrid error: " + response.getBody());
                }
            } catch (IOException e) {
                log.error("Error sending email via SendGrid", e);
                return EmailResponseDTO.error(e.getMessage());
            }
        }).subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Build the Mail object from the EmailRequestDTO.
     */
    private Mail buildMail(EmailRequestDTO request) {
        // Prepare 'from' and 'to'
        Email from = new Email(request.getFrom());
        Email to = new Email(request.getTo());

        // Create content object: prefer HTML if it's present
        Content content;
        if (StringUtils.hasText(request.getHtml())) {
            content = new Content("text/html", request.getHtml());
        } else {
            // Fallback to plain text
            content = new Content("text/plain",
                    StringUtils.hasText(request.getText()) ? request.getText() : "");
        }

        // Build the Mail
        Mail mail = new Mail(from, request.getSubject(), to, content);

        // Add CC recipients if present
        if (request.getCc() != null) {
            for (String cc : request.getCc()) {
                if (StringUtils.hasText(cc)) {
                    mail.personalization.getFirst().addCc(new Email(cc));
                }
            }
        }

        // Add BCC recipients if present
        if (request.getBcc() != null) {
            for (String bcc : request.getBcc()) {
                if (StringUtils.hasText(bcc)) {
                    mail.personalization.getFirst().addBcc(new Email(bcc));
                }
            }
        }

        // Add attachments if present
        if (request.getAttachments() != null) {
            for (EmailAttachmentDTO attachment : request.getAttachments()) {
                if (attachment != null && attachment.getContent() != null && attachment.getContent().length > 0) {
                    Attachments sgAttachment = new Attachments();
                    sgAttachment.setContent(Base64.getEncoder().encodeToString(attachment.getContent()));
                    sgAttachment.setType(attachment.getContentType());
                    sgAttachment.setFilename(attachment.getFilename());
                    sgAttachment.setDisposition("attachment");
                    mail.addAttachments(sgAttachment);
                }
            }
        }

        return mail;
    }

    /**
     * Safely extract the 'X-Message-Id' header from SendGrid response headers, if present.
     */
    private String getMessageIdFromHeaders(Map<String, String> headers) {
        if (headers == null) {
            return null;
        }
        // Some SendGrid responses have the key as "X-Message-Id" or "X-Message-ID"
        // (exact case can vary in older documentation). Adjust if needed.
        String headerValue = headers.get("X-Message-Id");
        if (headerValue == null) {
            headerValue = headers.get("X-Message-ID");
        }
        return headerValue;
    }
}