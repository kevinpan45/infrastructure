package io.github.kevinpan45.common.exception;

public class BasicRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BasicRuntimeException(String message) {
        super(message);
    }

    public BasicRuntimeException(String message, Exception e) {
        super(message, e);
    }
}
