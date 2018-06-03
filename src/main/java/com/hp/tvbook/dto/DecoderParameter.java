package com.hp.tvbook.dto;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

/**
 * Created by liuwei on 2017/1/19.
 */
@Data
public class DecoderParameter {
	 @NotBlank
    private String profile_package_id;
}
