package org.arya.banking.admin.service;

import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminClientManager {

    private AdminClient adminClient;

    private final KafkaProperties kafkaProperties;

    public synchronized AdminClient init() {
        if (null == adminClient) {
            adminClient = AdminClient.create(kafkaProperties.buildAdminProperties(null));
        }
        return adminClient;
    }

    @PreDestroy
    public void close() {
        if (null != adminClient) {
            adminClient.close();
        }
    }

}
