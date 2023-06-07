package org.projectx.rssreader.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.projectx.rssreader.model.Feed;
import org.projectx.rssreader.service.FeedService;
import org.projectx.rssreader.util.DateTimeParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@Slf4j
public class FeedPollService {

    private final String feedUrl;

    private final FeedService feedService;

    private final RestTemplate restTemplate;

    public FeedPollService(@Value("${rss.feed.url}") String feedUrl,
                           FeedService feedService,
                           RestTemplate restTemplate) {
        this.feedUrl = feedUrl;
        this.feedService = feedService;
        this.restTemplate = restTemplate;
    }

    @Scheduled(fixedDelay = 300000)
    public void play() {
        try {
            String data = pollFeed();
            Document doc = Jsoup.parse(data);

            for (Element element : doc.select("item")) {
                Feed feed = getFeedFromElement(element);
                Optional<Feed> feedEntity = this.feedService.getFeedByTitle(selectTextFromElement(element, "title"));

                if (feedEntity.isPresent()) {
                    feed.setId(feedEntity.get().getId());
                }
                this.feedService.save(feed);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    private String pollFeed() {
        return this.restTemplate.getForObject(this.feedUrl, String.class);
    }

    private Feed getFeedFromElement(Element element) {
        String title = selectTextFromElement(element,"title");
        String desc = selectTextFromElement(element,"description");
        String link = selectTextFromElement(element,"guid");
        String pubDate = selectTextFromElement(element,"pubDate");

        return Feed.builder()
                .title(title)
                .desc(desc)
                .link(link)
                .pubDate(DateTimeParser.format(DateTimeParser.DateTimePattern.FIRST, pubDate))
                .build();
    }

    private String selectTextFromElement(Element element, String tag) {
        return element.selectFirst(tag).text();
    }
}
