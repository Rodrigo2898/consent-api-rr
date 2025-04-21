package com.sensedia.sample.consents.resource;

import com.sensedia.sample.consents.dto.request.CreateConsent;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ConsentResourceTest {

    @Mock
    private IConsentService consentService;

    @InjectMocks
    private ConsentResource consentResource;

    @Captor
    ArgumentCaptor<CreateConsent> createConsentArgumentCaptor;

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
}