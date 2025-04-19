package com.sensedia.sample.consents.dto.response;

import com.sensedia.sample.consents.domain.enums.ConsentStatus;

import java.time.LocalDateTime;

public record ConsentResponse(String id,
                              String cpf,
                              ConsentStatus status,
                              LocalDateTime creationDateTime,
                              LocalDateTime expirationDateTime,
                              String additionalInfo) {
}
