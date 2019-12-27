package com.saas.api.admin.controller.sys.auth;

import com.saas.api.admin.annotation.AuthRuleAnnotation;
import com.saas.api.admin.req.sys.auth.UserDataRequest;
import com.saas.api.admin.res.select.sys.UserSelect;
import com.saas.api.admin.service.sys.auth.UserService;
import com.saas.api.admin.service.sys.CompanyService;
import com.saas.api.admin.service.sys.DepartmentService;
import com.saas.api.admin.service.sys.GroupService;
import com.saas.api.admin.service.sys.auth.RoleAdminService;
import com.saas.api.admin.service.sys.auth.RoleService;
import com.saas.api.common.entity.sys.auth.User;
import com.saas.api.common.constant.RequestParamConstant;
import com.saas.api.common.constant.ResponseCodeI18n;
import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.sys.Company;
import com.saas.api.common.entity.sys.Department;
import com.saas.api.common.entity.sys.Group;
import com.saas.api.common.entity.sys.auth.RoleAdmin;
import com.saas.api.common.util.ApiResultI18n;
import com.saas.api.common.util.PasswordUtils;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.*;

/**
 * @Description: 用户相关接口
 * @Author: 徐未
 * @Date: 2019/11/20
 */
@RestController
@Slf4j
public class UserController {
	
	@Autowired
    private GroupService groupService;

	@Autowired
    private CompanyService companyService;
	
    @Autowired
    private DepartmentService departmentService;

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    @Resource
    private RoleAdminService roleAdminService;
    

