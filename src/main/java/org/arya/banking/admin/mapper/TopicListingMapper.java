package org.arya.banking.admin.mapper;

import org.apache.kafka.clients.admin.TopicListing;
import org.arya.banking.admin.dto.TopicListingDto;
import org.springframework.stereotype.Component;

@Component
public class TopicListingMapper extends BaseMapper<TopicListing, TopicListingDto> {

    @Override
    public TopicListing convertToEntity(TopicListingDto dto) {
        if (null != dto) {
            return new TopicListing(dto.name(), null, dto.isInternal());
        }
        return null;
    }

    @Override
    public TopicListingDto convertToDto(TopicListing entity) {
        if (null != entity) {
            return new TopicListingDto(entity.name(), entity.isInternal());
        }
        return null;
    }
}
