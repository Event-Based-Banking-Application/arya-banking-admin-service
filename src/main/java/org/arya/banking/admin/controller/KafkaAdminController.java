package org.arya.banking.admin.controller;

import lombok.RequiredArgsConstructor;
import org.arya.banking.admin.annotation.AllowedRoles;
import org.arya.banking.admin.dto.TopicListingDto;
import org.arya.banking.admin.service.KafkaAdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
