package com.sensedia.sample.consents.service.impl;

import com.sensedia.sample.consents.domain.document.Consent;
import com.sensedia.sample.consents.factory.ConsentFactory;
import com.sensedia.sample.consents.mapper.ConsentMapper;
import com.sensedia.sample.consents.repository.ConsentRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        void shouldCreateClienteSuccessfully() {
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
}