package com.sensedia.sample.consents.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public interface IConsentResource {

	@GetMapping(value = "/consents", produces = { "application/json" })
	ResponseEntity<Object> findAll();
}
