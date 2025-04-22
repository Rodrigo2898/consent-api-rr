package com.sensedia.sample.consents.resource;

import com.sensedia.sample.consents.dto.request.CreateConsent;
import com.sensedia.sample.consents.dto.request.UpdateConsent;
import com.sensedia.sample.consents.dto.response.ConsentAuditResponse;
import com.sensedia.sample.consents.dto.response.ConsentResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface IConsentResource {

	@PostMapping
	ResponseEntity<ConsentResponse> create(@Valid @RequestBody CreateConsent createConsent);

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<List<ConsentResponse>> findAll();

	@GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ConsentResponse> findByConsentId(@PathVariable String id);

	@PutMapping(value = "/{id}")
	ResponseEntity<ConsentResponse> update(@PathVariable String id, @Valid @RequestBody UpdateConsent consent);

	@DeleteMapping(value = "/{id}")
	ResponseEntity<Void> delete(@PathVariable String id);

	@GetMapping("/{id}/history")
	public ResponseEntity<List<ConsentAuditResponse>> getConsentHistory(@PathVariable String id);
}
