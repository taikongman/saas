package com.saas.api.admin.controller.sys;


import com.saas.api.admin.annotation.AuthRuleAnnotation;
import com.saas.api.admin.res.select.sys.DepartmentSelect;
import com.saas.api.admin.service.sys.CompanyService;
import com.saas.api.admin.service.sys.DepartmentService;
import com.saas.api.admin.service.sys.GroupService;
import com.saas.api.admin.service.sys.auth.UserService;
import com.saas.api.common.constant.CommonConstant;
import com.saas.api.common.constant.RequestParamConstant;
import com.saas.api.common.constant.ResponseCodeI18n;
import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.sys.Company;
import com.saas.api.common.entity.sys.Department;
import com.saas.api.common.entity.sys.Group;
import com.saas.api.common.entity.sys.auth.User;
import com.saas.api.common.util.ApiResultI18n;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 公司部分控制类
 * @Author: 徐未
 * @Date: 2019/11/12
 */
@RestController
@Slf4j
public class DepartmentController {
	
	@Autowired
    private GroupService groupService;

	@Autowired
    private CompanyService companyService;
	
    @Autowired
    private DepartmentService departmentService;
    
    @Resource
    private UserService userService;

    /**
     * * 查询列表
     * @return @AuthRuleAnnotation("admin/system/department/queryList")
     */
    @PostMapping(value = "/admin/system/department/queryList")
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
        
        Integer groupId = user.getGroupId();
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.GROUP_ID))){
        	groupId = jsonObj.getInt(RequestParamConstant.GROUP_ID);
        }
        
        Integer companyId = user.getCompanyId();
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.COMPANY_ID))){
        	companyId = jsonObj.getInt(RequestParamConstant.COMPANY_ID);
        }
        
        Integer departmentId = user.getDepartmentId();
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.DEPARTMENT_ID))){
        	departmentId = jsonObj.getInt(RequestParamConstant.DEPARTMENT_ID);
        }
        
        String lanType = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.LAN_TYPE))){
        	lanType = jsonObj.getString(RequestParamConstant.LAN_TYPE);
        }
        
    	Page page = new Page(pageNo, pageSize);
    	Map<String, Object> result = departmentService.getListData(groupId, companyId, departmentId, lanType, page);
    	
    	return ApiResultI18n.success(result, lanType);
    }

    /**
     * * 查询下拉列表
     * @return @AuthRuleAnnotation("admin/system/department/querySelectList")
     */
    @PostMapping("/admin/system/department/querySelectList")
    public ApiResultI18n listSelectData(@RequestBody String params,
    		HttpServletRequest request) {
    	log.debug(params);
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	
    	String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        User user = userService.findByPrimayKey(adminId);
        
        Integer groupId = user.getGroupId();
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.GROUP_ID))){
        	groupId = jsonObj.getInt(RequestParamConstant.GROUP_ID);
        }
        
        Integer companyId = user.getCompanyId();
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.COMPANY_ID))){
        	companyId = jsonObj.getInt(RequestParamConstant.COMPANY_ID);
        }
        Integer departmentId = user.getDepartmentId();
        
        String lanType = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.LAN_TYPE))){
        	lanType = jsonObj.getString(RequestParamConstant.LAN_TYPE);
        }
        
        Department record = new Department();
        record.setGroupId(groupId);
        record.setCompanyId(companyId);
        record.setDepartmentId(departmentId);
        
    	List<Department> serviceList = departmentService.findByObject(record);
    	List<DepartmentSelect> resultList = new ArrayList<DepartmentSelect>();
    	for(Department forTemp : serviceList) {
    		DepartmentSelect selecttmp = new DepartmentSelect();
    		selecttmp.copyData(forTemp);
    		resultList.add(selecttmp);
    	}
    	
    	Map<String, Object> result = new HashMap<>();
    	result.put("list", resultList);
    	return ApiResultI18n.success(result, lanType);
    }

    /**
     * * 添加数据
     * @return
     */
    @AuthRuleAnnotation("admin/system/department/addData")
    @PostMapping("/admin/system/department/addData")
    public ApiResultI18n insertData(@RequestBody Department department){
    	Department check = departmentService.findByName(department.getDepartmentName());
    	if(check != null) {
    		return ApiResultI18n.failure(ResponseCodeI18n.DEPARTMENT_IS_EXIST.getCode(), 
    				ResponseCodeI18n.DEPARTMENT_IS_EXIST.getMessage(), department.getLanType());
    	}
    	
    	Company checkCompany = companyService.findByPrimayKey(department.getCompanyId());
    	if(checkCompany == null) {
    		return ApiResultI18n.failure(ResponseCodeI18n.COMPANY_IS_NOT_EXIST.getCode(), 
    				ResponseCodeI18n.COMPANY_IS_NOT_EXIST.getMessage(), department.getLanType());
    	}
    	Group checkGroup = groupService.findByPrimayKey(checkCompany.getGroupId());
    	if(checkGroup == null) {
    		return ApiResultI18n.failure(ResponseCodeI18n.GROUP_IS_NOT_EXIST.getCode(), 
    				ResponseCodeI18n.GROUP_IS_NOT_EXIST.getMessage(), department.getLanType());
    	}
    	
    	department.setGroupName(checkGroup.getGroupName());
    	department.setCompanyName(checkCompany.getCompanyName());
    	department.setCreateTime(new Date());
    	department.setStatus(CommonConstant.INT_ONE);
    	departmentService.insertData(department);
    	
    	return ApiResultI18n.success(department.getLanType());
    }

    /**
     * * 修改信息
     * @return
     */
    @AuthRuleAnnotation("admin/system/department/updateData")
    @PostMapping("/admin/system/department/updateData")
    public ApiResultI18n updateData(@RequestBody Department department) {
    	Department check = departmentService.findByName(department.getDepartmentName());
    	if(check != null) {
    		if(check.getDepartmentId().intValue() != department.getDepartmentId().intValue()) {
				return ApiResultI18n.failure(ResponseCodeI18n.DEPARTMENT_IS_EXIST.getCode(), 
						ResponseCodeI18n.DEPARTMENT_IS_EXIST.getMessage(), department.getLanType());
			}
    	}
    	
    	department.setUpdateTime(new Date());
    	departmentService.updateData(department);

    	return ApiResultI18n.success(department.getLanType());
    }
    
    /**
     * * 删除信息
     * @return
     */
    @AuthRuleAnnotation("admin/system/department/deleteData")
    @PostMapping("/admin/system/department/deleteData")
    public ApiResultI18n deleteById(@RequestBody String params) {
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	
    	String lanType = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.LAN_TYPE))){
        	lanType = jsonObj.getString(RequestParamConstant.LAN_TYPE);
        }
        
    	Integer departmentId = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.DEPARTMENT_ID))){
        	departmentId = jsonObj.getInt(RequestParamConstant.DEPARTMENT_ID);
        }else {
        	return ApiResultI18n.failure(ResponseCodeI18n.PARAM_ERROR.getCode(), 
        			ResponseCodeI18n.PARAM_ERROR.getMessage(),lanType);
        }
        
        departmentService.deleteByPrimayKey(departmentId);

        return ApiResultI18n.success(lanType);
    }

}
