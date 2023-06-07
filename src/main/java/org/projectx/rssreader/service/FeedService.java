package org.projectx.rssreader.service;

import org.projectx.rssreader.model.Feed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface FeedService {
    Feed save(Feed feed);
    Page<Feed> getAllFeeds(Pageable pageable);
    Optional<Feed> getFeedById(Long id);
    Optional<Feed> getFeedByTitle(String title);
}
