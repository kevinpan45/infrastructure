package io.github.kevinpan45.common.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.github.kevinpan45.common.exception.BasicException;
import io.github.kevinpan45.common.exception.BasicRuntimeException;

@ControllerAdvice
public class GlobalApiExcetionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<String> handleMultipartException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File upload error.");
    }

    @ExceptionHandler(BasicRuntimeException.class)
    public ResponseEntity<String> handleBusinessRuntimeError(BasicRuntimeException e) {
        logger.error("Server Internal Runtime Error", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("System Internal error.");
    }

    @ExceptionHandler(BasicException.class)
    public ResponseEntity<String> handleBusinessServerError(BasicException e) {
        logger.error("Server Internal Error", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("System Internal error.");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleServerError() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE.value()).body("System Internal error.");
    }
}
