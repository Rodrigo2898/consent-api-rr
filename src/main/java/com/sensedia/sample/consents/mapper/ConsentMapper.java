package com.sensedia.sample.consents.mapper;

import com.sensedia.sample.consents.domain.document.Consent;
import com.sensedia.sample.consents.dto.ConsentDTO;
import com.sensedia.sample.consents.dto.request.ConsentRequest;
import com.sensedia.sample.consents.dto.response.ConsentResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConsentMapper {

    ConsentDTO toDTO(Consent consent);

    Consent toDocument(ConsentRequest dto);

    ConsentResponse toResponse(Consent document);
}
