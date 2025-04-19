package com.sensedia.sample.consents.resource;

import com.sensedia.sample.consents.dto.request.CreateConsent;
import com.sensedia.sample.consents.dto.response.ConsentResponse;
import com.sensedia.sample.consents.service.IConsentService;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/consents")
@Slf4j
public class ConsentResource implements IConsentResource {

	private final IConsentService consentService;
	private final ServerProperties serverProperties;

	public ConsentResource(IConsentService consentService, ServerProperties serverProperties) {
        this.consentService = consentService;
		this.serverProperties = serverProperties;
	}

    @Override
	public ResponseEntity<CreateConsent> create(CreateConsent createConsent) {
		log.info("Criando novo consentimento");
		consentService.saveConsent(createConsent);
		return ResponseEntity.status(HttpStatus.CREATED).body(createConsent);
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
}
