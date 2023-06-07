package org.projectx.rssreader.controller;

import org.projectx.rssreader.common.DataMapper;
import org.projectx.rssreader.dto.FeedResponse;
import org.projectx.rssreader.exception.FeedNotFoundException;
import org.projectx.rssreader.model.Feed;
import org.projectx.rssreader.service.FeedService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class FeedController {

    private final FeedService feedService;

    public FeedController(FeedService feedService) {
        this.feedService = feedService;
    }

    @GetMapping(value = "/feeds/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FeedResponse> getFeedById(@PathVariable Long id) {
        Optional<Feed> feed = feedService.getFeedById(id);

        if (feed.isPresent()) {
            return new ResponseEntity<>(DataMapper.convert(feed.get()), HttpStatus.OK);
        } else {
            throw new FeedNotFoundException("Unable to find the feed based on the id: " + id);
        }
    }

    @GetMapping(value = "/feeds", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<FeedResponse>> getFeedByPage(@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size,
                                                            @RequestParam(defaultValue = "pubDate") String sort,
                                                            @RequestParam(defaultValue = "DESC") String direction) {
        PageRequest pr = PageRequest.of(page, size , Sort.by(Sort.Direction.valueOf(direction), sort));
        Page<Feed> feeds = feedService.getAllFeeds(pr);
        return new ResponseEntity<>(DataMapper.convert(feeds), HttpStatus.OK);
    }
}
