package com.sensedia.sample.consents.resource;

import com.sensedia.sample.consents.dto.request.CreateConsent;
import com.sensedia.sample.consents.dto.response.ConsentResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IConsentResource {

	@PostMapping
	ResponseEntity<CreateConsent> create(@Valid @RequestBody CreateConsent createConsent);

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<List<ConsentResponse>> findAll();
}
