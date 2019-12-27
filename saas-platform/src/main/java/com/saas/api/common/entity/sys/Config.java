package com.saas.api.common.entity.sys;

import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 系统参数配置
 * @Author: 徐未
 * @Date: 2019/11/12
 */
@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class Config {
	    
    // 主键
    private Integer id;
    
    private String keyName;
    
    private String keyValue;
	
    private String description;
    
    private Date createTime;
    
    private Date updateTime;
    
    private String lanType;
    
    
}
