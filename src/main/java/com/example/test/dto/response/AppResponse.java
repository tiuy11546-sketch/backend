package com.example.test.dto.response;


public class AppResponse {
    private boolean success;
    private String message;
    private Object data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public AppResponse(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public AppResponse() {}

    public static AppResponse success(String message, Object data) {
        return new AppResponse(true, message, data);
    }

    public static AppResponse success(String message) {
        return new AppResponse(true, message, null);
    }

    public static AppResponse error(String message, Object data) {
        return new AppResponse(false, message, data);
    }

    public static AppResponse error(String message) {
        return new AppResponse(false, message, null);
    }
}
