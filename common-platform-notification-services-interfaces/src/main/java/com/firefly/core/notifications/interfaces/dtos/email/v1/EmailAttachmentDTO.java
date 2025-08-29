package com.firefly.core.notifications.interfaces.dtos.email.v1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailAttachmentDTO {
    private String filename;

    /**
     * Byte content of the file to be attached.
     */
    private byte[] content;

    /**
     * MIME type of the attachment (e.g., "application/pdf").
     */
    private String contentType;
}