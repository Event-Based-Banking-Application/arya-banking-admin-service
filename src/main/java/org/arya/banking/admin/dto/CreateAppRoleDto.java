package org.arya.banking.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Payload for creating a new Vault AppRole")
public record CreateAppRoleDto(
        @Schema(description = "Name of the AppRole", example = "payment-service")
        String roleName,
        @Schema(description = "List of Vault ACL policies to attach to the AppRole", example = "[\"payment-service\", \"default\"]")
        List<String> policies) {
}
