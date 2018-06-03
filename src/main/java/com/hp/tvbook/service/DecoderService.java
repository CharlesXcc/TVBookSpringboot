package com.hp.tvbook.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hp.tvbook.model.RestResponse;
import com.hp.tvbook.model.bean.DecoderBean;
import com.hp.tvbook.model.db.Profilepackage;
import com.hp.tvbook.repository.DecoderRepo;
import com.hpe.sim.profile.pedefinitions.ProfileElement;
import com.hpe.sim.smdp.nds.profile.generation.lib.EncodeDecode;
import com.hpe.sim.smdp.nds.profile.generation.lib.PE;

/**
 * Created by liuwei on 2017/5/3.
 */
@Service
@Transactional
public class DecoderService {
    private static final Logger logger = LoggerFactory.getLogger(DecoderService.class);
    @Autowired
    private DecoderRepo decoderRepo;
    @Autowired
    private LocaleMessageSourceService messageSource;


    public RestResponse findPackageBerAndDecoder(String profilePackageId) {
    	logger.info("order history get start");
    	
    	
    	String profile_package_ber = decoderRepo.findPackageBer(profilePackageId);
    	String profile_package_ber_decoder="";
    	if (!("").equals(profile_package_ber) && profile_package_ber != null) {
			try {
				profile_package_ber_decoder = getDecodedJSON(profile_package_ber);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}

    	DecoderBean returnOrderInfo = new DecoderBean();
    	returnOrderInfo.setProfile_package_ber_decoder(profile_package_ber_decoder);
    	
    	 return RestResponse.ok(messageSource.getMessage("decoder.ok"), returnOrderInfo);
    }
//    
//    
//    public DecoderBean getBean(String profilePackageId, List<Object[]> searchResuts) {
//
//    	DecoderBean decoderBean = null;
//    	if (searchResuts != null && searchResuts.size() > 0) {
//    		decoderBean = new DecoderBean();
//    		Object[] searchResut = (Object[]) searchResuts.get(0);
//
//    		String profile_package_ber="";
//    		if (searchResut[0] != null) {
//    			profile_package_ber = searchResut[0].toString();
//    		}
//    		
//    		String profile_package_ber_decoder="";
//			try {
//				profile_package_ber_decoder = getDecodedJSON(profile_package_ber);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//    		
//    	    logger.info("convert 1");
//
//    	    decoderBean.setProfile_package_id(profilePackageId);
//    	    decoderBean.setProfile_package_ber_decoder(profile_package_ber_decoder);
//    		logger.info("decoder end");
//    	}
//    	
//		return decoderBean;
//    }
    
    public static String getDecodedJSON(String profile) throws IOException {
        EncodeDecode decoder = new EncodeDecode();
        List<PE> pes = new ArrayList<PE>();
        decoder.decodeDER(profile, pes, new ProfileElement());
        Map<String, Object> json = new LinkedHashMap<String, Object>(pes.size());
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        for(int i=pes.size()-1; i>=0; i--) {
            Object pe = pes.get(i).getPeObject();
            json.put(pe.getClass().getSimpleName(), pe);
        }
        return gson.toJson(json);
    }
}
