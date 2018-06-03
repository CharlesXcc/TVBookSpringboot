package com.hp.tvbook.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * Created by xinch on 2016/11/18.
 */
@Data
public class NotFoundException extends BaseException {
    public NotFoundException(String errorMessage) {
        super(HttpStatus.NOT_FOUND, errorMessage);
    }

    public NotFoundException(String errorMessage, Object code) {
        super(HttpStatus.NOT_FOUND, errorMessage, code);
    }
}