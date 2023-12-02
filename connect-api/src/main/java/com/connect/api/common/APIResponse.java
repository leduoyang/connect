package com.connect.api.common;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class APIResponse<T> {

    private int status;
    private String message;
    private T data;
    private String error;

    public APIResponse() {
    }

    public APIResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public APIResponse(int status, String message, String error) {
        this.status = status;
        this.message = message;
        this.error = error;
    }

    public static <T> APIResponse<T> getOKJsonResult(T data) {
        return new APIResponse<>(200, "OK", data);
    }

    public static <T> APIResponse<T> getErrorJsonResult(int status, String code, String message) {
        return new APIResponse<>(status, code, message);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public enum Type {
        INVALID(400),
        GENERAL(200),
        SYS(500);

        final int code;

        Type(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }
}
