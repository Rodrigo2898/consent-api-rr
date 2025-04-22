package com.sensedia.sample.consents.service.impl;

import com.sensedia.sample.consents.domain.document.Consent;
import com.sensedia.sample.consents.domain.document.ConsentAudit;
import com.sensedia.sample.consents.repository.ConsentAuditRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ConsentAuditServiceImpl {
    private final ConsentAuditRepository auditRepository;

    public ConsentAuditServiceImpl(ConsentAuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    public void recordUpdate(Consent previousState, Consent newState, String changedBy) {
        ConsentAudit audit = new ConsentAudit();
        audit.setConsentId(previousState.getId());
        audit.setAction("UPDATE");
        audit.setTimestamp(LocalDateTime.now());
        audit.setChangedBy(changedBy);
        audit.setPreviousState(toMap(previousState));
        audit.setNewState(toMap(newState));

        auditRepository.save(audit);
    }

    public void recordDelete(Consent deletedConsent, String changedBy, String reason) {
        ConsentAudit audit = new ConsentAudit();
        audit.setConsentId(deletedConsent.getId());
        audit.setAction("DELETE");
        audit.setTimestamp(LocalDateTime.now());
        audit.setChangedBy(changedBy);
        audit.setPreviousState(toMap(deletedConsent));
        audit.setReason(reason);

        auditRepository.save(audit);
    }

    public List<ConsentAudit> getConsentHistory(String consentId) {
        return auditRepository.findByConsentIdOrderByTimestampDesc(consentId);
    }

    private Map<String, Object> toMap(Consent consent) {
        Map<String, Object> map = new HashMap<>();
        map.put("cpf", consent.getCpf());
        map.put("status", consent.getStatus());
        map.put("creationDateTime", consent.getCreationDateTime());
        map.put("expirationDateTime", consent.getExpirationDateTime());
        map.put("additionalInfo", consent.getAdditionalInfo());
        return map;
    }
}
