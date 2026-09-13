package org.arya.banking.admin.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ListTopicsOptions;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.arya.banking.admin.dto.TopicListingDto;
import org.arya.banking.admin.mapper.TopicListingMapper;
import org.arya.banking.admin.service.AdminClientManager;
import org.arya.banking.admin.service.KafkaAdminService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class KafkaAdminServiceImpl implements KafkaAdminService {

    private final AdminClientManager adminClientManager;
    private final TopicListingMapper topicListingMapper;

    @Override
    public CompletableFuture<List<TopicListingDto>> listTopics(boolean listInternal, Integer timeoutMs) {
        ListTopicsOptions listTopicsOptions = new ListTopicsOptions();
        listTopicsOptions.listInternal(listInternal);
        listTopicsOptions.timeoutMs(timeoutMs);

        ListTopicsResult result = getAdminClient().listTopics(listTopicsOptions);

        return result.listings()
                .toCompletionStage()
                .toCompletableFuture()
                .orTimeout(5, TimeUnit.SECONDS)

                .thenApply(topicListings -> topicListings.stream().map(topicListingMapper::convertToDto).collect(toList()));

    }

    private AdminClient getAdminClient() {
        return adminClientManager.init();
    }
}
