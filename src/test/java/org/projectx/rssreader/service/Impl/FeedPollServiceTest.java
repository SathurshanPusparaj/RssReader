package org.projectx.rssreader.service.Impl;

import org.junit.jupiter.api.*;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.projectx.rssreader.TestConfiguration;
import org.projectx.rssreader.model.Feed;
import org.projectx.rssreader.service.FeedService;
import org.projectx.rssreader.service.impl.FeedPollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.mockito.Mockito.when;

@DataJpaTest
@Import(TestConfiguration.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

public class FeedPollServiceTest {
    private final static String FEED_URL = "https://dummyurl";

    @Autowired
    FeedService feedService;

    @Mock
    RestTemplate restTemplate;

    FeedPollService feedPollService;

    @BeforeAll
    public void init(){
        MockitoAnnotations.openMocks(this);
        feedPollService = new FeedPollService(FEED_URL, feedService, restTemplate);
    }

    @Test
    public void testPlay() throws IOException {
        String resource = new String(this.getClass().getResourceAsStream("/rss.xml").readAllBytes());
        when(restTemplate.getForObject(FEED_URL, String.class)).thenReturn(resource);
        feedPollService.play();

        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<Feed> firstAttempt = feedService.getAllFeeds(pageRequest);
        Assertions.assertEquals(1, firstAttempt.getTotalPages());
        Assertions.assertEquals(2, firstAttempt.getContent().size());

        feedPollService.play();

        Page<Feed> secondAttempt = feedService.getAllFeeds(pageRequest);
        Assertions.assertEquals(1, secondAttempt.getTotalPages());
        Assertions.assertEquals(2, secondAttempt.getContent().size());
    }
}
