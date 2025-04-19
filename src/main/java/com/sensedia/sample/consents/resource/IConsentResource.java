package com.sensedia.sample.consents.resource;

import com.sensedia.sample.consents.dto.request.CreateConsent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface IConsentResource {

	@PostMapping
	ResponseEntity<CreateConsent> create(@RequestBody CreateConsent createConsent);

	@GetMapping(produces = { "application/json" })
	ResponseEntity<Object> findAll();
}
