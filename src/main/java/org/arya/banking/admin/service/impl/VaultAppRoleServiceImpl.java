package org.arya.banking.admin.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arya.banking.admin.dto.AppRoleResponseDto;
import org.arya.banking.admin.dto.CreateAppRoleDto;
import org.arya.banking.admin.dto.VaultApiResponseDto;
import org.arya.banking.admin.dto.VaultResponseDto;
import org.arya.banking.admin.mapper.VaultResponseMapper;
import org.arya.banking.admin.service.VaultAppRoleService;
import org.arya.banking.common.exception.RoleIdNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.vault.VaultException;
import org.springframework.vault.core.VaultTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class VaultAppRoleServiceImpl implements VaultAppRoleService {

    private static final String APPROLE_BASE = "/auth/approle/role/";
    private static final String APPROLE_PATH = APPROLE_BASE + "%s";
    private static final String ROLE_PATH = APPROLE_BASE + "%s/role-id";
    private static final String SECRET_PATH = APPROLE_BASE + "%s/secret-id";
    private static final String ROLE_ID = "role_id";
    private final VaultTemplate vaultTemplate;
    private final VaultResponseMapper vaultResponseMapper;

    @Override
    public List<String> getAppRoles() {
        return vaultTemplate.list(APPROLE_BASE);
    }

    @Override
    public VaultApiResponseDto getAppRole(String role) {
        return vaultResponseMapper.toDto(vaultTemplate.read(APPROLE_BASE + role));
    }

    @Override
    public AppRoleResponseDto createAppRole(CreateAppRoleDto createAppRoleDto) {

        Map<String, Object> appRoleRequest = new HashMap<>();
        appRoleRequest.put("policies", String.join(",", createAppRoleDto.policies()));
        appRoleRequest.put("token_ttl", "1h");
        appRoleRequest.put("token_max_ttl", "4h");

        String roleName = createAppRoleDto.roleName();
        vaultTemplate.write(getAppRolePath(roleName), appRoleRequest);

        return new AppRoleResponseDto(getRoleId(roleName), getSecretId(roleName));
    }

    private String getRoleId(String roleName) {
        VaultApiResponseDto vaultApiResponseDto = null;
        try {
            vaultApiResponseDto = vaultResponseMapper.toDto(vaultTemplate.read(
                    resolveRoleIdPath(roleName)));
        } catch (VaultException e) {
            throw new RoleIdNotFoundException("Role Id is not present for role or permission denied");
        }
        return vaultApiResponseDto.data().get(ROLE_ID).toString();
    }

    private String getSecretId(String roleName) {
        VaultApiResponseDto vaultApiResponseDto = vaultResponseMapper.toDto(vaultTemplate.write(
                resolveSecretIdPath(roleName), Map.of()
        ));
        return vaultApiResponseDto.data().get("secret_id").toString();
    }

    private String resolveRoleIdPath(String roleName) {
        return String.format(ROLE_PATH, roleName);
    }

    private String resolveSecretIdPath(String roleName) {
        return String.format(SECRET_PATH, roleName);
    }

    private static String getAppRolePath(String roleName) {
        return String.format(APPROLE_PATH, roleName);
    }

    @Override
    public VaultResponseDto deleteAppRole(String role) {
        vaultTemplate.delete(APPROLE_BASE + role);

        return new VaultResponseDto("", "");
    }

    @Override
    public AppRoleResponseDto generateCredentials(String service) {
        return new AppRoleResponseDto(getRoleId(service), getSecretId(service));
    }


}
