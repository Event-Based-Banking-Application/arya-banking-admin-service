package org.arya.banking.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.arya.banking.admin.annotation.AllowedRoles;
import org.arya.banking.admin.dto.KeyCloakClientResponse;
import org.arya.banking.admin.service.KeyCloakService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
@Tag(name = "Keycloak Clients", description = "Keycloak client management — create inter-service OAuth2 clients with credentials")
@SecurityRequirement(name = "bearerAuth")
public class ClientCreationController {

    private final KeyCloakService keyCloakService;

    @PostMapping("/inter-service-clients")
    @PreAuthorize("@rolePermissionValidator.hasAnyRole(authentication, 'create-client')")
    @Operation(summary = "Create Keycloak client", description = "Creates a new OAuth2 client in Keycloak for inter-service communication. Returns the generated client ID and secret. Requires 'create-client' role.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Client created successfully, client ID and secret returned"),
            @ApiResponse(responseCode = "403", description = "Insufficient permissions — requires 'create-client' role")
    })
    public ResponseEntity<KeyCloakClientResponse> createKeyCloakClient(
            @Parameter(description = "Name of the client to create") @RequestParam String clientName) {
        return ResponseEntity.ok(keyCloakService.createClient(clientName));
    }
}
