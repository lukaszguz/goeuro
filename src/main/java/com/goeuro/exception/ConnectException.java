package com.goeuro.exception;

public class ConnectException extends RuntimeException {

    public ConnectException() {
    }

    public ConnectException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectException(Throwable cause) {
        super(cause);
    }
}