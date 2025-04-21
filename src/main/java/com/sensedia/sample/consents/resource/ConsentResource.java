package com.sensedia.sample.consents.resource;

import com.sensedia.sample.consents.dto.ConsentDTO;
import com.sensedia.sample.consents.dto.request.CreateConsent;
import com.sensedia.sample.consents.dto.request.UpdateConsent;
import com.sensedia.sample.consents.dto.response.ConsentResponse;
import com.sensedia.sample.consents.service.IConsentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/consents")
@Slf4j
public class ConsentResource implements IConsentResource {

	private final IConsentService consentService;

	public ConsentResource(IConsentService consentService) {
        this.consentService = consentService;
	}

    @Override
	public ResponseEntity<ConsentResponse> create(CreateConsent createConsent) {
		log.info("Criando novo consentimento");
		ConsentResponse response = consentService.saveConsent(createConsent);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@Override
	public ResponseEntity<List<ConsentResponse>> findAll() {
		log.info("Consultando todos os consentimentos");
		return ResponseEntity.ok().body(consentService.getAll());
	}

	@Override
	public ResponseEntity<ConsentResponse> findByConsentId(String id) {
		log.info("Buscando consentimento: {}", id);
		return ResponseEntity.ok().body(consentService.getConsentById(id));
	}

	@Override
	public ResponseEntity<ConsentResponse> update(String id, UpdateConsent consent) {
		log.info("Consentimento {} foi atualizado", id);
		return ResponseEntity.ok().body(consentService.updateConsent(id, consent));
	}

	@Override
	public ResponseEntity<Void> delete(String id) {
		log.info("Deletando consentimento: {}", id);
		consentService.deleteConsent(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
