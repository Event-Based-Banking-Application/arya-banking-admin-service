package org.arya.banking.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.arya.banking.admin.annotation.AdminRestController;
import org.arya.banking.admin.dto.KeycloakRole;
import org.arya.banking.admin.service.KeyCloakService;
import org.arya.banking.common.dto.KeyCloakResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@AdminRestController
@Tag(name = "Keycloak Roles", description = "Keycloak realm role management — list, query, and create realm-level roles")
@SecurityRequirement(name = "bearerAuth")
public class KeyCloakRolesController {

    private final KeyCloakService keyCloakService;

    @GetMapping("/realm-roles")
    @PreAuthorize("@rolePermissionValidator.hasAnyRole(authentication, 'query-realm')")
    @Operation(summary = "List all realm roles", description = "Retrieves all roles defined in the Keycloak realm. Requires 'query-realm' role.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of realm roles returned"),
            @ApiResponse(responseCode = "403", description = "Insufficient permissions — requires 'query-realm' role")
    })
    public ResponseEntity<List<KeycloakRole>> getRealmRoles() {
        return ResponseEntity.ok(keyCloakService.getRealmRoles());
    }

    @GetMapping("/realm-role")
    @PreAuthorize("@rolePermissionValidator.hasAnyRole(authentication, 'query-realm')")
    @Operation(summary = "Get realm role by name", description = "Retrieves a specific Keycloak realm role by its name. Requires 'query-realm' role.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Realm role found"),
            @ApiResponse(responseCode = "403", description = "Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Role not found")
    })
    public ResponseEntity<KeycloakRole> getRealmRoleByName(
            @Parameter(description = "Name of the realm role") @RequestParam String roleName) {
        return ResponseEntity.ok(keyCloakService.getRealmRoleByName(roleName));
    }

    @PostMapping("/realm-roles")
    @PreAuthorize("@rolePermissionValidator.hasAnyRole(authentication, 'query-realm')")
    @Operation(summary = "Create realm role", description = "Creates a new role in the Keycloak realm. Requires 'query-realm' role.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Role created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid role data"),
            @ApiResponse(responseCode = "403", description = "Insufficient permissions"),
            @ApiResponse(responseCode = "409", description = "Role already exists")
    })
    public ResponseEntity<KeyCloakResponse> createRole(@RequestBody KeycloakRole keycloakRole) {
        return ResponseEntity.ok(keyCloakService.createRealmRole(keycloakRole));
    }
}
