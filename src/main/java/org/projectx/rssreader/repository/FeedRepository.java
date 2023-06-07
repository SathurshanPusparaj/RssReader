package org.projectx.rssreader.repository;

import org.projectx.rssreader.model.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FeedRepository extends JpaRepository<Feed, Long> {
    Optional<Feed> findByTitle(String title);
}
