package com.hp.tvbook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hp.tvbook.model.bean.OrdersBean;
import com.hp.tvbook.model.db.Profilepackage;

/**
 * Created by liuwei on 2017/4/24.
 */
@Repository
public interface OrderRepo extends JpaRepository<Profilepackage, Long>, JpaSpecificationExecutor<Profilepackage> {
	  
	@Query(value="SELECT p.profile_package_id, p.profile_type_tid, p.profile_package_state,p.created_dt,p.profile_package_imsi,b.bound_profile_matching_id,b.bound_profile_state from profile_package p LEFT JOIN bound_profile b on p.profile_package_tid=b.profile_package_tid where p.profile_package_id=?1",nativeQuery=true)
	List<Object[]> findOrderHistory1(String profilePackageId);
	
	@Query(value="SELECT p.profile_package_id, p.profile_type_tid, p.profile_package_state,p.created_dt,p.profile_package_imsi,b.bound_profile_matching_id,b.bound_profile_state from profile_package p LEFT JOIN bound_profile b on p.profile_package_tid=b.profile_package_tid where CAST(p.profile_package_state as varchar)=?1",nativeQuery=true)
	List<Object[]> findOrderHistory1State(String profilePacState);
	
	@Query(value="SELECT p.profile_package_id, p.profile_type_tid, p.profile_package_state,p.created_dt,p.profile_package_imsi,b.bound_profile_matching_id,b.bound_profile_state from profile_package p LEFT JOIN bound_profile b on p.profile_package_tid=b.profile_package_tid where to_char(p.created_dt, 'YYYY-MM-DD')=?1",nativeQuery=true)
	List<Object[]> findOrderHistory1Time(String profilePacTime);
	
	@Query(value="SELECT p.profile_package_id, p.profile_type_tid, p.profile_package_state,p.created_dt,p.profile_package_imsi,b.bound_profile_matching_id,b.bound_profile_state from profile_package p LEFT JOIN bound_profile b on p.profile_package_tid=b.profile_package_tid",nativeQuery=true)
	List<Object[]> findOrderHistoryNoCondition();
	
	@Query(value="SELECT p.profile_package_id, p.profile_type_tid, p.profile_package_state,p.created_dt,p.profile_package_imsi,b.bound_profile_matching_id,b.bound_profile_state from profile_package p LEFT JOIN bound_profile b on p.profile_package_tid=b.profile_package_tid where p.profile_package_id=?1 and CAST(p.profile_package_state as varchar)=?2",nativeQuery=true)
	List<Object[]> findOrderHistory2TidAndSate(String profilePackageId, String profilePacState);

	@Query(value="SELECT p.profile_package_id, p.profile_type_tid, p.profile_package_state,p.created_dt,p.profile_package_imsi,b.bound_profile_matching_id,b.bound_profile_state from profile_package p LEFT JOIN bound_profile b on p.profile_package_tid=b.profile_package_tid where p.profile_package_id=?1 and to_char(p.created_dt, 'YYYY-MM-DD')=?2",nativeQuery=true)
	List<Object[]> findOrderHistory2IdAndTime(String profilePackageId, String profilePacTime);
	
	@Query(value="SELECT p.profile_package_id, p.profile_type_tid, p.profile_package_state,p.created_dt,p.profile_package_imsi,b.bound_profile_matching_id,b.bound_profile_state from profile_package p LEFT JOIN bound_profile b on p.profile_package_tid=b.profile_package_tid where CAST(p.profile_package_state as varchar)=?1 and to_char(p.created_dt, 'YYYY-MM-DD')=?2",nativeQuery=true)
	List<Object[]> findOrderHistory2StateAndTime(String profilePacState, String profilePacTime);
	
	@Query(value="SELECT p.profile_package_id, p.profile_type_tid, p.profile_package_state,p.created_dt,p.profile_package_imsi,b.bound_profile_matching_id,b.bound_profile_state from profile_package p LEFT JOIN bound_profile b on p.profile_package_tid=b.profile_package_tid where p.profile_package_id=?1 and CAST(p.profile_package_state as varchar)=?2 and to_char(p.created_dt, 'YYYY-MM-DD')=?3",nativeQuery=true)
	List<Object[]> findOrderHistory3IdAndStateAndTime(String profilePackageId, String profilePacState, String profilePacTime);
	
	
}
