package com.sensedia.sample.consents.dto;

import com.sensedia.sample.consents.domain.enums.ConsentStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record ConsentDTO(String id,
                         String cpf,
                         ConsentStatus status,
                         LocalDateTime creationDateTime,
                         LocalDateTime expirationDateTime,
                         String additionalInfo) {
}
