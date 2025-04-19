package com.sensedia.sample.consents.service.impl;

import com.sensedia.sample.consents.domain.document.Consent;
import com.sensedia.sample.consents.domain.enums.ConsentStatus;
import com.sensedia.sample.consents.dto.request.CreateConsent;
import com.sensedia.sample.consents.dto.response.ConsentResponse;
import com.sensedia.sample.consents.mapper.ConsentMapper;
import com.sensedia.sample.consents.repository.ConsentRepository;
import com.sensedia.sample.consents.service.IConsentService;
import com.sensedia.sample.consents.service.exceptions.ConsentNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ConsentServiceImpl implements IConsentService {

    private final ConsentRepository consentRepository;
    private final ConsentMapper consentMapper;

    public ConsentServiceImpl(ConsentRepository consentRepository, ConsentMapper consentMapper) {
        this.consentRepository = consentRepository;
        this.consentMapper = consentMapper;
    }

    @Override
    public void saveConsent(CreateConsent request) {
        Consent consent = consentMapper.toDocument(request);
        consent.setCreationDateTime(LocalDateTime.now());
        consentRepository.save(consent);
    }

    @Override
    public List<ConsentResponse> getAll() {
        return consentMapper.toResponseList(consentRepository.findAll());
    }

    @Override
    public ConsentResponse getConsentById(String id) {
        Consent consent = consentRepository.findById(id)
                .orElseThrow(() -> new ConsentNotFoundException("Consent not found"));
        return consentMapper.toResponse(consent);
    }

    @Override
    public ConsentResponse updateConsent(String id, CreateConsent request) {
        return null;
    }

    @Override
    public void deleteConsent(String id) {

    }


}
