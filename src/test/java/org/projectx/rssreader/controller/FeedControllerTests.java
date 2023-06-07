package org.projectx.rssreader.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.projectx.rssreader.model.Feed;
import org.projectx.rssreader.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FeedController.class)
public class FeedControllerTests {

    @MockBean
    private FeedService feedService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testGetFeedById() throws Exception {
        Optional<Feed> feed = Optional.ofNullable(Feed.builder().id(1L)
                .title("Sudan officials fear for historical artefacts threatened by fighting")
                .desc("Warring factions urged to preserve heritage after video clip appears to show fighters raiding Khartoum museum")
                .link("https://www.theguardian.com/world/2023/jun/05/sudan-officials-fear-for-historical-artefacts-threatened-by-fighting")
                .pubDate(LocalDateTime.now()).build());

        when(feedService.getFeedById(eq(1l))).thenReturn(feed);

        mockMvc.perform(get("/feeds/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(feed.get().getId()))
                .andExpect(jsonPath("$.title").value(feed.get().getTitle()))
                .andExpect(jsonPath("$.desc").value(feed.get().getDesc()));

    }


    @Test
    void testGetFeedById_Exception() throws Exception {
        when(feedService.getFeedById(eq(1l))).thenReturn(Optional.empty());

        mockMvc.perform(get("/feeds/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value( "Unable to find the feed based on the id: 1"));

    }

    @Test
    void testGetFeedByPage() throws Exception {
        URL resource = this.getClass().getResource("/result.json");
        List<Feed> feeds = objectMapper.readValue(resource, new TypeReference<>() {});

        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<Feed> page = new PageImpl(feeds, pageRequest, 0);

        when(feedService.getAllFeeds(Mockito.any())).thenReturn(page);

        mockMvc.perform(get("/feeds")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andExpect(status().isOk());
    }

}
