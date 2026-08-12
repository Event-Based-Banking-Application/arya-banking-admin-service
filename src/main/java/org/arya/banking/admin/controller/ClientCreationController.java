package org.arya.banking.admin.controller;

import lombok.RequiredArgsConstructor;
import org.arya.banking.admin.annotation.AllowedRoles;
import org.arya.banking.admin.dto.KeyCloakClientResponse;
import org.arya.banking.admin.service.KeyCloakService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.arya.banking.admin.annotation.AdminOperations.CREATE_CLIENT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class ClientCreationController {

    private final KeyCloakService keyCloakService;

    @AllowedRoles(CREATE_CLIENT)
    @PostMapping("/inter-service-clients")
    public ResponseEntity<KeyCloakClientResponse> createKeyCloakClient(@RequestParam String clientName) {
        return ResponseEntity.ok(keyCloakService.createClient(clientName));
    }
}
