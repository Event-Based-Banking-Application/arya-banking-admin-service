package org.arya.banking.admin.dto;

import java.util.List;

public record VaultSecretDto(String service, List<VaultSecret> secrets) {
}
