package com.hp.tvbook.model.bean;

import java.sql.Timestamp;

import lombok.Data;

/**
 * Created by liuwei on 2017/4/24
 */
@Data
public class OrdersBean  {
	
    private String profile_package_id; 
    private String profile_type_tid;
    private String Profile_package_state;
    private String created_dt;
    private String profile_package_imsi;
    private String bound_profile_matching_id;
    private String bound_profile_state;

}
