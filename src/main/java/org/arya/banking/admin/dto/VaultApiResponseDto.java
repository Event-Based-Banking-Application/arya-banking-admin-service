package org.arya.banking.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Map;

@Schema(description = "Vault API response with metadata")
public record VaultApiResponseDto(
        @Schema(description = "Response data payload from Vault")
        Map<String, Object> data,
        @Schema(description = "Least duration before lease expiration (seconds)", example = "86400")
        long leastDuration,
        @Schema(description = "Lease ID associated with the secret", example = "vault-lease-abc123")
        String leaseId,
        @Schema(description = "Vault request identifier", example = "req-abc-123-def")
        String requestId) {
}
