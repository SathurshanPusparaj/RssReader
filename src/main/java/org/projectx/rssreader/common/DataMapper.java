package org.projectx.rssreader.common;

import org.projectx.rssreader.dto.FeedResponse;
import org.projectx.rssreader.model.Feed;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;


public final class DataMapper {

    public static FeedResponse convert(Feed feed) {
        return FeedResponse.builder()
                .id(feed.getId())
                .title(feed.getTitle())
                .link(feed.getLink())
                .desc(feed.getDesc())
                .pubDate(feed.getPubDate()).build();
    }

    public static Iterable<FeedResponse> convert(Iterable<Feed> feed) {
        List<FeedResponse> responseList = new ArrayList<>();
        feed.forEach(item -> responseList.add(convert(item)));
        return responseList;
    }

    public static Page<FeedResponse> convert(Page<Feed> feed) {
        return feed.map(item -> convert(item));
    }
}
