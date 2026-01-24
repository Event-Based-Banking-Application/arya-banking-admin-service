package org.arya.banking.admin.controller;

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
public class VaultAppRoleController {

    private final VaultAppRoleService vaultAppRoleService;

    @GetMapping("vault-approle")
    @PreAuthorize("@rolePermissionValidator.hasAnyRole(authentication, 'vault-ops')")
    public ResponseEntity<List<String>> getAppRoleDetails() {
        return ResponseEntity.ok(vaultAppRoleService.getAppRoles());
    }

    @GetMapping("vault-approle/{role}")
    @PreAuthorize("@rolePermissionValidator.hasAnyRole(authentication, 'vault-ops')")
    public ResponseEntity<VaultApiResponseDto> getAppRoleByRole(@PathVariable String role) {
        return ResponseEntity.ok(vaultAppRoleService.getAppRole(role));
    }

    @PostMapping("vault-approle")
    @PreAuthorize("@rolePermissionValidator.hasAnyRole(authentication, 'vault-ops')")
    public ResponseEntity<AppRoleResponseDto> createAppRole(@RequestBody CreateAppRoleDto createAppRoleDto) {
        return ResponseEntity.ok(vaultAppRoleService.createAppRole(createAppRoleDto));
    }

    @DeleteMapping("vault-approle")
    @PreAuthorize("@rolePermissionValidator.hasAnyRole(authentication, 'vault-ops')")
    public ResponseEntity<VaultResponseDto> deleteAppRole(@RequestParam String role) {
        return ResponseEntity.ok(vaultAppRoleService.deleteAppRole(role));
    }

    @GetMapping("/vault-approle/secrets")
    @PreAuthorize("@rolePermissionValidator.hasAnyRole(authentication, 'vault-ops')")
    public ResponseEntity<AppRoleResponseDto> generateAppRoleCredentials(@RequestParam String appRole) {
        return ResponseEntity.ok(vaultAppRoleService.generateCredentials(appRole));
    }
}
