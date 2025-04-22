package com.sensedia.sample.consents.service;

import com.sensedia.sample.consents.dto.request.CreateConsent;
import com.sensedia.sample.consents.dto.request.UpdateConsent;
import com.sensedia.sample.consents.dto.response.ConsentAuditResponse;
import com.sensedia.sample.consents.dto.response.ConsentResponse;

import java.util.List;

public interface IConsentService {

    ConsentResponse saveConsent(CreateConsent dto);

    List<ConsentResponse> getAll();

    ConsentResponse getConsentById(String id);

    ConsentResponse updateConsent(String id, UpdateConsent dto);

    void deleteConsent(String id);

    List<ConsentAuditResponse> getConsentHistory(String consentId);
}
