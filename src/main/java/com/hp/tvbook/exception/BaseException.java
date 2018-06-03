package com.hp.tvbook.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * Created by xinch on 2016/11/18.
 */
@Data
public class BaseException extends RuntimeException {
    private HttpStatus status;
    private String errorMessage;
    private Object data;

    public BaseException(String errorMessage) {
        super(errorMessage);
        this.status = HttpStatus.BAD_REQUEST;
        this.errorMessage = errorMessage;
        this.data = null;
    }

    public BaseException(HttpStatus status, String errorMessage) {
        super(errorMessage);
        this.status = status;
        this.errorMessage = errorMessage;
        this.data = null;
    }

    public BaseException(HttpStatus status, String errorMessage, Object data) {
        super(errorMessage);
        this.status = status;
        this.errorMessage = errorMessage;
        this.data = data;
    }
}