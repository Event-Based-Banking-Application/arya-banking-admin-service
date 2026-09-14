package org.arya.banking.admin.service;

import org.arya.banking.admin.dto.TopicCreationDto;
import org.arya.banking.admin.dto.TopicDescriptionDto;
import org.arya.banking.admin.dto.TopicListingDto;
import org.arya.banking.common.core.dto.ResponseDto;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface KafkaAdminService {

    CompletableFuture<List<TopicListingDto>> listTopics(boolean listInternal, Integer timeoutMs);

    CompletableFuture<ResponseDto> createTopic(TopicCreationDto topicCreationDto);

    CompletableFuture<List<TopicDescriptionDto>> describeTopics(List<String> topicNames, Integer timeoutMs);
}
