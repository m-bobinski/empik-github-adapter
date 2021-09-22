package com.empik.githubadapter.model.exception;

public class CounterAlreadyExistsException extends Exception {

    public CounterAlreadyExistsException() {
    }

    public CounterAlreadyExistsException(String message) {
        super(message);
    }

    public CounterAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public CounterAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public CounterAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
