package com.sensedia.sample.consents.service;

import com.sensedia.sample.consents.dto.request.CreateConsent;
import com.sensedia.sample.consents.dto.response.ConsentResponse;

import java.util.List;

public interface IConsentService {

    void saveConsent(CreateConsent request);

    List<ConsentResponse> getAll();

    ConsentResponse getConsentById(String id);

    ConsentResponse updateConsent(String id, CreateConsent request);

    void deleteConsent(String id);
}
