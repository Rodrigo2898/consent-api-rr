package com.sensedia.sample.consents.dto.response;

import java.time.LocalDateTime;
import java.util.Map;

public record ConsentAuditResponse(
        String id,
        String action,
        LocalDateTime timestamp,
        String changedBy,
        Map<String, Object> previousState,
        Map<String, Object> newState,
        String reason
) {}