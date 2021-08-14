package io.kp45.mvc;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private String code;
    private String msg;
    private T data;
}
