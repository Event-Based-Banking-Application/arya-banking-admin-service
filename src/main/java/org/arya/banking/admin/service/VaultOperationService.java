package org.arya.banking.admin.service;

import org.arya.banking.admin.dto.VaultResponseDto;
import org.arya.banking.admin.dto.VaultSecretDto;

import java.util.Map;

public interface VaultOperationService {

    VaultResponseDto createVaultSecret(VaultSecretDto vaultSecretDto);

    VaultResponseDto deleteVaultSecret(String service);

    Map<String, Object> getVaultSecret(String service);

    VaultResponseDto updateVaultSecret(VaultSecretDto vaultSecretDto);
}
