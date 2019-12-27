package com.saas.api.admin.res.sys.auth;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * @Description: 菜单返回信息
 * @Author: 徐未
 * @Date: 2019/11/19
 */
@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class MenuResponse {
	
	private Integer menuId;
	private Integer pid;
	private String menuUrl;
	private String menuRule;
	private String menuTitle;
	private String expression;
	private Integer listorder;
	private String iconclass;
	private Integer status;

    // 一次性加载所有权限规则生成 tree 树形节点时需要
    private List<MenuResponse> children;

}
