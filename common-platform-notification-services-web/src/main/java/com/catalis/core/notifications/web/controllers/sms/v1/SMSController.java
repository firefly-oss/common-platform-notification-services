package com.catalis.core.notifications.web.controllers.sms.v1;

import com.catalis.core.notifications.core.services.sms.v1.SMSServiceImpl;
import com.catalis.core.notifications.interfaces.dtos.sms.v1.SMSRequestDTO;
import com.catalis.core.notifications.interfaces.dtos.sms.v1.SMSResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/sms")
@Tag(name = "SMS Controller", description = "API for managing SMS-related operations.")
public class SMSController {

    @Autowired
    private SMSServiceImpl service;

    @Operation(
            summary = "Send an SMS message",
            description = "Endpoint to send an SMS message using the specified phone number and text message.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Details of the SMS to be sent.",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SMSRequestDTO.class),
                            examples = @ExampleObject(value = "{\"phoneNumber\": \"+1234567890\", \"message\": \"Hello, World!\"}")
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "SMS sent successfully.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SMSResponseDTO.class),
                            examples = @ExampleObject(value = "{\"messageId\": \"12345\", \"status\": \"SENT\", \"timestamp\": 1696433658000}")
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request details. Ensure that the phone number and message are valid.",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"status\": \"FAILED\", \"errorMessage\": \"Invalid phone number.\", \"timestamp\": 1696433658000}")
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error.",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"status\": \"FAILED\", \"errorMessage\": \"Provider service unavailable.\", \"timestamp\": 1696433658000}")
                    )
            )
    })
    @PostMapping("/send")
    public Mono<ResponseEntity<SMSResponseDTO>> sendSMS(
            @RequestBody SMSRequestDTO request
    ) {
        return service.sendSMS(request)
                .map(ResponseEntity::ok)
                .doOnNext(response -> System.out.println("SMS response: " + response.getBody()));
    }
}