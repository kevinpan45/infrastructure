package io.kp45.web;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private int code;
    private String msg = "";
    private T data;

    public static <T> ApiResponse<T> ok(T data) {
        ApiResponse<T> result = new ApiResponse<>();
        result.setData(data);
        return result;
    }

    public static ApiResponse<?> ok(String msg) {
        ApiResponse<?> result = new ApiResponse<>();
        result.setMsg(msg);
        return result;
    }

    public static <T> ApiResponse<T> ok(int code, T data) {
        ApiResponse<T> result = new ApiResponse<>();
        result.setCode(code);
        result.setData(data);
        return result;
    }

    public static <T> ApiResponse<T> ok(int code, String msg, T data) {
        ApiResponse<T> result = new ApiResponse<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static <T> ApiResponse<T> error(String msg) {
        ApiResponse<T> result = new ApiResponse<>();
        result.setCode(400);
        result.setMsg(msg);
        return result;
    }

    public static <T> ApiResponse<T> error(int code) {
        ApiResponse<T> result = new ApiResponse<>();
        result.setCode(code);
        return result;
    }

    public static <T> ApiResponse<T> error(int code, String msg) {
        ApiResponse<T> result = new ApiResponse<>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public ApiResponse<String> unauthenticated() {
        ApiResponse<String> result = new ApiResponse<>();
        result.setMsg("Unauthenticated request.");
        result.setCode(401);
        return result;
    }

    public ApiResponse<String> unauthorized() {
        ApiResponse<String> result = new ApiResponse<>();
        result.setMsg("Unauthorized request.");
        result.setCode(403);
        return result;
    }
}
