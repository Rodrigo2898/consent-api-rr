package com.sensedia.sample.consents.service.impl;

import com.sensedia.sample.consents.domain.document.Consent;
import com.sensedia.sample.consents.dto.request.CreateConsent;
import com.sensedia.sample.consents.dto.request.UpdateConsent;
import com.sensedia.sample.consents.dto.response.ConsentResponse;
import com.sensedia.sample.consents.exceptions.ConsentNotFoundException;
import com.sensedia.sample.consents.mapper.ConsentMapper;
import com.sensedia.sample.consents.repository.ConsentRepository;
import com.sensedia.sample.consents.exceptions.InvalidConsentDataException;
import com.sensedia.sample.consents.service.IConsentService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConsentServiceImpl implements IConsentService {

    private final ConsentRepository consentRepository;
    private final ConsentMapper consentMapper;

    public ConsentServiceImpl(ConsentRepository consentRepository, ConsentMapper consentMapper) {
        this.consentRepository = consentRepository;
        this.consentMapper = consentMapper;
    }

    @Override
    public ConsentResponse saveConsent(CreateConsent dto) {
        if (dto == null || dto.status() == null || dto.expirationDateTime() == null) {
            throw new InvalidConsentDataException("Status e data de expiração são obrigatórios.");
        }

        Consent consent = consentMapper.toDocument(dto);
        consent.setCreationDateTime(LocalDateTime.now());
        return consentMapper.toResponse(consentRepository.save(consent));
    }

    @Override
    public List<ConsentResponse> getAll() {
        return consentMapper.toResponseList(consentRepository.findAll());
    }

    @Override
    public ConsentResponse getConsentById(String id) {
        Consent consent = consentRepository.findById(id)
                .orElseThrow(() -> new ConsentNotFoundException("Consentimento com ID " + id + " não encontrado"));
        return consentMapper.toResponse(consent);
    }

    @Override
    public ConsentResponse updateConsent(String id, UpdateConsent dto) {
        Consent consent = consentRepository.findById(id)
                .orElseThrow(() -> new ConsentNotFoundException("Consentimento com ID " + id + " não encontrado"));

        if (dto == null || dto.status() == null || dto.expirationDateTime() == null) {
            throw new InvalidConsentDataException("Status e data de expiração são obrigatórios para atualizar o consentimento.");
        }

        consent.setStatus(dto.status());
        consent.setExpirationDateTime(dto.expirationDateTime());
        consent.setAdditionalInfo(dto.additionalInfo());

        return consentMapper.toResponse(consentRepository.save(consent));
    }

    @Override
    public void deleteConsent(String id) {
        Consent consent = consentRepository.findById(id)
                .orElseThrow(() -> new ConsentNotFoundException("Consentimento com ID " + id + " não encontrado"));
        consentRepository.delete(consent);
    }
}
