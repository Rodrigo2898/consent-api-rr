package com.sensedia.sample.consents.rest;

import org.springframework.http.ResponseEntity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ConsentApi implements IConsentApi {

	@Override
	public ResponseEntity<Object> findAll() {
		log.info("Requisição recebida!!!!!!");
		return ResponseEntity.badRequest().body("Dummy");
	}

}
