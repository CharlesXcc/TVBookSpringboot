package com.hp.tvbook.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hp.tvbook.dto.DecoderParameter;
import com.hp.tvbook.model.RestResponse;
import com.hp.tvbook.service.DecoderService;

@RestController
@CrossOrigin
@RequestMapping("/")
public class ProfileDecoderAPI {
	
	private static final Logger logger = LoggerFactory.getLogger(ProfileDecoderAPI.class);

    @Autowired
    private DecoderService decoderService;
    
	
	@PostMapping(value="decoder", produces = { "application/json" })
	public RestResponse orderHistory(@RequestBody DecoderParameter decoderParameter) {
		RestResponse response = null;
	    String profile_package_id = decoderParameter.getProfile_package_id();

	    response = decoderService.findPackageBerAndDecoder(profile_package_id);
	    
        
        return response;
	}
}
