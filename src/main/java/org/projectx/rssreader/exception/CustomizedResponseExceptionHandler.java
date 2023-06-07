package org.projectx.rssreader.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class CustomizedResponseExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleAllException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(generateExceptionResponse(ex, request), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FeedNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleFeedNotFoundException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(generateExceptionResponse(ex, request), HttpStatus.NOT_FOUND);
    }

    private ExceptionResponse generateExceptionResponse(Exception e, WebRequest request) {
        return new ExceptionResponse(new Date(), e.getMessage(), request.getDescription(false));
    }
}
