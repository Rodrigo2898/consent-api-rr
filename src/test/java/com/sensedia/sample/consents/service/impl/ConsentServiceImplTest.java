package com.sensedia.sample.consents.service.impl;

import com.sensedia.sample.consents.domain.document.Consent;
import com.sensedia.sample.consents.domain.enums.ConsentStatus;
import com.sensedia.sample.consents.dto.request.UpdateConsent;
import com.sensedia.sample.consents.factory.ConsentFactory;
import com.sensedia.sample.consents.mapper.ConsentMapper;
import com.sensedia.sample.consents.repository.ConsentRepository;
import com.sensedia.sample.consents.service.exceptions.ConsentNotFoundException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsentServiceImplTest {

    @Mock
    private ConsentRepository consentRepository;

    @Mock
    private ConsentMapper consentMapper;

    @InjectMocks
    private ConsentServiceImpl consentService;

    @Nested
    class SaveConsent {

        @Test
        void shouldCallRepositorySave() {
            // Arrange
            var dto = ConsentFactory.buildCreateConsent();
            var consent = ConsentFactory.buildConsent(dto);
            when(consentMapper.toDocument(dto)).thenReturn(consent);

            // Act
            consentService.saveConsent(dto);

            // Assert
            verify(consentRepository, times(1)).save(consent);
        }

        @Test
        void shouldCreateConsentSuccessfully() {
            // Arrange
            var dto = ConsentFactory.buildCreateConsent();
            var consent = ConsentFactory.buildConsent(dto);
            when(consentMapper.toDocument(dto)).thenReturn(consent);

            ArgumentCaptor<Consent> captor = ArgumentCaptor.forClass(Consent.class);

            // Act
            consentService.saveConsent(dto);

            // Assert
            verify(consentRepository).save(captor.capture());
            verifyNoMoreInteractions(consentRepository);

            var captorValue = captor.getValue();

            assertNotNull(captorValue);
            assertEquals(captorValue.getCpf(), dto.cpf());
            assertEquals(captorValue.getStatus(), dto.status());
            assertEquals(captorValue.getExpirationDateTime(), dto.expirationDateTime());
            assertEquals(captorValue.getAdditionalInfo(), dto.additionalInfo());
            assertNotNull(captorValue.getCreationDateTime());
        }
    }

    @Nested
    class GetAllConsents {

        @Test
        void shouldCallRepositoryGetAll() {
            // Arrange
            var consent = ConsentFactory.buildConsent(ConsentFactory.buildCreateConsent());
            consent.setId(UUID.randomUUID().toString());
            consent.setCreationDateTime(LocalDateTime.now().minusDays(1));

            var consentList = List.of(consent);
            var responseList = List.of(ConsentFactory.buildConsentResponse());

            when(consentRepository.findAll()).thenReturn(consentList);
            when(consentMapper.toResponseList(consentList)).thenReturn(responseList);

            // Act
            var result = consentService.getAll();

            // Assert
            verify(consentRepository, times(1)).findAll();
            verify(consentMapper).toResponseList(consentList);
        }

        @Test
        void shouldReturnAllConsentsSuccessfully() {
            // Arrange
            var consent = ConsentFactory.buildConsent(ConsentFactory.buildCreateConsent());
            consent.setId(UUID.randomUUID().toString());
            consent.setCreationDateTime(LocalDateTime.now().minusDays(1));

            var consentList = List.of(consent);
            var responseList = List.of(ConsentFactory.buildConsentResponse());

            when(consentRepository.findAll()).thenReturn(consentList);
            when(consentMapper.toResponseList(consentList)).thenReturn(responseList);

            // Act
            var result = consentService.getAll();

            // Assert
            verify(consentRepository).findAll();
            verifyNoMoreInteractions(consentRepository);

            assertNotNull(result);
            assertEquals(responseList.size(), result.size());
            assertEquals(responseList, result);
        }
    }

    @Nested
    class GetConsentById {

        @Test
        void shouldCallRepositoryGetConsentById() {
            // Arrange
            var id = UUID.randomUUID().toString();
            var consent = ConsentFactory.buildConsent(ConsentFactory.buildCreateConsent());
            consent.setId(id);
            consent.setCreationDateTime(LocalDateTime.now());

            var response = ConsentFactory.buildConsentResponse();

            when(consentRepository.findById(id)).thenReturn(Optional.of(consent));
            when(consentMapper.toResponse(consent)).thenReturn(response);

            // Act
            var result = consentService.getConsentById(id);

            // Assert
            verify(consentRepository, times(1)).findById(id);
            verify(consentMapper).toResponse(consent);
        }

        @Test
        void shouldReturnConsentByIdSuccessfully() {
            // Arrange
            var id = UUID.randomUUID().toString();
            var consent = ConsentFactory.buildConsent(ConsentFactory.buildCreateConsent());
            consent.setId(id);
            consent.setCreationDateTime(LocalDateTime.now());

            var response = ConsentFactory.buildConsentResponse();

            when(consentRepository.findById(id)).thenReturn(Optional.of(consent));
            when(consentMapper.toResponse(consent)).thenReturn(response);

            // Act
            var result = consentService.getConsentById(id);

            // Assert
            verify(consentRepository).findById(id);
            verifyNoMoreInteractions(consentRepository);

            assertNotNull(result);
            assertEquals(response, result);
            assertEquals(response.cpf(), result.cpf());
            assertEquals(response.status(), result.status());
            assertEquals(response.creationDateTime(), result.creationDateTime());
            assertEquals(response.expirationDateTime(), result.expirationDateTime());
            assertEquals(response.additionalInfo(), result.additionalInfo());
        }

        @Test
        void shouldThrowExceptionWhenConsentNotFound() {
            // Arrange
            String id = UUID.randomUUID().toString();
            when(consentRepository.findById(id)).thenReturn(Optional.empty());

            // Act
            var exception = assertThrows(ConsentNotFoundException.class, () -> {
                consentService.getConsentById(id);
            });

            // Assert
            assertEquals("Consentimento não encontrado", exception.getMessage());
            verify(consentRepository).findById(id);
            verifyNoMoreInteractions(consentMapper);
        }
    }

    @Nested
    class Update {

        @Test
        void shouldCallRepositoryUpdateConsent() {
            // Arrange
            var id = UUID.randomUUID().toString();
            var existingConsent = ConsentFactory.buildConsent(ConsentFactory.buildCreateConsent());
            existingConsent.setId(id);

            var dto = new UpdateConsent(
                    ConsentStatus.REVOKED,
                    LocalDateTime.parse("2025-12-01T00:00:00"),
                    "Atualização de consentimento"
            );

            var updatedConsent = Consent.builder()
                    .id(id)
                    .cpf(existingConsent.getCpf())
                    .status(dto.status())
                    .expirationDateTime(dto.expirationDateTime())
                    .additionalInfo(dto.additionalInfo())
                    .creationDateTime(existingConsent.getCreationDateTime())
                    .build();

            var expectedResponse = ConsentFactory.buildConsentResponse();

            when(consentRepository.findById(id)).thenReturn(Optional.of(existingConsent));
            when(consentRepository.save(any(Consent.class))).thenReturn(updatedConsent);
            when(consentMapper.toResponse(updatedConsent)).thenReturn(expectedResponse);

            // Act
            var result = consentService.updateConsent(id, dto);

            // Assert
            verify(consentRepository, times(1)).findById(id);
            verify(consentRepository, times(1)).save(updatedConsent);
            verify(consentMapper).toResponse(updatedConsent);
        }

        @Test
        void shouldUpdateConsentSuccessfully() {
            // Arrange
            var id = UUID.randomUUID().toString();
            var existingConsent = ConsentFactory.buildConsent(ConsentFactory.buildCreateConsent());
            existingConsent.setId(id);

            var dto = new UpdateConsent(
                    ConsentStatus.REVOKED,
                    LocalDateTime.parse("2025-12-01T00:00:00"),
                    "Atualização de consentimento"
            );

            var updatedConsent = Consent.builder()
                    .id(id)
                    .cpf(existingConsent.getCpf())
                    .status(dto.status())
                    .expirationDateTime(dto.expirationDateTime())
                    .additionalInfo(dto.additionalInfo())
                    .creationDateTime(existingConsent.getCreationDateTime())
                    .build();

            var expectedResponse = ConsentFactory.buildConsentResponse();

            when(consentRepository.findById(id)).thenReturn(Optional.of(existingConsent));
            when(consentRepository.save(any(Consent.class))).thenReturn(updatedConsent);
            when(consentMapper.toResponse(updatedConsent)).thenReturn(expectedResponse);

            // Act
            var result = consentService.updateConsent(id, dto);

            // Assert
            verify(consentRepository).findById(id);
            verify(consentRepository).save(existingConsent);
            verifyNoMoreInteractions(consentRepository);

            assertNotNull(result);
            assertEquals(expectedResponse, result);
            assertEquals(expectedResponse.cpf(), result.cpf());
            assertEquals(expectedResponse.status(), result.status());
            assertEquals(expectedResponse.creationDateTime(), result.creationDateTime());
            assertEquals(expectedResponse.expirationDateTime(), result.expirationDateTime());
            assertEquals(expectedResponse.additionalInfo(), result.additionalInfo());
        }

        @Test
        void shouldThrowExceptionWhenUpdatingNonExistingConsent() {
            // Arrange
            var id = UUID.randomUUID().toString();
            var dto = new UpdateConsent(ConsentStatus.REVOKED, LocalDateTime.now(), "Motivo");

            when(consentRepository.findById(id)).thenReturn(Optional.empty());

            // Act
            var exception = assertThrows(ConsentNotFoundException.class, () -> {
                consentService.updateConsent(id, dto);
            });

            // Assert
            assertEquals("Consentimento não encontrado", exception.getMessage());
            verify(consentRepository).findById(id);
            verifyNoMoreInteractions(consentRepository);
            verifyNoInteractions(consentMapper);
        }

    }
}