package org.arya.banking.admin.mapper;

import org.arya.banking.admin.dto.VaultApiResponseDto;
import org.arya.banking.common.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.springframework.vault.support.VaultResponse;

@Mapper(componentModel = "spring")
public interface VaultResponseMapper extends BaseMapper<VaultResponse, VaultApiResponseDto> {

    VaultApiResponseDto toDto(VaultResponse vaultResponse);
}
