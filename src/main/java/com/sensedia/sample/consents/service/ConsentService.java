package com.sensedia.sample.consents.service;

import com.sensedia.sample.consents.domain.document.Consent;
import com.sensedia.sample.consents.dto.request.ConsentRequest;

public interface ConsentService {

    void saveConsent(ConsentRequest request);
}
