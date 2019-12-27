package com.saas.api.admin.controller.sys.auth;

import com.saas.api.admin.annotation.AuthRuleAnnotation;
import com.saas.api.admin.req.sys.auth.RoleRequest;
import com.saas.api.admin.res.select.sys.RoleSelect;
import com.saas.api.admin.res.sys.auth.MenuResponse;
import com.saas.api.admin.service.sys.auth.MenuService;
import com.saas.api.admin.service.sys.auth.RoleAdminService;
import com.saas.api.admin.service.sys.auth.RoleMenuService;
import com.saas.api.admin.service.sys.auth.RoleService;
import com.saas.api.admin.service.sys.auth.UserService;
import com.saas.api.common.constant.CommonConstant;
import com.saas.api.common.constant.RequestParamConstant;
import com.saas.api.common.constant.ResponseCodeI18n;
import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.sys.auth.RoleMenu;
import com.saas.api.common.entity.sys.auth.User;
import com.saas.api.common.entity.sys.auth.Menu;
import com.saas.api.common.entity.sys.auth.Role;
import com.saas.api.common.entity.sys.auth.RoleAdmin;
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
import java.util.stream.Collectors;

/**
 * 角色相关
 */
@RestController
@Slf4j
public class RoleController {

    @Resource
    private UserService userService;
    
    @Resource
    private RoleService roleService;
    
    @Resource
    private MenuService menuService;

    @Resource
    private RoleMenuService roleMenuService;

    @Resource
    private RoleAdminService roleAdminService;

