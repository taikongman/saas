package com.saas.api.common.entity.sys.auth;

import lombok.Data;

/**
 * 用户角色对应表
 */
@Data
public class RoleAdmin {
  private Integer id;
  private Integer roleId;
  private Integer adminId;

}
