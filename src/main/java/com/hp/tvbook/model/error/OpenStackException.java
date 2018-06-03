package com.hp.tvbook.model.error;

import lombok.Data;

/**
 * Created by xinch on 2017/1/6.
 */
@Data
public class OpenStackException {
    private int code;
    private String message;
    private String details;
}
