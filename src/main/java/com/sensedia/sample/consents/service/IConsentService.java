package com.sensedia.sample.consents.service;

import com.sensedia.sample.consents.dto.request.ConsentRequest;
import com.sensedia.sample.consents.dto.response.ConsentResponse;

import java.util.List;
import java.util.UUID;

public interface IConsentService {

    void saveConsent(ConsentRequest request);

    List<ConsentResponse> getAll();

    ConsentResponse getConsentById(UUID id);

    ConsentResponse updateConsent(UUID id, ConsentRequest request);

    void deleteConsent(UUID id);
}
