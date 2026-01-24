package org.arya.banking.admin.dto;

import java.util.Map;

public record VaultApiResponseDto(
        Map<String, Object> data,
        long leastDuration,
        String leaseId,
        String requestId) {
}
