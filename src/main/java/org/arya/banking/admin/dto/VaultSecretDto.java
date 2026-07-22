package org.arya.banking.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Payload for creating or updating a Vault secret")
public record VaultSecretDto(
        @Schema(description = "Service name (Vault path)", example = "payment-service")
        String service,
        @Schema(description = "Secret key", example = "DB_PASSWORD")
        String secretKey,
        @Schema(description = "Secret value", example = "s3cr3t!@#")
        String secretValue) {
}
