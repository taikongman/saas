package com.saas.api.common.entity.sys.auth;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Description: 用户人员账号信息
 * @Author: 徐未
 * @Date: 2019/11/12
 */
@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class Menu {

  private Integer menuId;
  private Integer pid;
  private String menuUrl;
  private String menuRule;
  private String menuTitle;
  private String expression;
  private Integer listorder;
  private String iconclass;
  private Integer status;
  private Date createTime;
  private Date updateTime;
  private String lanType;
  
}
