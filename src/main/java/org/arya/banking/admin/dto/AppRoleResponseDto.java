package org.arya.banking.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response containing Vault AppRole credentials")
public record AppRoleResponseDto(
        @Schema(description = "Vault AppRole role ID", example = "6b2c3d4e-5f6a-7890-bcde-f1234567890a")
        String roleId,
        @Schema(description = "Vault AppRole secret ID", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890b")
        String secretId
) {
}
