package com.hp.tvbook.model.db;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

/**
 * Created by liuwei on 2017/4/24
 */
@Data
@Entity(name = "profile_package")
public class Profilepackage {
	
	@Id	
    private Long profilePackageTid;
	private String profilePackageId;
	private String profilePackageName;
    private String profileTypeTid;
    
    private String profilePackageBer;

    private String profilePackagePpk;

	private String profilePackageState;

    
}
