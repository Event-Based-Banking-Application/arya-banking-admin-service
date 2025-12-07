package org.arya.banking.admin.mapper;

import org.arya.banking.admin.dto.KeycloakRole;
import org.arya.banking.common.mapper.BaseMapper;
import org.keycloak.representations.idm.RoleRepresentation;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface KeycloakRoleMapper extends BaseMapper<RoleRepresentation, KeycloakRole> {

    KeycloakRole toDto(RoleRepresentation roleRepresentation);

    List<KeycloakRole> toDtoList(List<RoleRepresentation> roleRepresentations);
}
