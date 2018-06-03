package com.hp.tvbook.model.db;


import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import lombok.Data;

/**
 * Created by liuwei on 2017/1/24.
 */
@MappedSuperclass
@Data
public abstract class IdEntity implements Serializable {

    //    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
 
}