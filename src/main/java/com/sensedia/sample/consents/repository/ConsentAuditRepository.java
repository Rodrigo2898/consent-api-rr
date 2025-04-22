package com.sensedia.sample.consents.repository;

import com.sensedia.sample.consents.domain.document.ConsentAudit;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ConsentAuditRepository extends MongoRepository<ConsentAudit, String> {
    List<ConsentAudit> findByConsentId(String consentId);
    List<ConsentAudit> findByConsentIdOrderByTimestampDesc(String consentId);
}
