package org.arya.banking.admin.service.impl;

import lombok.RequiredArgsConstructor;
import org.arya.banking.admin.dto.VaultResponseDto;
import org.arya.banking.admin.dto.VaultSecretDto;
import org.arya.banking.admin.service.VaultOperationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.vault.core.*;

import java.util.Collections;

import static org.arya.banking.common.constants.ResponseCodes.VAULT_SECRET_CREATED_200;
import static org.arya.banking.common.constants.ResponseCodes.VAULT_SECRET_DELETED_200;

@Service
@Transactional
@RequiredArgsConstructor
public class VaultOperationServiceImpl implements VaultOperationService {

    private final VaultOperations vaultOperations;
    private VaultKeyValueOperations vaultKeyValueOperations;
    private static final String ARYA_BANKING = "/arya-banking/";
    private static final String DEV = "/dev";

    @Override
    public VaultResponseDto createVaultSecret(VaultSecretDto vaultSecretDto) {

        vaultKeyValueOperations = getVaultKeyValueOperations();
        vaultKeyValueOperations.put(getSecretPath(vaultSecretDto.service()),
                Collections.singletonMap(vaultSecretDto.secretKey(), vaultSecretDto.secretValue()));
        return new VaultResponseDto(VAULT_SECRET_CREATED_200, "Vault Secret created successfully");
    }

    @Override
    public VaultResponseDto deleteVaultSecret(String service) {
        vaultKeyValueOperations = getVaultKeyValueOperations();
        vaultKeyValueOperations.delete(getSecretPath(service));
        return new VaultResponseDto(VAULT_SECRET_DELETED_200, "Vault Secret deleted successfully");
    }

    private String getSecretPath(String serviceName) {
        return ARYA_BANKING + serviceName + DEV;
    }


    private VaultKeyValueOperations getVaultKeyValueOperations() {
        return vaultOperations.opsForKeyValue("secret",
                VaultKeyValueOperationsSupport.KeyValueBackend.KV_2);
    }

}
