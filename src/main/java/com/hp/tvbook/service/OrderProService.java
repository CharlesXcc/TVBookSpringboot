package com.hp.tvbook.service;

import java.util.UUID;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hp.tvbook.model.RestResponse;
import com.hp.tvbook.model.bean.OrderProBean;
import com.hp.tvbook.util.Http;

/**
 * Created by liuwei on 2017/5/3.
 */
@Service
@Transactional
public class OrderProService {
    private static final Logger logger = LoggerFactory.getLogger(OrderProService.class);
    @Autowired
    private LocaleMessageSourceService messageSource;


    public RestResponse getOrderProfile(String iccid, String imsi) {
    	logger.info("getProvider start");
    	
		Http http = new Http();
		String callIdentifier = UUID.randomUUID().toString();
		String nameValue = "Profile-" + iccid;
		String path = "https://smdpplus:8443/smdpplus/gsma/rsp2/es2plus/OrderProfile";
		String body = "{\"header\":{\"functionRequesterIdentifier\":\"1.1.1.2\",\"functionCallIdentifier\":\"" + callIdentifier + "\"},\"name\":\"" + nameValue + "\", \"iccid\":\"" + iccid + "\",\"profileType\":\"ST_PROFILE_LOW_END\",\"imsi\":\"" + imsi + "\",\"msisdn\":\"\"}";
	
		String sendRes = "";
		try {
			sendRes = http.sendAPostRequest(path, body);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("88888888888888888" + sendRes + "999999999999999999");
		JSONObject sendResJson=null;
		try {
			sendResJson = new JSONObject(sendRes);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		
    	OrderProBean orderProBean = new OrderProBean();
    	String returnMessage = "";
    	try {
			JSONObject headerJson =  (JSONObject)sendResJson.get("header");
			JSONObject functionExecutionStatusJson =  (JSONObject)headerJson.get("functionExecutionStatus");
			returnMessage = functionExecutionStatusJson.getString("status");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	int successFlg = 0;
    	
    	if (("Executed-Success").equals(returnMessage)) {
    		successFlg = 0;
    	} else {
    		successFlg = 1;
    	}
    	orderProBean.setSuccessFlg(successFlg);
    	
    	 return RestResponse.ok(messageSource.getMessage("decoder.ok"), orderProBean);
    }
}
