package org.arya.banking.admin.controller;

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

@AdminRestController
@RequiredArgsConstructor
public class VaultOperationsController {

    private final VaultOperationService vaultOperationService;

    @PostMapping("/vault-secrets")
    @PreAuthorize("@rolePermissionValidator.hasAnyRole(authentication, 'vault-ops')")
    public ResponseEntity<VaultResponseDto> createVaultSecret(@RequestBody VaultSecretDto vaultSecretDto) {
        return ResponseEntity.ok(vaultOperationService.createVaultSecret(vaultSecretDto));
    }

    @DeleteMapping("/vault-secrets")
    public ResponseEntity<VaultResponseDto> deleteVaultSecret(@RequestParam String service) {
        return ResponseEntity.ok(vaultOperationService.deleteVaultSecret(service));
    }

    @GetMapping("/vault-secrets")
    @PreAuthorize("@rolePermissionValidator.hasAnyRole(authentication, 'vault-ops')")
    public ResponseEntity<Void> getVaultSecret(@RequestParam String service) {
        return ResponseEntity.ok(vaultOperationService.getVaultSecret(service));
    }

    @PutMapping("/vault-secrets")
    @PreAuthorize("@rolePermissionValidator.hasAnyRole(authentication, 'vault-ops')")
    public ResponseEntity<VaultResponseDto> updateVaultSecret(@RequestBody VaultSecretDto vaultSecretDto) {
        return ResponseEntity.ok(vaultOperationService.updateVaultSecret(vaultSecretDto));
    }
}
