package com.saas.api.admin.res.select.sys;

import com.saas.api.common.entity.sys.auth.User;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 用户人员下拉选择
 * @Author: 徐未
 * @Date: 2019/11/20
 */
@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class UserSelect {
	
    // 主键
    private Long adminId;
    
    // 昵称
    private String nickName;
    
    public void copyData(User user) {
    	this.adminId = user.getAdminId();
    	this.nickName = user.getNickName();
    }
    
}
