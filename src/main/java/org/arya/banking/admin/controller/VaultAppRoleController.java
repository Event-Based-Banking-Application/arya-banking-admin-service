package org.arya.banking.admin.controller;

import lombok.RequiredArgsConstructor;
import org.arya.banking.admin.annotation.AdminRestController;
import org.arya.banking.admin.annotation.AllowedRoles;
import org.arya.banking.admin.dto.AppRoleResponseDto;
import org.arya.banking.admin.dto.CreateAppRoleDto;
import org.arya.banking.admin.dto.VaultApiResponseDto;
import org.arya.banking.admin.dto.VaultResponseDto;
import org.arya.banking.admin.service.VaultAppRoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static org.arya.banking.admin.annotation.AdminOperations.VAULT_OPS;

@AdminRestController
@RequiredArgsConstructor
public class VaultAppRoleController {

    private final VaultAppRoleService vaultAppRoleService;

    @GetMapping("vault-approle")
    @AllowedRoles(VAULT_OPS)
    public ResponseEntity<List<String>> getAppRoleDetails() {
        return ResponseEntity.ok(vaultAppRoleService.getAppRoles());
    }

    @GetMapping("vault-approle/{role}")
    @AllowedRoles(VAULT_OPS)
    public ResponseEntity<VaultApiResponseDto> getAppRoleByRole(@PathVariable String role) {
        return ResponseEntity.ok(vaultAppRoleService.getAppRole(role));
    }

    @PostMapping("vault-approle")
    @AllowedRoles(VAULT_OPS)
    public ResponseEntity<AppRoleResponseDto> createAppRole(@RequestBody CreateAppRoleDto createAppRoleDto) {
        return ResponseEntity.ok(vaultAppRoleService.createAppRole(createAppRoleDto));
    }

    @DeleteMapping("vault-approle")
    @AllowedRoles(VAULT_OPS)
    public ResponseEntity<VaultResponseDto> deleteAppRole(@RequestParam String role) {
        return ResponseEntity.ok(vaultAppRoleService.deleteAppRole(role));
    }

    @GetMapping("/vault-approle/secrets")
    @AllowedRoles(VAULT_OPS)
    public ResponseEntity<AppRoleResponseDto> generateAppRoleCredentials(@RequestParam String appRole) {
        return ResponseEntity.ok(vaultAppRoleService.generateCredentials(appRole));
    }
}
