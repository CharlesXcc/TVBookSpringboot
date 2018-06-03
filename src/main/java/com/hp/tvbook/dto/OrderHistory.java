package com.hp.tvbook.dto;

import lombok.Data;

/**
 * Created by liuwei on 2017/1/19.
 */
@Data
public class OrderHistory {
    private String profile_package_id;

    private String profile_package_state;
    
    private String created_dt;
    
}
