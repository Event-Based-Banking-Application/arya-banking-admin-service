package org.arya.banking.admin.controller;

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
public class VaultPolicyController {

    private final VaultPolicyService vaultPolicyService;

    @GetMapping("/vault/policies")
    @PreAuthorize("@rolePermissionValidator.hasAnyRole(authentication, 'vault-ops')")
    public ResponseEntity<List<String>> getVaultPolicies() {
        return ResponseEntity.ok(vaultPolicyService.getPolicies());
    }

    @PostMapping("/vault/policies")
    @PreAuthorize("@rolePermissionValidator.hasAnyRole(authentication, 'vault-ops')")
    public ResponseEntity<VaultResponseDto> uploadPolicy(@RequestParam String service) throws IOException {
        return ResponseEntity.ok(vaultPolicyService.uploadPolicy(service));
    }

    @DeleteMapping("/vault/policies")
    @PreAuthorize("@rolePermissionValidator.hasAnyRole(authentication, 'vault-ops')")
    public ResponseEntity<VaultResponseDto> deletePolicy(@RequestParam String service) {
        return ResponseEntity.ok(vaultPolicyService.deletePolicy(service));
    }
}
