package com.sensedia.sample.consents.service.impl;

import com.sensedia.sample.consents.domain.document.Consent;
import com.sensedia.sample.consents.dto.request.CreateConsent;
import com.sensedia.sample.consents.dto.request.UpdateConsent;
import com.sensedia.sample.consents.dto.response.ConsentAuditResponse;
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
    private final ConsentAuditServiceImpl auditService;
    private final ConsentMapper consentMapper;

    public ConsentServiceImpl(ConsentRepository consentRepository, ConsentAuditServiceImpl auditService, ConsentMapper consentMapper) {
        this.consentRepository = consentRepository;
        this.auditService = auditService;
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
                .orElseThrow(() -> new ConsentNotFoundException("Consentimento não encontrado"));
        return consentMapper.toResponse(consent);
    }

    @Override
    public ConsentResponse updateConsent(String id, UpdateConsent dto) {
        Consent existingConsent = consentRepository.findById(id)
                .orElseThrow(() -> new ConsentNotFoundException(id));

        // Salva estado anterior para auditoria
        Consent previousState = new Consent(existingConsent);

        existingConsent.setStatus(dto.status());
        existingConsent.setExpirationDateTime(dto.expirationDateTime());
        existingConsent.setAdditionalInfo(dto.additionalInfo());

        Consent updatedConsent = consentRepository.save(existingConsent);

        // Registra a alteração no histórico
        auditService.recordUpdate(previousState, updatedConsent, "system_or_user_id");

        return consentMapper.toResponse(updatedConsent);
    }

    @Override
    public void deleteConsent(String id) {
        Consent consent = consentRepository.findById(id)
                .orElseThrow(() -> new ConsentNotFoundException("Consentimento não encontrado"));
        consentRepository.delete(consent);

        // Registra a exclusão no histórico
        auditService.recordDelete(consent, "system_or_user_id", "User requested deletion");
    }


    @Override
    public List<ConsentAuditResponse> getConsentHistory(String consentId) {
        if (!consentRepository.existsById(consentId)) {
            throw new ConsentNotFoundException(consentId);
        }

        return auditService.getConsentHistory(consentId).stream()
                .map(consentMapper::toAuditResponse)
                .toList();
    }
}
