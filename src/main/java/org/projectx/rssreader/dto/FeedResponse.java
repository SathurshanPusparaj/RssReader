package org.projectx.rssreader.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeedResponse {

    private Long id;

    private String title;

    private String link;

    private String desc;

    private LocalDateTime pubDate;
}
