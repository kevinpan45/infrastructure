package io.kp45.mvc;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.kp45.exception.BasicRuntimeException;
import io.kp45.web.ApiResponse;

@ControllerAdvice
public class GlobalApiExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<String>> handleReqParamError() {
        ApiResponse<String> body = ApiResponse.error("Missing request parameter or spec error.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<String>> handleHttpMessageNotReadableException() {
        ApiResponse<String> body = ApiResponse.error("Request parameter parse error.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

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
