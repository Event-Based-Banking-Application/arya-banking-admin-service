package org.arya.banking.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Generic Vault operation response")
public record VaultResponseDto(
        @Schema(description = "HTTP response code", example = "200")
        String responseCode,
        @Schema(description = "Response message", example = "Operation completed successfully")
        String responseMessage) {
}
