package io.kp45.mvc;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.kp45.exception.BasicRuntimeException;
import io.kp45.web.ApiResponse;

@ControllerAdvice
public class GlobalApiExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<ApiResponse<String>> handleMultipartException() {
        ApiResponse<String> body = ApiResponse.error("File upload error.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(BasicRuntimeException.class)
    public ResponseEntity<ApiResponse<String>> handleBizError(BasicRuntimeException e) {
        ApiResponse<String> body = ApiResponse.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleServerError() {
        ApiResponse<String> body = ApiResponse.error(500, "System Internal error.");
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE.value()).body(body);
    }
}
