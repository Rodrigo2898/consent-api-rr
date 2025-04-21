package com.sensedia.sample.consents.resource;

import com.sensedia.sample.consents.domain.enums.ConsentStatus;
import com.sensedia.sample.consents.dto.request.CreateConsent;
import com.sensedia.sample.consents.dto.request.UpdateConsent;
import com.sensedia.sample.consents.factory.ConsentFactory;
import com.sensedia.sample.consents.service.IConsentService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsentResourceTest {

    @Mock
    private IConsentService consentService;

    @InjectMocks
    private ConsentResource consentResource;

    @Captor
    ArgumentCaptor<CreateConsent> createConsentArgumentCaptor;

    @Captor
    ArgumentCaptor<String> consentIdArgumentCaptor;

    @Nested
    class CreateConsentResource {

        @Test
        void shouldReturnHttpCreated() {
            // Arrange
            var in = ConsentFactory.buildCreateConsent();

            // Act
            var response =  consentResource.create(in);

            // Assert
            assertEquals(HttpStatusCode.valueOf(201), response.getStatusCode());
        }

        @Test
        void shouldPassCorrectParametersConsentService() {
            // Arrange
            var in = ConsentFactory.buildCreateConsent();

            // Act
            var response =  consentResource.create(in);

            // Assert
            verify(consentService).saveConsent(createConsentArgumentCaptor.capture());
            assertEquals(in, createConsentArgumentCaptor.getValue());
        }

        @Test
        void shouldReturnCreateResponseBodyCorrectly() {
            // Arrange
            var in = ConsentFactory.buildCreateConsent();

            // Act
            var response = consentResource.create(in);

            // Assert
            assertNotNull(response);
            assertNotNull(response.getBody());

            assertEquals(in.cpf(), response.getBody().cpf());
            assertEquals(in.status(), response.getBody().status());
            assertEquals(in.additionalInfo(), response.getBody().additionalInfo());
            assertEquals(in.expirationDateTime(), response.getBody().expirationDateTime());
        }
    }

    @Nested
    class FindALlConsentsResource {

        @Test
        void shouldReturnHttpOk() {
            // Arrange
            var out = List.of(ConsentFactory.buildConsentResponse());
            when(consentService.getAll()).thenReturn(out);

            // Act
            var response = consentResource.findAll();

            // Assert
            assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        }

        @Test
        void shouldReturnListOfConsentsCorrectly() {
            // Arrange
            var out = List.of(ConsentFactory.buildConsentResponse());
            when(consentService.getAll()).thenReturn(out);

            // Act
            var response = consentResource.findAll();

            // Assert
            assertNotNull(response);
            assertNotNull(response.getBody());

            assertEquals(out.size(), response.getBody().size());
            assertEquals(out, response.getBody());
        }
    }

    @Nested
    class FindByConsentIdResource {

        @Test
        void shouldReturnHttpOk() {
            // Arrange
            var id = UUID.randomUUID().toString();
            var out = ConsentFactory.buildConsentResponse();
            when(consentService.getConsentById(id)).thenReturn(out);

            // Act
            var response = consentResource.findByConsentId(id);

            // Assert
            assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        }

        @Test
        void shouldPassCorrectParametersToConsentService() {
            // Arrange
            var id = UUID.randomUUID().toString();
            var out = ConsentFactory.buildConsentResponse();
            when(consentService.getConsentById(id)).thenReturn(out);

            // Act
            var response = consentResource.findByConsentId(id);

            // Assert
            verify(consentService).getConsentById(consentIdArgumentCaptor.capture());
            assertEquals(id, consentIdArgumentCaptor.getValue());
        }

        @Test
        void shouldReturnFindByIdResponseBodyCorrectly() {
            // Arrange
            var id = UUID.randomUUID().toString();
            var out = ConsentFactory.buildConsentResponse();
            when(consentService.getConsentById(id)).thenReturn(out);

            // Act
            var response = consentResource.findByConsentId(id);

            // Assert
            assertNotNull(response);
            assertNotNull(response.getBody());

            assertEquals(out.id(), response.getBody().id());
            assertEquals(out.cpf(), response.getBody().cpf());
            assertEquals(out.status(), response.getBody().status());
            assertEquals(out.creationDateTime(), response.getBody().creationDateTime());
            assertEquals(out.expirationDateTime(), response.getBody().expirationDateTime());
            assertEquals(out.additionalInfo(), response.getBody().additionalInfo());
        }
    }

    @Nested
    class UpdateConsentResource {

        @Test
        void shouldReturnHttpOk() {
            // Arrange
            var id = UUID.randomUUID().toString();
            var in = new UpdateConsent(ConsentStatus.REVOKED, LocalDateTime.now(), "Atualizado");
            var out = ConsentFactory.buildConsentResponse();

            when(consentService.updateConsent(id, in)).thenReturn(out);

            // Act
            var response = consentResource.update(id, in);

            // Assert
            assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        }


        @Test
        void shouldPassCorrectParametersToConsentService() {
            // Arrange
            var id = UUID.randomUUID().toString();
            var in = new UpdateConsent(ConsentStatus.REVOKED, LocalDateTime.now(), "Atualizado");
            var out = ConsentFactory.buildConsentResponse();

            when(consentService.updateConsent(id, in)).thenReturn(out);

            // Act
            var response = consentResource.update(id, in);

            // Assert
            verify(consentService).updateConsent(eq(id), eq(in));
        }

        @Test
        void shouldReturnUpdatedConsentResponseCorrectly() {
            // Arrange
            var id = UUID.randomUUID().toString();
            var dto = new UpdateConsent(ConsentStatus.REVOKED, LocalDateTime.now(), "Atualizado");
            var expected = ConsentFactory.buildConsentResponse();

            when(consentService.updateConsent(id, dto)).thenReturn(expected);

            // Act
            var result = consentResource.update(id, dto);

            // Assert
            assertNotNull(result);
            assertNotNull(result.getBody());

            var body = result.getBody();
            assertEquals(expected.id(), body.id());
            assertEquals(expected.cpf(), body.cpf());
            assertEquals(expected.status(), body.status());
            assertEquals(expected.creationDateTime(), body.creationDateTime());
            assertEquals(expected.expirationDateTime(), body.expirationDateTime());
            assertEquals(expected.additionalInfo(), body.additionalInfo());
        }
    }

    @Nested
    class DeleteConsentResource {

        @Test
        void shouldReturnHttpNoContent() {
            // Arrange
            var id = UUID.randomUUID().toString();
            doNothing().when(consentService).deleteConsent(id);

            // Act
            var result = consentResource.delete(id);

            // Assert
            assertEquals(HttpStatusCode.valueOf(204), result.getStatusCode());
        }

        @Test
        void shouldPassCorrectParametersToConsentService() {
            // Arrange
            var id = UUID.randomUUID().toString();
            doNothing().when(consentService).deleteConsent(id);

            // Act
            var response = consentResource.delete(id);

            // Assert
            verify(consentService).deleteConsent(consentIdArgumentCaptor.capture());
            assertEquals(id, consentIdArgumentCaptor.getValue());
        }
    }
}