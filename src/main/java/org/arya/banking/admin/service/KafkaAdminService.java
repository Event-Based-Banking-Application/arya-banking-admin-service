package org.arya.banking.admin.service;

import org.arya.banking.admin.dto.TopicListingDto;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface KafkaAdminService {

    CompletableFuture<List<TopicListingDto>> listTopics(boolean listInternal, Integer timeoutMs);
}
