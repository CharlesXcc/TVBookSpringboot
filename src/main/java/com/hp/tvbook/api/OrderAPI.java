package com.hp.tvbook.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hp.tvbook.dto.OrderHistory;
import com.hp.tvbook.model.RestResponse;
import com.hp.tvbook.service.OrderService;

@RestController
@CrossOrigin
@RequestMapping("/")
public class OrderAPI {
	
	private static final Logger logger = LoggerFactory.getLogger(OrderAPI.class);

    @Autowired
    private OrderService orderService;
    
	
	@PostMapping(value="viewOrderHistory", produces = { "application/json" })
	public RestResponse orderHistory(@RequestBody OrderHistory orderHistory) {
		
	    String profile_package_id = orderHistory.getProfile_package_id();

	    String profile_package_state = orderHistory.getProfile_package_state();
	    
	    String created_dt = orderHistory.getCreated_dt();
	    
	    logger.info(profile_package_id + "11111111" + profile_package_state + "2222222222" + created_dt);
	    
	    RestResponse response = null;
	    
	    if (profile_package_id.equals("") && profile_package_state.equals("") && created_dt.equals("")) {
	    	response = orderService.findOrderHistoryNoCondition();
	    }
	    
	    if (!profile_package_id.equals("") && profile_package_state.equals("") && created_dt.equals("")) {
	    	response = orderService.findOrderHistory1(profile_package_id);
	    	
	    }
	    
	    if (profile_package_id.equals("") && !profile_package_state.equals("") && created_dt.equals("")) {
	    	response = orderService.findOrderHistory1State(profile_package_state);
	    	
	    }
	    
	    if (profile_package_id.equals("") && profile_package_state.equals("") && !created_dt.equals("")) {
	    	response = orderService.findOrderHistory1Time(created_dt);
	    	
	    }
	    
	    if (!profile_package_id.equals("") && !profile_package_state.equals("") && created_dt.equals("")) {
	    	response = orderService.findOrderHistory2TidAndSate(profile_package_id, profile_package_state);
	    	
	    }
	    
	    if (!profile_package_id.equals("") && profile_package_state.equals("") && !created_dt.equals("")) {
	    	response = orderService.findOrderHistory2IdAndTime(profile_package_id,created_dt);
	    }
	    
	    if (profile_package_id.equals("") && !profile_package_state.equals("") && !created_dt.equals("")) {
	    	response = orderService.findOrderHistory2StateAndTime(profile_package_state, created_dt);
	    }
	    
	    if (!profile_package_id.equals("") && !profile_package_state.equals("") && !created_dt.equals("")) {
	    	response = orderService.findOrderHistory3IdAndStateAndTime(profile_package_id, profile_package_state, created_dt);
	    }
	    
        
        return response;
	}
}
