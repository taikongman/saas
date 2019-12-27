package com.saas.api.common.entity.sys.auth;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Description: 角色定义
 * @Author: 徐未
 * @Date: 2019/11/19
 */
@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class Role {

  private Long roleId;
  
  private Integer groupId;
  
  private Integer companyId;
  
  private String roleName;
  
  private Long pid;
  
  private Integer status;
  
  private String remark;
  
  private Date createTime;
  
  private Date updateTime;
  
  private Integer listorder;
  
  private String lanType;
}
