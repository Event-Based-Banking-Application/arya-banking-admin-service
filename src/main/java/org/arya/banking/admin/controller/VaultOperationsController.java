package org.arya.banking.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.arya.banking.admin.annotation.AdminRestController;
import org.arya.banking.admin.dto.VaultResponseDto;
import org.arya.banking.admin.dto.VaultSecretDto;
import org.arya.banking.admin.service.VaultOperationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@AdminRestController
@RequiredArgsConstructor
@Tag(name = "Vault Secrets", description = "HashiCorp Vault secret management — CRUD operations for service configuration secrets")
@SecurityRequirement(name = "bearerAuth")
public class VaultOperationsController {

    private final VaultOperationService vaultOperationService;

    @PostMapping("/vault-secrets")
    @PreAuthorize("@rolePermissionValidator.hasAnyRole(authentication, 'vault-ops')")
    @Operation(summary = "Create vault secret", description = "Stores a new secret in Vault under the specified service path. Requires 'vault-ops' role.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Secret created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "403", description = "Insufficient permissions")
    })
    public ResponseEntity<VaultResponseDto> createVaultSecret(@RequestBody VaultSecretDto vaultSecretDto) {
        return ResponseEntity.ok(vaultOperationService.createVaultSecret(vaultSecretDto));
    }

    @DeleteMapping("/vault-secrets")
    @Operation(summary = "Delete vault secret", description = "Deletes all secrets stored under the specified service path in Vault.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Secret deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Service path not found")
    })
    public ResponseEntity<VaultResponseDto> deleteVaultSecret(
            @Parameter(description = "Service name whose secrets to delete") @RequestParam String service) {
        return ResponseEntity.ok(vaultOperationService.deleteVaultSecret(service));
    }

    @GetMapping("/vault-secrets")
    @PreAuthorize("@rolePermissionValidator.hasAnyRole(authentication, 'vault-ops')")
    @Operation(summary = "Get vault secret", description = "Retrieves all secrets stored for a given service from Vault. Requires 'vault-ops' role.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Secrets returned"),
            @ApiResponse(responseCode = "403", description = "Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Service not found")
    })
    public ResponseEntity<Map<String, Object>> getVaultSecret(
            @Parameter(description = "Service name to retrieve secrets for") @RequestParam String service) {
        return ResponseEntity.ok(vaultOperationService.getVaultSecret(service));
    }

    @PutMapping("/vault-secrets")
    @PreAuthorize("@rolePermissionValidator.hasAnyRole(authentication, 'vault-ops')")
    @Operation(summary = "Update vault secret", description = "Updates an existing secret in Vault for the specified service. Requires 'vault-ops' role.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Secret updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "403", description = "Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Service not found")
    })
    public ResponseEntity<VaultResponseDto> updateVaultSecret(@RequestBody VaultSecretDto vaultSecretDto) {
        return ResponseEntity.ok(vaultOperationService.updateVaultSecret(vaultSecretDto));
    }
}
