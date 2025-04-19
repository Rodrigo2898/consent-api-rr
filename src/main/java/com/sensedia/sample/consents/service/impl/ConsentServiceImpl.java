package com.sensedia.sample.consents.service.impl;

import com.sensedia.sample.consents.domain.document.Consent;
import com.sensedia.sample.consents.domain.enums.ConsentStatus;
import com.sensedia.sample.consents.dto.request.ConsentRequest;
import com.sensedia.sample.consents.mapper.ConsentMapper;
import com.sensedia.sample.consents.repository.ConsentRepository;
import com.sensedia.sample.consents.service.ConsentService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ConsentServiceImpl implements ConsentService {

    private final ConsentRepository consentRepository;
    private final ConsentMapper consentMapper;

    public ConsentServiceImpl(ConsentRepository consentRepository, ConsentMapper consentMapper) {
        this.consentRepository = consentRepository;
        this.consentMapper = consentMapper;
    }

    @Override
    public void saveConsent(ConsentRequest request) {
        Consent consent = consentMapper.toDocument(request);
        consent.setStatus(ConsentStatus.ACTIVE);
        consent.setCreationDateTime(LocalDateTime.now());
        consentRepository.save(consent);
    }
}
