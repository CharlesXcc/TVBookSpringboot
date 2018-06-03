package com.hp.tvbook.model.db;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

/**
 * Created by liuwei on 2017/4/24
 */
@Data
@Entity(name = "bound_profile")
public class Boundprofile extends IdEntity {
	
	@Id	
    private String bound_profile_tid;
	private String bound_profile_id;
	private String profile_package_tid;
    private String bound_profile_matching_id;
    
    private String bound_profile_confirmation_code;

    private String bound_profile_download_counter;

	private String euicc_tid;

    private String sk_alias;
    private String bound_profile_state;
    private String created_dt;

}
