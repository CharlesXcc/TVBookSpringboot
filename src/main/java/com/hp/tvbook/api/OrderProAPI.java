package com.hp.tvbook.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hp.tvbook.dto.OrderPro;
import com.hp.tvbook.model.RestResponse;
import com.hp.tvbook.service.OrderProService;

@RestController
@CrossOrigin
@RequestMapping("/")
public class OrderProAPI {
	
	private static final Logger logger = LoggerFactory.getLogger(OrderProAPI.class);

    @Autowired
    private OrderProService orderProService;
    
	
	@PostMapping(value="orderPro", produces = { "application/json" })
	public RestResponse orderHistory(@RequestBody OrderPro orderPro) {
		RestResponse response = null;
		String iccid = orderPro.getIccid();
		String imsi = orderPro.getImsi();

	    response = orderProService.getOrderProfile(iccid, imsi);
	    
        
        return response;
	}
}
