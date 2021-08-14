package io.kp45.exception;

public class BizRuntimeException extends RuntimeException {
    public BizRuntimeException(String msg) {
        super(msg);
    }

    public BizRuntimeException(String msg, Exception e) {
        super(msg, e);
    }
}
