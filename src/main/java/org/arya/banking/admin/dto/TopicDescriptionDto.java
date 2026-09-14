package org.arya.banking.admin.dto;

import java.util.Set;

public record TopicDescriptionDto(String name, int partitions, boolean isInternal, Set<Byte> aclOperation) {
}
