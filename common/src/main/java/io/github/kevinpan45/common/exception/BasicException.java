package io.github.kevinpan45.common.exception;

public class BasicException extends Exception {
    public BasicException(String message) {
        super(message);
    }

    public BasicException(String message, Exception e) {
        super(message, e);
    }
}
