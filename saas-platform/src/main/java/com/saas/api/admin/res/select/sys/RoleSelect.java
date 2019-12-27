package com.saas.api.admin.res.select.sys;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.saas.api.common.entity.sys.auth.Role;

/**
 * @Description: 角色定义
 * @Author: 徐未
 * @Date: 2019/11/19
 */
@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class RoleSelect {

  private Long roleId;
  
  private String roleName;
  
  public void copyData(Role role) {
	  this.roleId = role.getRoleId();
	  this.roleName = role.getRoleName();
  }
  
}
