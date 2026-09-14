package org.arya.banking.admin.controller;

import lombok.RequiredArgsConstructor;
import org.arya.banking.admin.annotation.AllowedRoles;
import org.arya.banking.admin.dto.TopicCreationDto;
import org.arya.banking.admin.dto.TopicDescriptionDto;
import org.arya.banking.admin.dto.TopicListingDto;
import org.arya.banking.admin.service.KafkaAdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.arya.banking.admin.annotation.AdminOperations.KAFKA_OPS;

@RestController
@RequestMapping("/api/admin/")
@RequiredArgsConstructor
public class KafkaAdminController {

    private final KafkaAdminService kafkaAdminService;

    @GetMapping("/topics")
    @AllowedRoles(KAFKA_OPS)
    public ResponseEntity<CompletableFuture<List<TopicListingDto>>> listTopics(@RequestParam boolean listInternal, @RequestParam Integer timeoutMs)  {
        return ResponseEntity.ok(kafkaAdminService.listTopics(listInternal, timeoutMs));
    }

    @PostMapping("/topics")
    @AllowedRoles(KAFKA_OPS)
    public ResponseEntity<CompletableFuture<?>> createTopic(@RequestBody TopicCreationDto topicCreationDto) {
        return ResponseEntity.ok(kafkaAdminService.createTopic(topicCreationDto));
    }

    @GetMapping("/topics/describe")
    @AllowedRoles(KAFKA_OPS)
    public ResponseEntity<CompletableFuture<List<TopicDescriptionDto>>> describe(@RequestParam List<String> topicNames, @RequestParam Integer timeoutMs)  {
        return ResponseEntity.ok(kafkaAdminService.describeTopics(topicNames, timeoutMs));
    }
}
