package com.sensedia.sample.consents.domain.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Document(collection = "consent_audit")
public class ConsentAudit {

    @Id
    private String id;
    private String consentId;
    private String action;
    private LocalDateTime timestamp;
    private String changedBy;
    private Map<String, Object> previousState;
    private Map<String, Object> newState;
    private String reason;
}