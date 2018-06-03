package com.hp.tvbook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hp.tvbook.model.bean.DecoderBean;
import com.hp.tvbook.model.db.Profilepackage;

/**
 * Created by liuwei on 2017/4/24.
 */
@Repository
public interface DecoderRepo extends JpaRepository<Profilepackage, Long>, JpaSpecificationExecutor<Profilepackage> {
	  
	@Query(value="SELECT p.profile_package_ber from profile_package p where p.profile_package_id=?1",nativeQuery=true)
	String findPackageBer(String profilePackageId);
	

	
	
}
