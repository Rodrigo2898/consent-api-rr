package com.sensedia.sample.consents.dto.request;

import com.sensedia.sample.consents.domain.enums.ConsentStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDateTime;

public record ConsentRequest(@NotBlank @CPF String cpf,
                             ConsentStatus status,
                             @Size(min = 1, max = 50) LocalDateTime expirationDateTime) {
}
