package com.saas.api.common.entity.sys;

import java.util.Date;

import lombok.Data;

/**
 * 
 * @author xuwei 2019-06-11 APP软件自动升级配置
 *
 */
@Data
public class Softwareversion {
	    
    // 主键
    private Integer id;
    
    private String appname;
    
    private String osname;
	
    private String version;
    
    private String url;
    
    private String note;
	
    private String lantype;
    
    private Date createTime;
    
    private Date updateTime;
    
    private Integer isUpdate;
    
    
}
