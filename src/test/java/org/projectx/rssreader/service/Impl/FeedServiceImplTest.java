package org.projectx.rssreader.service.Impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.projectx.rssreader.TestConfiguration;
import org.projectx.rssreader.model.Feed;
import org.projectx.rssreader.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@Import(TestConfiguration.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class FeedServiceImplTest {

    private List<Feed> feeds;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private FeedService feedService;

    @BeforeAll
    public void init() throws IOException {
        URL resource = this.getClass().getResource("/result.json");
        this.feeds = objectMapper.readValue(resource, new TypeReference<>() {});
    }

    @BeforeEach
    public void execute() {
        for (Feed feed : feeds) {
            feedService.save(feed);
        }
    }

    @Test
    @Order(1)
    public void testSave() {
        Feed feed = feedService.save(Feed.builder()
                .title("Australian economy grew 0.2% in first three months of the year, the slowest since Covid lockdowns")
                .link("https://dummylink")
                .desc("Jim Chalmers says higher interest rates and cost-of-living pressures are squeezing household budgets and slowing the economy")
                .pubDate(LocalDateTime.now()).build());
        Assertions.assertEquals(6L, feed.getId());
    }

    @Test
    @Order(2)
    public void testGetAllFeeds() {
        PageRequest pageRequest = PageRequest.of(0, 3);
        Page<Feed> page = feedService.getAllFeeds(pageRequest);
        Assertions.assertEquals(2, page.getTotalPages());
        Assertions.assertEquals(3, page.getContent().size());
    }

    @Test
    @Order(3)
    public void testGetFeedById() {
        Optional<Feed> feed = feedService.getFeedById(1L);
        Assertions.assertTrue(feed.isPresent());
        Assertions.assertEquals(1L, feed.get().getId());
    }

    @Test
    @Order(4)
    public void testGetFeedByTitle() {
        Optional<Feed> feed = feedService.getFeedByTitle(feeds.get(1).getTitle());
        Assertions.assertTrue(feed.isPresent());
        Assertions.assertEquals(2L, feed.get().getId());
        Assertions.assertEquals(feeds.get(1).getTitle(), feed.get().getTitle());
    }
}
