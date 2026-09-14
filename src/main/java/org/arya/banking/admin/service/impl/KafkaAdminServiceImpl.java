package org.arya.banking.admin.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.*;
import org.arya.banking.admin.dto.TopicCreationDto;
import org.arya.banking.admin.dto.TopicDescriptionDto;
import org.arya.banking.admin.dto.TopicListingDto;
import org.arya.banking.admin.mapper.TopicDescriptionMapper;
import org.arya.banking.admin.mapper.TopicListingMapper;
import org.arya.banking.admin.service.AdminClientManager;
import org.arya.banking.admin.service.KafkaAdminService;
import org.arya.banking.common.core.dto.ResponseDto;
import org.arya.banking.common.core.exception.TopicCreationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.arya.banking.common.core.exception.ExceptionCode.TOPIC_CREATION_FAILED_400;
import static org.arya.banking.common.core.exception.ExceptionConstants.BAD_REQUEST_ERROR_CODE;

@Service
@RequiredArgsConstructor
public class KafkaAdminServiceImpl implements KafkaAdminService {

    private final AdminClientManager adminClientManager;
    private final TopicListingMapper topicListingMapper;
    private final TopicDescriptionMapper topicDescriptionMapper;

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

                .thenApply(topicListings -> topicListings.stream().map(topicListingMapper::convertToDto).toList());

    }

    @Override
    public CompletableFuture<ResponseDto> createTopic(TopicCreationDto topicCreationDto) {

        NewTopic newTopic = new NewTopic(topicCreationDto.topicName(), topicCreationDto.numPartitions(), topicCreationDto.replicationFactor());
        CreateTopicsOptions createTopicsOptions = new CreateTopicsOptions().timeoutMs(topicCreationDto.timeoutMs())
                .validateOnly(false);

        CreateTopicsResult result = getAdminClient().createTopics(List.of(newTopic), createTopicsOptions);

        return result.all()
                .toCompletionStage()
                .toCompletableFuture()
                .orTimeout(5, TimeUnit.SECONDS)
                .thenApply(voidResult -> new ResponseDto("200", "Topic created successfully"))
                .exceptionally(ex -> {
                        throw new TopicCreationException(BAD_REQUEST_ERROR_CODE, TOPIC_CREATION_FAILED_400, "Failed to create topic: " + ex.getMessage());
                });
    }

    @Override
    public CompletableFuture<List<TopicDescriptionDto>> describeTopics(List<String> topicNames, Integer timeoutMs) {
        DescribeTopicsOptions describeTopicsOptions = new DescribeTopicsOptions().timeoutMs(timeoutMs);
        DescribeTopicsResult result = getAdminClient().describeTopics(topicNames, describeTopicsOptions);
        return result.allTopicNames()
                .toCompletionStage()
                .toCompletableFuture()
                .orTimeout(5, TimeUnit.SECONDS)
                .thenApply(descriptions ->
                        descriptions.values().stream().map(topicDescriptionMapper::convertToDto).toList());

    }

    private AdminClient getAdminClient() {
        return adminClientManager.init();
    }
}
