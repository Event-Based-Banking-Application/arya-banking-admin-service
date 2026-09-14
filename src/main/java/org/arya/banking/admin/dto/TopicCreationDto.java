package org.arya.banking.admin.dto;

public record TopicCreationDto(String topicName,
                               int numPartitions,
                               short replicationFactor,
                               Integer timeoutMs) {
}
