package org.arya.banking.admin.mapper;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.common.acl.AclOperation;
import org.arya.banking.admin.dto.TopicDescriptionDto;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Slf4j
@Component
public class TopicDescriptionMapper extends BaseMapper<TopicDescription, TopicDescriptionDto> {

    @Override
    public TopicDescription convertToEntity(TopicDescriptionDto dto) {
        return null;
    }

    @Override
    public TopicDescriptionDto convertToDto(TopicDescription entity) {

        if (null != entity) {
            return new TopicDescriptionDto(
                    entity.name(), entity.partitions().size(), entity.isInternal(),
                    null != entity.authorizedOperations() ?
                            entity.authorizedOperations().stream().map(AclOperation::code).collect(Collectors.toSet()) : null
            );
        }
        return null;
    }
}
