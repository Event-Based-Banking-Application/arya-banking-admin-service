package org.arya.banking.admin.service;

import org.arya.banking.admin.dto.VaultResponseDto;
import org.arya.banking.admin.dto.VaultSecretDto;

public interface VaultOperationService {

    VaultResponseDto createVaultSecret(VaultSecretDto vaultSecretDto);

    VaultResponseDto deleteVaultSecret(String service);
}
