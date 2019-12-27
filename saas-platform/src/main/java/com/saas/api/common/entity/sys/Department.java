package com.saas.api.common.entity.sys;

import java.util.Date;

import lombok.Data;

/**
 * @Description: 账号所属部门
 * @Author: 徐未
 * @Date: 2019/11/18
 */
@Data
public class Department {
	    
    // 部门ID 主键
	private Integer departmentId;
	
	// 组ID 主键
    private Integer groupId;
    
    // 组名称
    private String groupName;
	
	// 公司ID
    private Integer companyId;
    
    // 公司名称
    private String companyName;
    
    private String departmentName;
    
    private String contact;
    
    private String phoneno;
	
    private String description;
    
    //1 add 2delete
    private Integer status;
    
    private Date createTime;
    
    private Date updateTime;
    
    private String lanType;
    
    
}