    /**
     * 角色列表 @AuthRuleAnnotation("admin/system/auth/role/queryList")
     */
    @PostMapping("/admin/system/auth/role/queryList")
    public ApiResultI18n listData(@RequestBody String params,
    		HttpServletRequest request) {
    	log.debug(params);
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	
    	Integer pageNo = 0;
        Integer pageSize = 20;
        
        if (null != jsonObj.get(RequestParamConstant.PAGE_NO)){
            pageNo = jsonObj.getInt(RequestParamConstant.PAGE_NO);
        }
        if (null != jsonObj.get(RequestParamConstant.PAGE_SIZE)){
            pageSize = jsonObj.getInt(RequestParamConstant.PAGE_SIZE);
        }
        
        String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        User user = userService.findByPrimayKey(adminId);
        
        Integer groupId = null;
        Integer companyId = null;
        if(adminId.intValue() != 1) {
        	groupId = user.getGroupId();
        	companyId = user.getCompanyId();
        }
        
        Long roleId = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.ROLE_ID))){
        	roleId = jsonObj.getLong(RequestParamConstant.ROLE_ID);
        }
        String lanType = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.LAN_TYPE))){
        	lanType = jsonObj.getString(RequestParamConstant.LAN_TYPE);
        }
    	
    	Page page = new Page(pageNo, pageSize);
    	Map<String, Object> result = roleService.getListData(groupId, companyId, roleId, lanType, page);
    	return ApiResultI18n.success(result, lanType);
    }
    
    /**
     * 查询下拉列表
     * @return @AuthRuleAnnotation("admin/system/auth/role/querySelectList")
     */
    @PostMapping("/admin/system/auth/role/querySelectList")
    public ApiResultI18n listSelectData(@RequestBody String params,
    		HttpServletRequest request) {
    	log.debug(params);
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	
    	String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        User user = userService.findByPrimayKey(adminId);
        
        Integer groupId = null;
        Integer companyId = null;
        
        if(adminId.intValue() != 1) {
        	groupId = user.getGroupId();
        	companyId = user.getCompanyId();
        }
        
        String lanType = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.LAN_TYPE))){
        	lanType = jsonObj.getString(RequestParamConstant.LAN_TYPE);
        }
        
        Role record = new Role();
        record.setGroupId(groupId);
        record.setCompanyId(companyId);
        record.setLanType(lanType);
        
    	List<Role> serviceList = roleService.findByObject(record);
    	List<RoleSelect> resultList = new ArrayList<RoleSelect>();
    	for(Role forTemp : serviceList) {
    		RoleSelect selecttmp = new RoleSelect();
    		selecttmp.copyData(forTemp);
    		resultList.add(selecttmp);
    	}
    	
    	Map<String, Object> result = new HashMap<>();
    	result.put("dataList", resultList);
        return ApiResultI18n.success(result, lanType);
    }
    
    /**
     * 添加数据
     * @return
     */
    @AuthRuleAnnotation("admin/system/auth/role/addData")
    @PostMapping("/admin/system/auth/role/addData")
    public ApiResultI18n insertData(@RequestBody Role role,
    		HttpServletRequest request){
    	Role check = roleService.findByName(role.getRoleName());
    	if(check != null) {
    		return ApiResultI18n.failure(ResponseCodeI18n.ROLE_IS_EXIST.getCode(), 
    				ResponseCodeI18n.ROLE_IS_EXIST.getMessage(), role.getLanType());
    	}
    	
    	String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        User user = userService.findByPrimayKey(adminId);
        
        Integer groupId = user.getGroupId();
        Integer companyId = user.getCompanyId();
        role.setGroupId(groupId);
        role.setCompanyId(companyId);
    	
    	role.setCreateTime(new Date());
    	role.setStatus(CommonConstant.INT_ONE);
    	roleService.insertData(role);
    	
    	return ApiResultI18n.success(role.getLanType());
    }
    
    /**
     * 修改信息
     * @return
     */
    @AuthRuleAnnotation("admin/system/auth/role/updateData")
    @PostMapping("/admin/system/auth/role/updateData")
    public ApiResultI18n updateData(@RequestBody Role role) {
    	Role check = roleService.findByName(role.getRoleName());
    	if(check != null) {
    		if(check.getRoleId().intValue() != role.getRoleId().intValue()) {
				return ApiResultI18n.failure(ResponseCodeI18n.ROLE_IS_EXIST.getCode(), 
						ResponseCodeI18n.ROLE_IS_EXIST.getMessage(), role.getLanType());
			}
    	}
    	
    	role.setUpdateTime(new Date());
    	roleService.updateData(role);

    	return ApiResultI18n.success(role.getLanType());
    }
    
    /**
     * 删除信息
     * @return
     */
    @AuthRuleAnnotation("admin/system/auth/role/deleteData")
    @PostMapping("/admin/system/auth/role/deleteData")
    public ApiResultI18n deleteById(@RequestBody String params) {
    	log.debug(params);
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	
    	String lanType = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.LAN_TYPE))){
        	lanType = jsonObj.getString(RequestParamConstant.LAN_TYPE);
        }
        
        Long roleId = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.ROLE_ID))){
        	roleId = jsonObj.getLong(RequestParamConstant.ROLE_ID);
        }else {
        	return ApiResultI18n.failure(ResponseCodeI18n.PARAM_ERROR.getCode(), 
        			ResponseCodeI18n.PARAM_ERROR.getMessage(),lanType);
        }
        
        List<RoleAdmin> checkList = roleAdminService.findByRoleId(roleId.intValue());
        if(checkList != null && checkList.size() > 0) {
        	return ApiResultI18n.failure(ResponseCodeI18n.MENU_HAVE_DOWN_MENUS.getCode(), 
        			ResponseCodeI18n.MENU_HAVE_DOWN_MENUS.getMessage(),lanType);
    	}
        
        roleMenuService.deleteByRoleId(roleId.intValue());
        roleService.deleteByPrimayKey(roleId);

    	return ApiResultI18n.success(lanType);
    }

    /**
     * 获取授权列表
     * @param id
     * @return @AuthRuleAnnotation("admin/system/auth/role/queryAuthList")
     */
    @PostMapping("/admin/system/auth/role/queryAuthList")
    public ApiResultI18n getAuthList(@RequestBody String params,
    		HttpServletRequest request) {
    	log.debug(params);
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	
    	String lanType = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.LAN_TYPE))){
        	lanType = jsonObj.getString(RequestParamConstant.LAN_TYPE);
        }
        
        String xAdminId = request.getHeader("X-AdminId");
        Integer adminId = Integer.valueOf(xAdminId);
        
        Integer roleId = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.ROLE_ID))){
        	roleId = jsonObj.getInt(RequestParamConstant.ROLE_ID);
        }

        // 查询当前角色拥有的权限id
        List<RoleMenu> checkedRoleMenuList = roleMenuService.listByRoleId(roleId);
        List<Integer> checkedKeys = checkedRoleMenuList.stream()
                .map(RoleMenu::getMenuId)
                .collect(Collectors.toList());

        // 查询所有权限规则
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
        
        if(adminId.intValue() == 1) {
        	Menu record = new Menu();
        	record.setLanType(lanType);
        	menuList = menuService.findByObject(record);
        }
        
        
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

        Map<String, Object> result = new HashMap<>();
        result.put("dataList", resultList);
        result.put("checkedMenuIds", checkedKeys);
        
        return ApiResultI18n.success(result, lanType);
    }

    @AuthRuleAnnotation("admin/system/auth/role/authMenuToRole")
    @PostMapping("/admin/system/auth/role/authMenuToRole")
    public ApiResultI18n authMenuToRole(@RequestBody RoleRequest roleRequest) {
        // 先删除之前的授权
    	roleMenuService.deleteByRoleId(roleRequest.getRoleId());
    	if(roleRequest.getMenuIds() != null && roleRequest.getMenuIds().size() > 0) {
    		List<RoleMenu> roleMenuList = roleRequest.getMenuIds().stream()
                    .map(aMenuId -> {
                        RoleMenu roleMenu = new RoleMenu();
                        roleMenu.setRoleId(roleRequest.getRoleId());
                        roleMenu.setMenuId(aMenuId);
                        roleMenu.setType("admin");
                        return roleMenu;
                    }).collect(Collectors.toList());
    		
    		roleMenuService.insertRoleMenuAll(roleMenuList);
    	}
    	
        return ApiResultI18n.success(roleRequest.getLanType());
    }


}
