package com.saas.api.admin.controller.sys.auth;

import com.saas.api.admin.annotation.AuthRuleAnnotation;
import com.saas.api.admin.res.sys.auth.MenuResponse;
import com.saas.api.admin.service.sys.auth.RoleMenuService;
import com.saas.api.admin.service.sys.auth.MenuService;
import com.saas.api.admin.service.sys.auth.RoleAdminService;
import com.saas.api.common.constant.CommonConstant;
import com.saas.api.common.constant.RequestParamConstant;
import com.saas.api.common.constant.ResponseCodeI18n;
import com.saas.api.common.entity.sys.auth.Menu;
import com.saas.api.common.entity.sys.auth.RoleAdmin;
import com.saas.api.common.entity.sys.auth.RoleMenu;
import com.saas.api.common.util.ApiResultI18n;
import com.saas.api.common.util.MenuTreeUtils;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 权限规则相关
 */
@RestController
@Slf4j
public class MenuController {

    @Resource
    private MenuService menuService;
    
    @Resource
    private RoleAdminService roleAdminService;
    
    @Resource
    private RoleMenuService roleMenuService;

    /**
     * 列表
     * @return @AuthRuleAnnotation("admin/system/auth/menu/queryList")
     */
    @PostMapping("/admin/system/auth/menu/queryList")
    public ApiResultI18n listData(@RequestBody String params,
    		HttpServletRequest request) {
    	log.debug(params);
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	
    	String xAdminId = request.getHeader("X-AdminId");
        Integer adminId = Integer.valueOf(xAdminId);
        
        String lanType = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.LAN_TYPE))){
        	lanType = jsonObj.getString(RequestParamConstant.LAN_TYPE);
        }
        
        RoleAdmin roleAdmin = roleAdminService.findByAdminId(adminId);
        if(roleAdmin == null) {
        	return ApiResultI18n.failure(ResponseCodeI18n.GROUP_IS_NOT_EXIST.getCode(), 
    				ResponseCodeI18n.GROUP_IS_NOT_EXIST.getMessage(), lanType);
        }
        
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
        
        Map<String,Object> result = new HashMap<>();
        result.put("dataList", resultList);
        return ApiResultI18n.success(result, lanType);
    }
    
    /**
     * 新增
     * @param authPermissionRuleSaveRequest
     * @param bindingResult
     * @return
     */
    @AuthRuleAnnotation("admin/system/auth/menu/addData")
    @PostMapping("/admin/system/auth/menu/addData")
    public ApiResultI18n insertData(@RequestBody Menu menu) {
        if (menu.getPid() == null) {
        	// 默认设置
        	menu.setPid(CommonConstant.INT_ZERO);
        }else {
        	Menu checkMenu = menuService.findByPrimayKey(menu.getPid());
        	if(checkMenu == null) {
        		return ApiResultI18n.failure(ResponseCodeI18n.PARENT_MENU_IS_NOT_EXIST.getCode(), 
        				ResponseCodeI18n.PARENT_MENU_IS_NOT_EXIST.getMessage(), menu.getLanType());
        	}
        }
        
        Menu check = menuService.findByName(menu.getMenuTitle());
    	if(check != null) {
    		return ApiResultI18n.failure(ResponseCodeI18n.MENU_IS_EXIST.getCode(), 
    				ResponseCodeI18n.MENU_IS_EXIST.getMessage(), menu.getLanType());
    	}
    	
    	menu.setCreateTime(new Date());
    	menuService.insertData(menu);

        return ApiResultI18n.success(menu.getLanType());
    }

    /**
     * 编辑
     * @param authPermissionRuleSaveRequest
     * @param bindingResult
     * @return
     */
    @AuthRuleAnnotation("admin/system/auth/menu/updateData")
    @PostMapping("/admin/system/auth/menu/updateData")
    public ApiResultI18n edit(@RequestBody Menu menu) {

    	if (menu.getMenuId() == null) {
    		return ApiResultI18n.failure(ResponseCodeI18n.PARAM_ERROR.getCode(), 
    				ResponseCodeI18n.PARAM_ERROR.getMessage(), menu.getLanType());
        }
    	
    	menu.setUpdateTime(new Date());
    	menuService.updateData(menu);

        return ApiResultI18n.success(menu.getLanType());
    }

    /**
     * 删除
     * @param authPermissionRuleSaveRequest
     * @return
     */
    @AuthRuleAnnotation("admin/system/auth/menu/deleteData")
    @PostMapping("/admin/system/auth/menu/deleteData")
    public ApiResultI18n delete(@RequestBody String params) {
    	log.debug(params);
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	
    	String lanType = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.LAN_TYPE))){
        	lanType = jsonObj.getString(RequestParamConstant.LAN_TYPE);
        }
        
    	Integer menuId = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.MENU_ID))){
        	menuId = jsonObj.getInt(RequestParamConstant.MENU_ID);
        }else {
        	return ApiResultI18n.failure(ResponseCodeI18n.PARAM_ERROR.getCode(), 
        			ResponseCodeI18n.PARAM_ERROR.getMessage(),lanType);
        }
        
        Menu check = new Menu();
        check.setPid(menuId);
        List<Menu> checkList = menuService.findByObject(check);
        if(checkList != null && checkList.size() > 0) {
        	return ApiResultI18n.failure(ResponseCodeI18n.MENU_HAVE_DOWN_MENUS.getCode(), 
        			ResponseCodeI18n.MENU_HAVE_DOWN_MENUS.getMessage(),lanType);
    	}
        
        
        menuService.deleteByPrimayKey(menuId);
        roleMenuService.deleteByMenuId(menuId);
        
        return ApiResultI18n.success(lanType);
    }


}
