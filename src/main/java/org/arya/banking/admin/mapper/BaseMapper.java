package org.arya.banking.admin.mapper;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class BaseMapper<E, D> {

    public abstract E convertToEntity(D dto);

    public abstract D convertToDto(E entity);

    public Collection<E> convertToEntityCollection(Collection<D> dto) {
        return dto.stream().map(this::convertToEntity).toList();
    }

    public Collection<D> convertToDtoCollection(Collection<E> entities) {
        return entities.stream().map(this::convertToDto).toList();
    }

    public List<E> covertToEntityList(Collection<D> dtos) {
        return convertToEntityCollection(dtos).stream().toList();
    }

    public List<D> convertToDtoList(Collection<E> entities) {
        return convertToDtoCollection(entities).stream().toList();
    }

    public Set<E> convertToEntitySet(Collection<D> dtos) {
        return new HashSet<>(convertToEntityCollection(dtos));
    }

    public Set<D> convertToDtoSet(Collection<E> entities) {
        return new HashSet<>(convertToDtoCollection(entities));
    }

}