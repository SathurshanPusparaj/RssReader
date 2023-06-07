package org.projectx.rssreader.service.impl;

import org.projectx.rssreader.model.Feed;
import org.projectx.rssreader.repository.FeedRepository;
import org.projectx.rssreader.service.FeedService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FeedServiceImpl implements FeedService {

    private final FeedRepository feedRepository;


    public FeedServiceImpl(FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    @Override
    public Feed save(Feed feed) {
        return feedRepository.save(feed);
    }

    @Override
    public Page<Feed> getAllFeeds(Pageable pageable) {
        return feedRepository.findAll(pageable);
    }

    @Override
    public Optional<Feed> getFeedById(Long id) {
        return feedRepository.findById(id);
    }

    @Override
    public Optional<Feed> getFeedByTitle(String title) {
        return feedRepository.findByTitle(title);
    }

}
