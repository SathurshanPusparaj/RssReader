package org.projectx.rssreader.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ServerConfiguration {

    @Bean
    public RestTemplate template() {
        return new RestTemplate();
    }
}
