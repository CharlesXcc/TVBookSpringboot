package com.hp.tvbook.model;

import lombok.Data;

/**
 * Created by xinch on 2016/1/5.
 */
@Data
public class RestResponse {
    private int code;
    private Boolean success;
    private String message;
    private Object data;

    public RestResponse(int code, boolean success, String message, Object data) {
        this.code = code;
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static RestResponse error(int code, String message, Object data) {
        return new RestResponse(code, false, message, data);
    }

    public static RestResponse error(String message, Object data) {
        return new RestResponse(400, false, message, data);
    }

    public static RestResponse error(int code, String message) {
        return new RestResponse(code, false, message, null);
    }

    public static RestResponse error(String message) {
        return new RestResponse(400, false, message, null);
    }

    public static RestResponse ok(String message) {
        return new RestResponse(200, true, message, null);
    }

    public static RestResponse ok(String message, Object data) {
        return new RestResponse(200, true, message, data);
    }

    public static RestResponse ok(int code, String message) {
        return new RestResponse(code, true, message, null);
    }
}