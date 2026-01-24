package org.arya.banking.admin.dto;

import java.util.List;

public record CreateAppRoleDto(
        String roleName,
        List<String> policies) {
}
