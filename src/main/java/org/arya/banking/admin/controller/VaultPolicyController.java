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
import org.arya.banking.admin.service.VaultPolicyService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@AdminRestController
@RequiredArgsConstructor
@Tag(name = "Vault Policies", description = "HashiCorp Vault ACL policy management — list, upload, and delete HCL policies for service access control")
@SecurityRequirement(name = "bearerAuth")
public class VaultPolicyController {

    private final VaultPolicyService vaultPolicyService;

    @GetMapping("/vault/policies")
    @PreAuthorize("@rolePermissionValidator.hasAnyRole(authentication, 'vault-ops')")
    @Operation(summary = "List all policies", description = "Retrieves the names of all ACL policies configured in Vault. Requires 'vault-ops' role.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of policy names returned"),
            @ApiResponse(responseCode = "403", description = "Insufficient permissions")
    })
    public ResponseEntity<List<String>> getVaultPolicies() {
        return ResponseEntity.ok(vaultPolicyService.getPolicies());
    }

    @PostMapping("/vault/policies")
    @PreAuthorize("@rolePermissionValidator.hasAnyRole(authentication, 'vault-ops')")
    @Operation(summary = "Upload policy from file", description = "Reads an HCL policy file from the classpath for the given service name and uploads it to Vault. Requires 'vault-ops' role.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Policy uploaded successfully"),
            @ApiResponse(responseCode = "403", description = "Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Policy file not found for the service")
    })
    public ResponseEntity<VaultResponseDto> uploadPolicy(
            @Parameter(description = "Service name whose HCL policy file to upload") @RequestParam String service) throws IOException {
        return ResponseEntity.ok(vaultPolicyService.uploadPolicy(service));
    }

    @DeleteMapping("/vault/policies")
    @PreAuthorize("@rolePermissionValidator.hasAnyRole(authentication, 'vault-ops')")
    @Operation(summary = "Delete policy", description = "Deletes an ACL policy from Vault by service name. Requires 'vault-ops' role.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Policy deleted successfully"),
            @ApiResponse(responseCode = "403", description = "Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Policy not found")
    })
    public ResponseEntity<VaultResponseDto> deletePolicy(
            @Parameter(description = "Service name whose policy to delete") @RequestParam String service) {
        return ResponseEntity.ok(vaultPolicyService.deletePolicy(service));
    }
}
