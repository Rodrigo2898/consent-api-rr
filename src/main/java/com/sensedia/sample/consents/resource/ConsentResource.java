package com.sensedia.sample.consents.resource;

import com.sensedia.sample.consents.dto.request.CreateConsent;
import com.sensedia.sample.consents.dto.request.UpdateConsent;
import com.sensedia.sample.consents.dto.response.ConsentAuditResponse;
import com.sensedia.sample.consents.dto.response.ConsentResponse;
import com.sensedia.sample.consents.dto.response.ErrorResponse;
import com.sensedia.sample.consents.service.IConsentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(
		name = "CRUD para REST API de Consentimentos",
		description = "CRUD - to CREATE, READ, UPDATE e DELETE consents"
)
@RestController
@RequestMapping("/consents")
@Slf4j
public class ConsentResource implements IConsentResource {

	private final IConsentService consentService;

	public ConsentResource(IConsentService consentService) {
        this.consentService = consentService;
	}

	@Operation(
			summary = "Criar Consentimento",
			description = "Endpoint para criar um novo consentimento"
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "201",
					description = "Consentimento criado com sucesso",
					content = @Content(
							schema = @Schema(implementation = ConsentResponse.class)
					)
			),
			@ApiResponse(
					responseCode = "400",
					description = "Dados de entrada inválidos",
					content = @Content(
							schema = @Schema(implementation = ErrorResponse.class)
					)
			),
			@ApiResponse(
					responseCode = "500",
					description = "Erro interno no servidor",
					content = @Content(
							schema = @Schema(implementation = ErrorResponse.class)
					)
			)
	})
    @Override
	public ResponseEntity<ConsentResponse> create(CreateConsent createConsent) {
		log.info("Criando novo consentimento");
		ConsentResponse response = consentService.saveConsent(createConsent);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}


	@Operation(
			summary = "Listar todos os Consentimentos",
			description = "Endpoint para recuperar todos os consentimentos cadastrados"
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "Lista de consentimentos retornada com sucesso",
					content = @Content(
							schema = @Schema(implementation = ConsentResponse[].class)
					)
			),
			@ApiResponse(
					responseCode = "500",
					description = "Erro interno no servidor",
					content = @Content(
							schema = @Schema(implementation = ErrorResponse.class)
					)
			)
	})
	@Override
	public ResponseEntity<List<ConsentResponse>> findAll() {
		log.info("Consultando todos os consentimentos");
		return ResponseEntity.ok().body(consentService.getAll());
	}


	@Operation(
			summary = "Buscar Consentimento por ID",
			description = "Endpoint para recuperar um consentimento específico pelo seu ID"
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "Consentimento encontrado e retornado",
					content = @Content(
							schema = @Schema(implementation = ConsentResponse.class)
					)
			),
			@ApiResponse(
					responseCode = "404",
					description = "Consentimento não encontrado",
					content = @Content(
							schema = @Schema(implementation = ErrorResponse.class)
					)
			),
			@ApiResponse(
					responseCode = "400",
					description = "ID inválido",
					content = @Content(
							schema = @Schema(implementation = ErrorResponse.class)
					)
			),
			@ApiResponse(
					responseCode = "500",
					description = "Erro interno no servidor",
					content = @Content(
							schema = @Schema(implementation = ErrorResponse.class)
					)
			)
	})
	@Override
	public ResponseEntity<ConsentResponse> findByConsentId(String id) {
		log.info("Buscando consentimento: {}", id);
		return ResponseEntity.ok().body(consentService.getConsentById(id));
	}


	@Operation(
			summary = "Atualizar Consentimento",
			description = "Endpoint para atualizar um consentimento existente"
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "Consentimento atualizado com sucesso",
					content = @Content(
							schema = @Schema(implementation = ConsentResponse.class)
					)
			),
			@ApiResponse(
					responseCode = "400",
					description = "Dados de entrada inválidos",
					content = @Content(
							schema = @Schema(implementation = ErrorResponse.class)
					)
			),
			@ApiResponse(
					responseCode = "404",
					description = "Consentimento não encontrado",
					content = @Content(
							schema = @Schema(implementation = ErrorResponse.class)
					)
			),
			@ApiResponse(
					responseCode = "500",
					description = "Erro interno no servidor",
					content = @Content(
							schema = @Schema(implementation = ErrorResponse.class)
					)
			)
	})
	@Override
	public ResponseEntity<ConsentResponse> update(String id, UpdateConsent consent) {
		log.info("Consentimento {} foi atualizado", id);
		return ResponseEntity.ok().body(consentService.updateConsent(id, consent));
	}


	@Operation(
			summary = "Excluir Consentimento",
			description = "Endpoint para excluir um consentimento existente"
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "204",
					description = "Consentimento excluído com sucesso"
			),
			@ApiResponse(
					responseCode = "404",
					description = "Consentimento não encontrado",
					content = @Content(
							schema = @Schema(implementation = ErrorResponse.class)
					)
			),
			@ApiResponse(
					responseCode = "400",
					description = "ID inválido",
					content = @Content(
							schema = @Schema(implementation = ErrorResponse.class)
					)
			),
			@ApiResponse(
					responseCode = "500",
					description = "Erro interno no servidor",
					content = @Content(
							schema = @Schema(implementation = ErrorResponse.class)
					)
			)
	})
	@Override
	public ResponseEntity<Void> delete(String id) {
		log.info("Deletando consentimento: {}", id);
		consentService.deleteConsent(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}


	@Operation(
			summary = "Consultar Histórico de Alterações",
			description = "Endpoint para recuperar o histórico de alterações de um consentimento"
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "Histórico retornado com sucesso",
					content = @Content(
							schema = @Schema(implementation = ConsentAuditResponse[].class)
					)
			),
			@ApiResponse(
					responseCode = "404",
					description = "Consentimento não encontrado"
			)
	})
	@Override
	public ResponseEntity<List<ConsentAuditResponse>> getConsentHistory(String id) {
		log.info("Consultando histórico do consentimento: {}", id);
		return ResponseEntity.ok(consentService.getConsentHistory(id));
	}
}
