package com.sensedia.sample.consents.mapper;

import com.sensedia.sample.consents.domain.document.Consent;
import com.sensedia.sample.consents.domain.document.ConsentAudit;
import com.sensedia.sample.consents.dto.ConsentDTO;
import com.sensedia.sample.consents.dto.request.CreateConsent;
import com.sensedia.sample.consents.dto.request.UpdateConsent;
import com.sensedia.sample.consents.dto.response.ConsentAuditResponse;
import com.sensedia.sample.consents.dto.response.ConsentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ConsentMapper {

    ConsentDTO toDTO(Consent consent);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDateTime", ignore = true)
    Consent toDocument(CreateConsent dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cpf", ignore = true)
    @Mapping(target = "creationDateTime", ignore = true)
    Consent toDocument(UpdateConsent dto);

    ConsentResponse toResponse(Consent document);

    List<ConsentResponse> toResponseList(List<Consent> consents);

    List<ConsentAuditResponse> toAuditResponseList(List<ConsentAudit> audits);

    ConsentAuditResponse toAuditResponse(ConsentAudit audit);
}
