package com.sensedia.sample.consents.dto.response;

import com.sensedia.sample.consents.domain.enums.ConsentStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record ConsentResponse(UUID id,
                              String cpf,
                              ConsentStatus status,
                              LocalDateTime creationDateTime,
                              LocalDateTime expirationDateTime,
                              String additionalInfo) {
}
