package org.arya.banking.admin.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.vault.support.VaultResponse;

@Data
@EqualsAndHashCode(callSuper = true)
public class VaultSecretResponseDto extends VaultResponse {
}
