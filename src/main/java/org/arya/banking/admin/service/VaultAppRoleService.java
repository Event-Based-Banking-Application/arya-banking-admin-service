package org.arya.banking.admin.service;

import org.arya.banking.admin.dto.AppRoleResponseDto;
import org.arya.banking.admin.dto.CreateAppRoleDto;
import org.arya.banking.admin.dto.VaultApiResponseDto;
import org.arya.banking.admin.dto.VaultResponseDto;

import java.util.List;

public interface VaultAppRoleService {

    List<String> getAppRoles();

    VaultApiResponseDto getAppRole(String role);

    AppRoleResponseDto createAppRole(CreateAppRoleDto createAppRoleDto);

    VaultResponseDto deleteAppRole(String role);

    AppRoleResponseDto generateCredentials(String service);
}
