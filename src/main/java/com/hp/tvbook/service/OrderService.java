package com.hp.tvbook.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hp.tvbook.model.RestResponse;
import com.hp.tvbook.model.bean.OrdersBean;
import com.hp.tvbook.repository.OrderRepo;

/**
 * Created by liuwei on 2017/5/3.
 */
@Service
@Transactional
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private LocaleMessageSourceService messageSource;


    public RestResponse findOrderHistory1(String profilePackageId) {
    	logger.info("order history get start");
    	
    	
    	List<Object[]> searchResults = orderRepo.findOrderHistory1(profilePackageId);

    	List<OrdersBean> returnOrderInfo = getlistBean(searchResults);
    	
    	 return RestResponse.ok(messageSource.getMessage("order.search.ok"), returnOrderInfo);
    }
    
    public RestResponse findOrderHistory1State(String profilePacState) {
    	logger.info("order history get start");
    	
    	
    	List<Object[]> searchResults = orderRepo.findOrderHistory1State(profilePacState);

    	List<OrdersBean> returnOrderInfo = getlistBean(searchResults);
    	
    	 return RestResponse.ok(messageSource.getMessage("order.search.ok"), returnOrderInfo);
    }
    
    public RestResponse findOrderHistory1Time(String profilePacTime) {
    	logger.info("order history get start");
    	
    	
    	List<Object[]> searchResults = orderRepo.findOrderHistory1Time(profilePacTime);

    	List<OrdersBean> returnOrderInfo = getlistBean(searchResults);
    	
    	 return RestResponse.ok(messageSource.getMessage("order.search.ok"), returnOrderInfo);
    }
    
    public RestResponse findOrderHistoryNoCondition() {
    	logger.info("order history get start");
    	
    	
    	List<Object[]> searchResults = orderRepo.findOrderHistoryNoCondition();

    	List<OrdersBean> returnOrderInfo = getlistBean(searchResults);
    	
    	 return RestResponse.ok(messageSource.getMessage("order.search.ok"), returnOrderInfo);
    }
    
    public RestResponse findOrderHistory2TidAndSate(String profilePackageId, String profilePacState) {
    	logger.info("order history get start");
    	
    	
    	List<Object[]> searchResults = orderRepo.findOrderHistory2TidAndSate(profilePackageId, profilePacState);

    	List<OrdersBean> returnOrderInfo = getlistBean(searchResults);
    	
    	 return RestResponse.ok(messageSource.getMessage("order.search.ok"), returnOrderInfo);
    }
    
    public RestResponse findOrderHistory2IdAndTime(String profilePackageId, String profilePacTime) {
    	logger.info("order history get start");
    	
    	
    	List<Object[]> searchResults = orderRepo.findOrderHistory2IdAndTime(profilePackageId, profilePacTime);

    	List<OrdersBean> returnOrderInfo = getlistBean(searchResults);
    	
    	 return RestResponse.ok(messageSource.getMessage("order.search.ok"), returnOrderInfo);
    }
    
    public RestResponse findOrderHistory2StateAndTime(String profilePacState, String profilePacTime) {
    	logger.info("order history get start");
    	
    	
    	List<Object[]> searchResults = orderRepo.findOrderHistory2StateAndTime(profilePacState, profilePacTime);

    	List<OrdersBean> returnOrderInfo = getlistBean(searchResults);
    	
    	 return RestResponse.ok(messageSource.getMessage("order.search.ok"), returnOrderInfo);
    }
    
    public RestResponse findOrderHistory3IdAndStateAndTime(String profilePackageId, String profilePacState, String profilePacTime) {
    	logger.info("order history get start");
    	
    	
    	List<Object[]> searchResults = orderRepo.findOrderHistory3IdAndStateAndTime(profilePackageId, profilePacState, profilePacTime);

    	List<OrdersBean> returnOrderInfo = getlistBean(searchResults);
    	
    	 return RestResponse.ok(messageSource.getMessage("order.search.ok"), returnOrderInfo);
    }
    
    
    public List<OrdersBean> getlistBean(List<Object[]> searchResuts) {
	List<OrdersBean> returnOrderInfo = new ArrayList();
	
	if (searchResuts != null) {
	
    	for (int i=0;i<searchResuts.size();i++) {
    		OrdersBean ordersBean = new OrdersBean();
    		Object[] searchResut = (Object[]) searchResuts.get(i);
    		String profile_package_id = "";
    		if (searchResut[0] != null) {
    	      profile_package_id = (String) searchResut[0];
    		}
    		String profile_type_tid="";
    		if (searchResut[1] != null) {
    			profile_type_tid = searchResut[1].toString();
    		}
    	    logger.info("convert 1");
    	    String Profile_package_state="";
    	    if (searchResut[2] != null) {
    	    	Profile_package_state = searchResut[2].toString();
    	    }
    	    logger.info("convert 2");
    	    String created_dt="";
    	    if (searchResut[3] != null) {
    	    	created_dt = searchResut[3].toString();
    	    }
    	    logger.info("convert 3");
    	    String profile_package_imsi="";
    	    if (searchResut[4] != null) {
    	    	profile_package_imsi =  searchResut[4].toString();
    	    }
    	    logger.info("convert 4");
    	    String bound_profile_matching_id="";
    	    if (searchResut[5] != null) {
    	    	bound_profile_matching_id = searchResut[5].toString();
    	    }
    	    logger.info("convert 5");
    	    String bound_profile_state = "";
    	    if (searchResut[6] != null) {
    	    	bound_profile_state = searchResut[6].toString();
    	    }
    	    logger.info("convert 6");
    	    ordersBean.setProfile_package_id(profile_package_id);
    	    ordersBean.setProfile_type_tid(profile_type_tid);
    	    ordersBean.setProfile_package_state(Profile_package_state);
    	    ordersBean.setCreated_dt(created_dt);
    	    ordersBean.setProfile_package_imsi(profile_package_imsi);
    	    ordersBean.setBound_profile_matching_id(bound_profile_matching_id);
    	    ordersBean.setBound_profile_state(bound_profile_state);
    	    returnOrderInfo.add(ordersBean);
    		logger.info("convert end");
    	}
    	
	}
		return returnOrderInfo;
    }
}
