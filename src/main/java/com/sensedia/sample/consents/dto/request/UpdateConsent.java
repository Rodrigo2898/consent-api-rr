package com.sensedia.sample.consents.dto.request;

import com.sensedia.sample.consents.domain.enums.ConsentStatus;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record UpdateConsent(ConsentStatus status,
                            LocalDateTime expirationDateTime,
                            @Size(min = 1, max = 50) String additionalInfo) {
}
