package org.arya.banking.admin.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arya.banking.admin.config.ApiProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.arya.banking.common.exception.UnAuthorizedException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.arya.banking.common.utils.CommonUtils.isEmpty;

@Slf4j
@Component("rolePermissionValidator")
@RequiredArgsConstructor
public class RolePermissionValidator {

    private final ApiProperties apiProperties;

    public Boolean hasAnyRole(Authentication authentication, String operation) {

        List<String> allowedRoles = apiProperties.getApiRoles().get(operation);

        if(isEmpty(allowedRoles)) {
            return false;
        }

        Set<String> userRoles = authentication.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toSet());

        boolean hasRole = allowedRoles.stream().anyMatch(userRoles::contains);
        if (!hasRole) {
            throw new UnAuthorizedException("User dose not have valid access for this operation");
        }
        return true;
    }
}
