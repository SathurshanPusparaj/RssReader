package org.projectx.rssreader;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.projectx.rssreader.repository.FeedRepository;
import org.projectx.rssreader.service.FeedService;
import org.projectx.rssreader.service.impl.FeedServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfiguration {

    @Bean
    ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        return objectMapper;
    }

    @Bean
    FeedService feedService(FeedRepository feedRepository) {
        return new FeedServiceImpl(feedRepository);
    }
}
