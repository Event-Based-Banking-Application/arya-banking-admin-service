package org.arya.banking.admin.service.impl;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arya.banking.admin.dto.KeyCloakClientResponse;
import org.arya.banking.admin.dto.KeycloakRole;
import org.arya.banking.admin.mapper.KeycloakRoleMapper;
import org.arya.banking.admin.service.KeyCloakManager;
import org.arya.banking.admin.service.KeyCloakService;
import org.arya.banking.common.constants.ResponseCodes;
import org.arya.banking.common.dto.KeyCloakResponse;
import org.arya.banking.common.exception.KeyCloakClientAlreadyExists;
import org.arya.banking.common.exception.KeyCloakRealmRoleAlreadyExists;
import org.arya.banking.common.exception.KeyCloakServiceException;
import org.keycloak.admin.client.resource.RoleResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.arya.banking.common.constants.ResponseCodes.KEYCLOAK_ROLE_CREATED_200;
import static org.arya.banking.common.exception.ExceptionCode.ADMIN_KEYCLOAK_CLIENT_ALREADY_EXISTS_409;
import static org.arya.banking.common.exception.ExceptionCode.ADMIN_KEYCLOAK_CLIENT_CREATION_EXCEPTION_500;
import static org.arya.banking.common.exception.ExceptionConstants.BAD_REQUEST_ERROR_CODE;
import static org.arya.banking.common.exception.ExceptionConstants.CONFLICT_ERROR_CODE;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class KeyCloakServiceImpl implements KeyCloakService  {

    public static final String CLIENT_SECRET = "client-secret";
    public static final String ERROR_OCCURRED_WHILE_CREATING_KEYCLOAK_CLIENT = "Error occurred while creating keycloak client";
    public static final String INTERNAL_SERVICE = "INTERNAL_SERVICE";
    private final KeyCloakManager keyCloakManager;
    private final KeycloakRoleMapper keycloakRoleMapper;

    @Override
    public KeyCloakClientResponse createClient(String clientName) {

        ClientRepresentation clientRepresentation;
        List<ClientRepresentation> clientRepresentations = keyCloakManager.getKeyCloakInstanceWithRealm()
                .clients().findByClientId(clientName);
        if(!clientRepresentations.isEmpty()) {
            throw new KeyCloakClientAlreadyExists(CONFLICT_ERROR_CODE, ADMIN_KEYCLOAK_CLIENT_ALREADY_EXISTS_409, "KeyCloak client with name " + clientName + " already exists");
        } else {
            clientRepresentation = new ClientRepresentation();
            clientRepresentation.setClientId(clientName);
            clientRepresentation.setEnabled(true);
            clientRepresentation.setClientAuthenticatorType(CLIENT_SECRET);
            clientRepresentation.setServiceAccountsEnabled(true);
            clientRepresentation.setPublicClient(false);
            clientRepresentation.setStandardFlowEnabled(false);
            clientRepresentation.setFrontchannelLogout(true);

            Response response = keyCloakManager.getKeyCloakInstanceWithRealm().clients().create(clientRepresentation);
            if(response.getStatus() != 201) {
                log.error(ERROR_OCCURRED_WHILE_CREATING_KEYCLOAK_CLIENT +"{}", response.getEntity().toString());
                throw new KeyCloakServiceException(BAD_REQUEST_ERROR_CODE, ADMIN_KEYCLOAK_CLIENT_CREATION_EXCEPTION_500, ERROR_OCCURRED_WHILE_CREATING_KEYCLOAK_CLIENT);
            }
        } clientRepresentation = keyCloakManager.getKeyCloakInstanceWithRealm().clients()
                .findByClientId(clientName).get(0);

        assignServiceAccountsRoles(clientRepresentation.getId(), INTERNAL_SERVICE);
        return new KeyCloakClientResponse(clientRepresentation.getClientId(), clientRepresentation.getSecret());
    }

    private void assignServiceAccountsRoles(String clientId, String role) {

        UserRepresentation userRepresentation = keyCloakManager.getRealmClient()
                .get(clientId).getServiceAccountUser();

        RoleRepresentation roleRepresentation = getRoleRepresentation(role);

        keyCloakManager.getRealmUsers().get(userRepresentation.getId()).roles()
                .realmLevel().add(List.of(roleRepresentation));
    }

    @Override
    public List<KeycloakRole> getRealmRoles() {
        return keycloakRoleMapper.toDtoList(keyCloakManager.getRealmRoles().list());
    }

    @Override
    public KeycloakRole getRealmRoleByName(String roleName) {
        return keycloakRoleMapper.toDto(getRoleRepresentation(roleName));
    }

    private RoleRepresentation getRoleRepresentation(String roleName) {
        return keyCloakManager.getRealmRoles().get(roleName).toRepresentation();
    }

    @Override
    public KeyCloakResponse createRealmRole(KeycloakRole keycloakRole) {

        RoleResource roleResource = keyCloakManager.getRealmRoles().get(keycloakRole.name());
        try {
            roleResource.toRepresentation();
            throw new KeyCloakRealmRoleAlreadyExists(String.format("Key cloak realm role: %s already exists", keycloakRole.name()));
        } catch (NotFoundException e) {
            log.debug("Creating new realm role: {}", keycloakRole.name());
            RoleRepresentation roleRepresentation = keycloakRoleMapper.toEntity(keycloakRole);
            keyCloakManager.getKeyCloakInstanceWithRealm().roles().create(roleRepresentation);
        }
        return new KeyCloakResponse(KEYCLOAK_ROLE_CREATED_200, "Key cloak Realm Role created successfully");
    }


}
