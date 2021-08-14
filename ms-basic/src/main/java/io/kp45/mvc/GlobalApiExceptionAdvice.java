package io.kp45.mvc;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.kp45.exception.BizRuntimeException;

@ControllerAdvice
public class GlobalApiExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<String> handleReqParamError() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing request parameter or spec error.");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Request parameter parse error.");
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<String> handleMultipartException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File upload error.");
    }

    @ExceptionHandler(BizRuntimeException.class)
    public ResponseEntity<ApiResponse<String>> handleBizError(BizRuntimeException e) {
        ApiResponse<String> body = new ApiResponse<>();
        body.setCode("400");
        body.setMsg(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleServerError() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE.value()).body("System Internal error.");
    }
}
