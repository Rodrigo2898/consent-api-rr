package com.sensedia.sample.consents.domain.document;

import com.sensedia.sample.consents.domain.enums.ConsentStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "consent")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Consent {

    @Id
    private UUID id;
    @CPF(message = "CPF inválido")
    @NotBlank
    private String cpf;
    private ConsentStatus status;
    private LocalDateTime creationDateTime;
    private LocalDateTime expirationDateTime;
    @Size(min = 1, max = 50)
    private String additionalInfo;
}
