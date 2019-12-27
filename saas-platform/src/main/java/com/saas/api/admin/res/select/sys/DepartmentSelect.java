package com.saas.api.admin.res.select.sys;

import com.saas.api.common.entity.sys.Department;

import lombok.Data;

/**
 * @Description: 账号所属部门
 * @Author: 徐未
 * @Date: 2019/11/18
 */
@Data
public class DepartmentSelect {
	    
    // 部门ID 主键
	private Integer departmentId;
	
    private String departmentName;
    
    public void copyData(Department record) {
    	this.departmentId = record.getDepartmentId();
    	this.departmentName = record.getDepartmentName();
    }
    
    
}
