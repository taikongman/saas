package com.saas.api.admin.service.sys.auth.impl;

import com.saas.api.admin.res.sys.auth.MenuResponse;
import com.saas.api.admin.service.sys.auth.*;
import com.saas.api.common.constant.CacheConstant;
import com.saas.api.common.constant.CommonConstant;
import com.saas.api.common.entity.sys.auth.RoleMenu;
import com.saas.api.common.entity.sys.auth.Menu;
import com.saas.api.common.entity.sys.auth.RoleAdmin;
import com.saas.api.common.util.CacheUtils;
import com.saas.api.common.util.MenuTreeUtils;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
@Slf4j
public class AuthLoginServiceImpl implements AuthLoginService {

    @Resource
    private RoleAdminService roleAdminService;

    @Resource
    private RoleMenuService roleMenuService;
    
    @Resource
    private MenuService menuService;



    /**
     * 根据 管理员id 获取权限
     * @param adminId
     * @return
     */
    @Override
    public List<MenuResponse> listRuleByAdminId(Long adminId) {
    	log.debug(adminId.toString());
        
        // 获取角色id
        RoleAdmin roleAdmin = roleAdminService.findByAdminId(adminId.intValue());
        
        // 获取授权的规则        
        List<RoleMenu> roleMenuList = roleMenuService.listByRoleId(roleAdmin.getRoleId());
        List<Integer> menuIdList = new ArrayList<Integer>();
        for(RoleMenu forTemp : roleMenuList) {
        	menuIdList.add(forTemp.getMenuId());
        }
        
        List<Menu> menuList = menuService.listDataByIdIn(menuIdList);
        List<MenuResponse> resultList = new ArrayList<MenuResponse>();
        for(Menu forMenu : menuList) {
        	if(forMenu.getPid().intValue() == CommonConstant.INT_ZERO.intValue()) {
        		MenuResponse menuResponse = new MenuResponse();
                BeanUtils.copyProperties(forMenu, menuResponse);
                resultList.add(menuResponse);
        	}
        }
        
        for(MenuResponse forMenuRes : resultList) {
        	forMenuRes.setChildren(MenuTreeUtils.merge(menuList, forMenuRes.getMenuId()));
        }
        
        return resultList;
    }



	@Override
	public List<String> listMenuUrlByAdminId(Long adminId) {
		List<String> authRules = new ArrayList<>();
		// 超级管理员
        if (adminId.equals(1L)) {
            authRules.add("admin");
            return authRules;
        }
        
        // 如果存在，先从缓存中获取权限
        String aarKey = String.format(CacheConstant.ADMIN_AUTH_RULES, adminId);
        if (CacheUtils.hasKey(aarKey)) {
            return new ArrayList<>(CacheUtils.sGetMembers(aarKey));
        }
        
        // 获取角色id
        RoleAdmin roleAdmin = roleAdminService.findByAdminId(adminId.intValue());
        
        // 获取授权的规则        
        List<RoleMenu> roleMenuList = roleMenuService.listByRoleId(roleAdmin.getRoleId());
        List<Integer> menuIdList = new ArrayList<Integer>();
        for(RoleMenu forTemp : roleMenuList) {
        	menuIdList.add(forTemp.getMenuId());
        }
        
        List<Menu> menuList = menuService.listDataByIdIn(menuIdList);
        List<MenuResponse> resultList = new ArrayList<MenuResponse>();
        for(Menu forMenu : menuList) {
        	if(forMenu.getPid().intValue() == CommonConstant.INT_ZERO.intValue()) {
        		MenuResponse menuResponse = new MenuResponse();
                BeanUtils.copyProperties(forMenu, menuResponse);
                resultList.add(menuResponse);
        	}
        	authRules.add(forMenu.getMenuRule());
        }
        
        // 如果为空，则添加一个空值
        if (authRules.isEmpty()) {
            authRules.add("");
        }
        
        String[] strings = authRules.toArray(new String[0]);
        CacheUtils.sAdd(aarKey, strings);
        CacheUtils.expire(aarKey, 36000L); // 两小时后过期

        return authRules;
	}

    
}
