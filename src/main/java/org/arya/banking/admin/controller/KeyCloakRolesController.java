package org.arya.banking.admin.controller;

import lombok.RequiredArgsConstructor;
import org.arya.banking.admin.annotation.AdminRestController;
import org.arya.banking.admin.dto.KeycloakRole;
import org.arya.banking.admin.service.KeyCloakService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@AdminRestController
public class KeyCloakRolesController {

    private final KeyCloakService keyCloakService;

    @GetMapping("/realm-roles")
    @PreAuthorize("@rolePermissionValidator.hasAnyRole(authentication, 'query-realm')")
    public ResponseEntity<List<KeycloakRole>> getRealmRoles() {
        return ResponseEntity.ok(keyCloakService.getRealmRoles());
    }
}