    /**
     * *获取管理员列表
     * @AuthRuleAnnotation("admin/system/auth/user/queryList")
     */
    @PostMapping("/admin/system/auth/user/queryList")
    public ApiResultI18n queryList(@RequestBody String params,
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
        Integer adminId = Integer.valueOf(xAdminId);
        
        String lanType = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.LAN_TYPE))){
        	lanType = jsonObj.getString(RequestParamConstant.LAN_TYPE);
        }
        
    	Integer adminIdSelect = null;
        if (!StringUtils.isEmpty(jsonObj.get("adminIdSelect"))){
        	adminIdSelect = jsonObj.getInt("adminIdSelect");
        }
        
        User adminUser = userService.findByPrimayKey(Long.valueOf(adminId));
        Integer groupId = adminUser.getGroupId();
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.GROUP_ID))){
        	groupId = jsonObj.getInt(RequestParamConstant.GROUP_ID);
        } 
        Integer companyId = adminUser.getCompanyId();
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.COMPANY_ID))){
        	companyId = jsonObj.getInt(RequestParamConstant.COMPANY_ID);
        }
        Integer departmentId = adminUser.getDepartmentId();
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.DEPARTMENT_ID))){
        	departmentId = jsonObj.getInt(RequestParamConstant.DEPARTMENT_ID);
        }
        
        Page page = new Page(pageNo, pageSize);
        Map<String, Object> result = userService.queryListData(groupId, companyId, departmentId, adminIdSelect, lanType, page);

        return ApiResultI18n.success(result, lanType);

    }


    /**
     * *获取角色列表
     * @AuthRuleAnnotation("admin/system/auth/user/querySelectList")
     */
    @PostMapping("/admin/system/auth/user/querySelectList")
    public ApiResultI18n querySelectList(@RequestBody String params,
    		HttpServletRequest request) {
    	log.debug(params);
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	String xAdminId = request.getHeader("X-AdminId");
    	Long adminId = Long.valueOf(xAdminId);
        
        String lanType = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.LAN_TYPE))){
        	lanType = jsonObj.getString(RequestParamConstant.LAN_TYPE);
        }
        
        User adminUser = userService.findByPrimayKey(adminId);
        Integer groupId = adminUser.getGroupId();
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.GROUP_ID))){
        	groupId = jsonObj.getInt(RequestParamConstant.GROUP_ID);
        } 
        Integer companyId = adminUser.getCompanyId();
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.COMPANY_ID))){
        	companyId = jsonObj.getInt(RequestParamConstant.COMPANY_ID);
        }
        Integer departmentId = adminUser.getDepartmentId();
        
        User record = new User();
        record.setGroupId(groupId);
        record.setCompanyId(companyId);
        record.setDepartmentId(departmentId);
        record.setLanType(lanType);
        List<User> serviceList = userService.findByObject(record);
        List<UserSelect> resultList = new ArrayList<UserSelect>();
        for(User forTemp : serviceList) {
        	UserSelect userSelect = new UserSelect();
        	userSelect.copyData(forTemp);
        	resultList.add(userSelect);
        }
        		
        Map<String, Object> result = new HashMap<>();
    	result.put("dataList", resultList);
        return ApiResultI18n.success(result, lanType);

    }


    /**
     * *新增
     * @return
     */
    @AuthRuleAnnotation("admin/system/auth/user/addData")
    @PostMapping("/admin/system/auth/user/addData")
    public ApiResultI18n addData(@RequestBody UserDataRequest userRequest) {
        // 检查是否存在相同名称的管理员
        User check = userService.findByName(userRequest.getUserName());
        if (check != null) {
        	return ApiResultI18n.failure(ResponseCodeI18n.USER_IS_EXIST.getCode(), 
    				ResponseCodeI18n.USER_IS_EXIST.getMessage(), userRequest.getLanType());
        }

        User user = new User();
        BeanUtils.copyProperties(userRequest, user);
        
        Group checkGroup = groupService.findByPrimayKey(user.getGroupId());
    	if(checkGroup == null) {
    		return ApiResultI18n.failure(ResponseCodeI18n.GROUP_IS_NOT_EXIST.getCode(), 
    				ResponseCodeI18n.GROUP_IS_NOT_EXIST.getMessage(), user.getLanType());
    	}else {
    		user.setGroupName(checkGroup.getGroupName());
    	}
    	
        if(user.getCompanyId() != null) {
        	Company checkCompany = companyService.findByPrimayKey(user.getCompanyId());
        	if(checkCompany == null) {
        		return ApiResultI18n.failure(ResponseCodeI18n.COMPANY_IS_NOT_EXIST.getCode(), 
        				ResponseCodeI18n.COMPANY_IS_NOT_EXIST.getMessage(), user.getLanType());
        	}else {
        		user.setCompanyName(checkCompany.getCompanyName());
        	}
        }
        if(user.getDepartmentId() != null) {
        	Department checkDepartment = departmentService.findByPrimayKey(user.getDepartmentId());
        	if(checkDepartment == null) {
        		return ApiResultI18n.failure(ResponseCodeI18n.DEPARTMENT_IS_NOT_EXIST.getCode(), 
        				ResponseCodeI18n.DEPARTMENT_IS_NOT_EXIST.getMessage(), user.getLanType());
        	}else {
        		user.setDepartmentName(checkDepartment.getDepartmentName());
        	}
        }
        
        if (user.getPassword() != null) {
        	user.setPassword(PasswordUtils.authAdminPwd(user.getPassword()));
        }
        if (user.getRealName() == null) {
        	user.setRealName(user.getUserName());
        	user.setNickName(user.getUserName());
        }else {
        	user.setNickName(user.getRealName());
        }
        
        Integer insertAdminId = userService.insertData(user);

        // 插入角色
        if (userRequest.getRoleId() != null) {
        	RoleAdmin record = new RoleAdmin();
        	record.setAdminId(insertAdminId);
        	record.setRoleId(userRequest.getRoleId());
            roleAdminService.insertData(record);
        }

        return ApiResultI18n.success(userRequest.getLanType());
    }
    
    /**
     * *重置密码
     * @return
     */
    @AuthRuleAnnotation("admin/system/auth/user/resetPasswod")
    @PostMapping("/admin/system/auth/user/resetPasswod")
    public ApiResultI18n resetPasswod(@RequestBody UserDataRequest userRequest) {
        if (userRequest.getAdminId() == null || userRequest.getPassword() == null) {
        	return ApiResultI18n.failure(ResponseCodeI18n.PARAM_ERROR.getCode(), 
    				ResponseCodeI18n.PARAM_ERROR.getMessage(), userRequest.getLanType());
        }
        
        Long adminId = userRequest.getAdminId();
        User check = userService.findByPrimayKey(adminId);
        if (check == null) {
        	return ApiResultI18n.failure(ResponseCodeI18n.USER_IS_NOT_EXIST.getCode(), 
    				ResponseCodeI18n.USER_IS_NOT_EXIST.getMessage(), userRequest.getLanType());
        }

        User user = new User();
        user.setAdminId(userRequest.getAdminId());
        user.setPassword(PasswordUtils.authAdminPwd(userRequest.getPassword()));

        userService.resetPassword(user);

        return ApiResultI18n.success(userRequest.getLanType());
    }
    
    /**
     * *修改
     * @return
     */
    @AuthRuleAnnotation("admin/system/auth/user/updateData")
    @PostMapping("/admin/system/auth/user/updateData")
    public ApiResultI18n updateData(@RequestBody UserDataRequest userRequest) {

        if (userRequest.getAdminId() == null) {
        	return ApiResultI18n.failure(ResponseCodeI18n.PARAM_ERROR.getCode(), 
    				ResponseCodeI18n.PARAM_ERROR.getMessage(), userRequest.getLanType());
        }
        
        Long adminId = userRequest.getAdminId();
        User check = userService.findByPrimayKey(adminId);
        if (check == null) {
        	return ApiResultI18n.failure(ResponseCodeI18n.USER_IS_NOT_EXIST.getCode(), 
    				ResponseCodeI18n.USER_IS_NOT_EXIST.getMessage(), userRequest.getLanType());
        }
        
        // 检查是否存在相同账号的管理员
        User checkByName = userService.findByName(userRequest.getUserName());
        if (checkByName != null && !userRequest.getAdminId().equals(checkByName.getAdminId())) {
        	return ApiResultI18n.failure(ResponseCodeI18n.USER_IS_EXIST.getCode(), 
    				ResponseCodeI18n.USER_IS_EXIST.getMessage(), userRequest.getLanType());
        }

        User user = new User();
        BeanUtils.copyProperties(userRequest, user);
        if (user.getPassword() != null) {
        	//user.setPassword(PasswordUtils.authAdminPwd(user.getPassword()));
        }

        userService.updateData(user);

        // 修改角色
        if (userRequest.getRoleId() != null) {
        	RoleAdmin record = roleAdminService.findByAdminId(adminId.intValue());
        	if(!record.getRoleId().equals(userRequest.getRoleId())) {
        		record.setRoleId(userRequest.getRoleId());
        		roleAdminService.updateDataByAdminId(record);
        	}
        }
        
        return ApiResultI18n.success(userRequest.getLanType());
    }

    /**
     * *删除
     * @return
     */
    @AuthRuleAnnotation("admin/system/auth/user/deleteData")
    @PostMapping("/admin/system/auth/user/deleteData")
    public ApiResultI18n deleteData(@RequestBody String params) {
    	log.debug(params);
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	
    	String lanType = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.LAN_TYPE))){
        	lanType = jsonObj.getString(RequestParamConstant.LAN_TYPE);
        }
        
        Long adminId = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.ADMIN_ID))){
        	adminId = jsonObj.getLong(RequestParamConstant.ADMIN_ID);
        }else {
        	return ApiResultI18n.failure(ResponseCodeI18n.PARAM_ERROR.getCode(), 
        			ResponseCodeI18n.PARAM_ERROR.getMessage(),lanType);
        }
        
        userService.deleteByPrimayKey(adminId);
        roleAdminService.deleteByAdminId(adminId.intValue());

    	return ApiResultI18n.success(lanType);
    }


}
