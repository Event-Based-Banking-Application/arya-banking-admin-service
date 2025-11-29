package org.arya.banking.admin.service.impl;

import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arya.banking.admin.dto.KeyCloakClientResponse;
import org.arya.banking.admin.service.KeyCloakManager;
import org.arya.banking.admin.service.KeyCloakService;
import org.arya.banking.common.exception.KeyCloakClientAlreadyExists;
import org.arya.banking.common.exception.KeyCloakServiceException;
import org.keycloak.representations.idm.ClientRepresentation;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.arya.banking.common.exception.ExceptionCode.ADMIN_KEYCLOAK_CLIENT_ALREADY_EXISTS_409;
import static org.arya.banking.common.exception.ExceptionCode.ADMIN_KEYCLOAK_CLIENT_CREATION_EXCEPTION_500;
import static org.arya.banking.common.exception.ExceptionConstants.BAD_REQUEST_ERROR_CODE;
import static org.arya.banking.common.exception.ExceptionConstants.CONFLICT_ERROR_CODE;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeyCloakServiceImpl implements KeyCloakService  {

    public static final String CLIENT_SECRET = "client-secret";
    public static final String ERROR_OCCURRED_WHILE_CREATING_KEYCLOAK_CLIENT = "Error occurred while creating keycloak client";
    private final KeyCloakManager keyCloakManager;

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

        return new KeyCloakClientResponse(clientRepresentation.getClientId(), clientRepresentation.getSecret());
    }
}
