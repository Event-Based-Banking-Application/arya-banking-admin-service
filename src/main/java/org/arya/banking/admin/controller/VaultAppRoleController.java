package org.arya.banking.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.arya.banking.admin.annotation.AdminRestController;
import org.arya.banking.admin.dto.AppRoleResponseDto;
import org.arya.banking.admin.dto.CreateAppRoleDto;
import org.arya.banking.admin.dto.VaultApiResponseDto;
import org.arya.banking.admin.dto.VaultResponseDto;
import org.arya.banking.admin.service.VaultAppRoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@AdminRestController
@RequiredArgsConstructor
@Tag(name = "Vault AppRole", description = "HashiCorp Vault AppRole management — create, list, delete AppRoles and generate dynamic credentials")
@SecurityRequirement(name = "bearerAuth")
public class VaultAppRoleController {

    private final VaultAppRoleService vaultAppRoleService;

    @GetMapping("vault-approle")
    @PreAuthorize("@rolePermissionValidator.hasAnyRole(authentication, 'vault-ops')")
    @Operation(summary = "List all AppRoles", description = "Retrieves the list of all AppRole names configured in Vault. Requires 'vault-ops' role.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of AppRole names returned"),
            @ApiResponse(responseCode = "403", description = "Insufficient permissions")
    })
    public ResponseEntity<List<String>> getAppRoleDetails() {
        return ResponseEntity.ok(vaultAppRoleService.getAppRoles());
    }

    @GetMapping("vault-approle/{role}")
    @PreAuthorize("@rolePermissionValidator.hasAnyRole(authentication, 'vault-ops')")
    @Operation(summary = "Get AppRole by name", description = "Retrieves detailed information about a specific Vault AppRole including its role ID and associated policies. Requires 'vault-ops' role.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "AppRole details returned"),
            @ApiResponse(responseCode = "403", description = "Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "AppRole not found")
    })
    public ResponseEntity<VaultApiResponseDto> getAppRoleByRole(
            @Parameter(description = "Name of the AppRole") @PathVariable String role) {
        return ResponseEntity.ok(vaultAppRoleService.getAppRole(role));
    }

    @PostMapping("vault-approle")
    @PreAuthorize("@rolePermissionValidator.hasAnyRole(authentication, 'vault-ops')")
    @Operation(summary = "Create AppRole", description = "Creates a new AppRole in Vault with the specified name and associated ACL policies. Requires 'vault-ops' role.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "AppRole created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "403", description = "Insufficient permissions")
    })
    public ResponseEntity<AppRoleResponseDto> createAppRole(@RequestBody CreateAppRoleDto createAppRoleDto) {
        return ResponseEntity.ok(vaultAppRoleService.createAppRole(createAppRoleDto));
    }

    @DeleteMapping("vault-approle")
    @PreAuthorize("@rolePermissionValidator.hasAnyRole(authentication, 'vault-ops')")
    @Operation(summary = "Delete AppRole", description = "Deletes a Vault AppRole by name. Requires 'vault-ops' role.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "AppRole deleted successfully"),
            @ApiResponse(responseCode = "403", description = "Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "AppRole not found")
    })
    public ResponseEntity<VaultResponseDto> deleteAppRole(
            @Parameter(description = "Name of the AppRole to delete") @RequestParam String role) {
        return ResponseEntity.ok(vaultAppRoleService.deleteAppRole(role));
    }

    @GetMapping("/vault-approle/secrets")
    @PreAuthorize("@rolePermissionValidator.hasAnyRole(authentication, 'vault-ops')")
    @Operation(summary = "Generate AppRole credentials", description = "Generates a dynamic secret ID for the specified AppRole. Returns role ID and secret ID for Vault authentication. Requires 'vault-ops' role.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Credentials generated successfully"),
            @ApiResponse(responseCode = "403", description = "Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "AppRole not found")
    })
    public ResponseEntity<AppRoleResponseDto> generateAppRoleCredentials(
            @Parameter(description = "Name of the AppRole to generate credentials for") @RequestParam String appRole) {
        return ResponseEntity.ok(vaultAppRoleService.generateCredentials(appRole));
    }
}
