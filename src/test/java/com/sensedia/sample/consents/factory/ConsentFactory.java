package com.sensedia.sample.consents.factory;

import com.sensedia.sample.consents.domain.document.Consent;
import com.sensedia.sample.consents.domain.enums.ConsentStatus;
import com.sensedia.sample.consents.dto.request.CreateConsent;

import java.time.LocalDateTime;

public class ConsentFactory {

    public static CreateConsent buildCreateConsent() {
        return new CreateConsent("123.456.789-00",
                ConsentStatus.ACTIVE,
                LocalDateTime.parse("2025-12-31T23:59:59"),
                "Consentimento para uso de dados pessoais");
    }

    public static Consent buildConsent(CreateConsent dto) {
        return Consent.builder()
                .cpf(dto.cpf())
                .status(dto.status())
                .expirationDateTime(dto.expirationDateTime())
                .additionalInfo(dto.additionalInfo())
                .build();
    }
}
