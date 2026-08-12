package org.arya.banking.admin.controller;

import lombok.RequiredArgsConstructor;
import org.arya.banking.admin.annotation.AdminRestController;
import org.arya.banking.admin.annotation.AllowedRoles;
import org.arya.banking.admin.dto.KeycloakRole;
import org.arya.banking.admin.service.KeyCloakService;
import org.arya.banking.common.dto.KeyCloakResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static org.arya.banking.admin.annotation.AdminOperations.QUERY_REALM;

@RequiredArgsConstructor
@AdminRestController
public class KeyCloakRolesController {

    private final KeyCloakService keyCloakService;

    @GetMapping("/realm-roles")
    @AllowedRoles(QUERY_REALM)
    public ResponseEntity<List<KeycloakRole>> getRealmRoles() {
        return ResponseEntity.ok(keyCloakService.getRealmRoles());
    }

    @GetMapping("/realm-role")
    @AllowedRoles(QUERY_REALM)
    public ResponseEntity<KeycloakRole> getRealmRoleByName(@RequestParam String roleName) {
        return ResponseEntity.ok(keyCloakService.getRealmRoleByName(roleName));
    }

    @PostMapping("/realm-roles")
    @AllowedRoles(QUERY_REALM)
    public ResponseEntity<KeyCloakResponse> createRole(@RequestBody KeycloakRole keycloakRole) {
        return ResponseEntity.ok(keyCloakService.createRealmRole(keycloakRole));
    }
}
