package org.arya.banking.admin.dto;

import org.keycloak.representations.idm.RoleRepresentation;

import java.util.List;
import java.util.Map;

public record KeycloakRole(boolean composite, String name,
                           String description, RoleRepresentation.Composites composites,
                           Map<String, List<String>> attributes) {
}
