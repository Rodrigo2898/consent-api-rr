package com.sensedia.sample.consents.repository;

import com.sensedia.sample.consents.domain.document.Consent;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ConsentRepository extends MongoRepository<Consent, String> {
}
