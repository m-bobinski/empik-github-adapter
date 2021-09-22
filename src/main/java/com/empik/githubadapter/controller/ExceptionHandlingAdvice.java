package com.empik.githubadapter.controller;

import brave.Tracer;
import com.empik.githubadapter.model.ErrorDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
class ExceptionHandlingAdvice {

    private final Tracer tracer;

    @ExceptionHandler(HttpClientErrorException.class)
    private ResponseEntity<ErrorDetails> handleHttpClientErrorException(HttpClientErrorException e) {
        if (e instanceof HttpClientErrorException.NotFound) {
            return new ResponseEntity<>(ErrorDetails.builder().message("User not found").build(), HttpStatus.NOT_FOUND);
        } else {
            log.error("Error occurred when requesting github", e);
            return buildInternalServerErrorResponse();
        }
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<ErrorDetails> handleGenericException(Exception e) {
        log.error("Error occurred when processing request", e);
        return buildInternalServerErrorResponse();
    }

    private ResponseEntity<ErrorDetails> buildInternalServerErrorResponse() {
        return new ResponseEntity<>(ErrorDetails.builder()
                .message("Internal error occurred")
                .spanId(tracer.currentSpan().context().spanIdString())
                .traceId(tracer.currentSpan().context().traceIdString())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
