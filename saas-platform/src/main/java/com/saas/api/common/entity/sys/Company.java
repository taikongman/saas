package com.saas.api.common.entity.sys;

import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 账号所属公司
 * @Author: 徐未
 * @Date: 2019/11/12
 */
@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class Company {
	    
    // 公司ID 主键
    private Integer companyId;
    
    // 公司名称
    private String companyName;
    
    // 组ID 主键
    private Integer groupId;
    
    // 组名称
    private String groupName;
    
    private String contact;
    
    private String phoneno;
	
    private String description;
    
    private String provinceId;
    private String cityId;
    private String areaId;
    
    private String province;
    private String city;
    private String area;
    private String address;
    
    private String detailAddress;
    
    private String logo;
    
    private String logo_url;
    
    //1 add 2delete
    private Integer status;
    
    private Date createTime;
    
    private Date updateTime;
    
    private String lanType;
    
    
}
