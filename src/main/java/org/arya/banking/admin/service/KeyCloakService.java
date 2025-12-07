package org.arya.banking.admin.service;

import org.arya.banking.admin.dto.KeyCloakClientResponse;
import org.arya.banking.admin.dto.KeycloakRole;
import org.arya.banking.common.dto.KeyCloakResponse;
import org.arya.banking.common.model.KeyCloakUser;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

public interface KeyCloakService {

    KeyCloakClientResponse createClient(String clientName);

    List<KeycloakRole> getRealmRoles();
}
