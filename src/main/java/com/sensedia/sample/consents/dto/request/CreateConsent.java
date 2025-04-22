package com.sensedia.sample.consents.dto.request;

import com.sensedia.sample.consents.domain.enums.ConsentStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDateTime;

public record CreateConsent(@NotBlank @CPF(message = "CPF inválido") String cpf,
                            @NotNull ConsentStatus status,
                            @NotNull LocalDateTime expirationDateTime,
                            @Size(min = 1, max = 50) String additionalInfo) {
}
