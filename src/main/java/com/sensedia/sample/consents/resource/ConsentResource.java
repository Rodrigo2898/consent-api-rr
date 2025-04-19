package com.sensedia.sample.consents.resource;

import com.sensedia.sample.consents.dto.request.CreateConsent;
import com.sensedia.sample.consents.service.IConsentService;
import org.springframework.http.ResponseEntity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consents")
@Slf4j
public class ConsentResource implements IConsentResource {

	private final IConsentService consentService;

    public ConsentResource(IConsentService consentService) {
        this.consentService = consentService;
    }

    @Override
	public ResponseEntity<CreateConsent> create(CreateConsent createConsent) {
		consentService.saveConsent(createConsent);
		return ResponseEntity.ok().body(createConsent);
	}

	@Override
	public ResponseEntity<Object> findAll() {
		log.info("Requisição recebida!!!!!!");
		return ResponseEntity.badRequest().body("Dummy");
	}

}
