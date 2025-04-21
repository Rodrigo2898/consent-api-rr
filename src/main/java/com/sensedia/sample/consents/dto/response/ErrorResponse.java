package com.sensedia.sample.consents.dto.response;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ErrorResponse(String apiPath,
                            HttpStatus errorCode,
                            String errorMessage,
                            LocalDateTime errorTime) {
}
