package org.arya.banking.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response containing Keycloak client credentials")
public record KeyCloakClientResponse(
        @Schema(description = "Generated Keycloak client ID", example = "payment-service-client")
        String clientId,
        @Schema(description = "Generated Keycloak client secret", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890")
        String clientSecret) {
}
