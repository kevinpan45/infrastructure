package io.kp45.exception;

public class BasicRuntimeException extends RuntimeException {
    public BasicRuntimeException(String msg) {
        super(msg);
    }

    public BasicRuntimeException(String msg, Exception e) {
        super(msg, e);
    }
}
