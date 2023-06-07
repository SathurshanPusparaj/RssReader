package org.projectx.rssreader.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FeedNotFoundException extends RuntimeException {

    public FeedNotFoundException(String message) {
        super(message);
    }
}
