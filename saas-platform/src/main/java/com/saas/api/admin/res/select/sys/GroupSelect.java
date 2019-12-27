package com.saas.api.admin.res.select.sys;


import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import com.saas.api.common.entity.sys.Group;

import lombok.AccessLevel;

/**
 * 账号所属组织
 * @author by 九尾 2019-11-11
 *
 */
@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class GroupSelect {
	    
    // 公司ID 主键
    private Integer groupId;
    
    // 公司名称
    private String groupName;
    
    public void copyData(Group group) {
    	this.groupId = group.getGroupId();
    	this.groupName = group.getGroupName();
    }
    
}
