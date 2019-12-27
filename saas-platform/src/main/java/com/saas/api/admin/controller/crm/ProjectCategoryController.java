package com.saas.api.admin.controller.crm;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.saas.api.admin.annotation.AuthRuleAnnotation;
import com.saas.api.admin.res.select.crm.ProjectCategorySelect;
import com.saas.api.admin.service.crm.ProjectCategoryService;
import com.saas.api.common.constant.CommonConstant;
import com.saas.api.common.constant.RequestParamConstant;
import com.saas.api.common.constant.ResponseCodeI18n;
import com.saas.api.common.entity.crm.ProjectCategory;
import com.saas.api.common.util.ApiResultI18n;
import com.saas.api.common.util.ParamUtil;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

/**
 * @Description: 发票相关接口
 * @Author: 徐未
 * @Date: 2019/12/10
 */
@RestController
@Slf4j
public class ProjectCategoryController {
	
	@Autowired
    private ProjectCategoryService projectCategoryService;
	
	
	
	/**
     * * 查询发票列表
     * @return @AuthRuleAnnotation("/admin/api/crm/commodity/category/getSelectList")
     */
    @PostMapping(value = "/admin/api/crm/project/category/queryList")
    public ApiResultI18n queryList(@RequestBody String params,
            HttpServletRequest request) {
    	log.debug(params);
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	
    	String lanType = request.getHeader("X-LanType");
    	String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        
        Integer categoryId = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.CATEGORY_ID))){
        	categoryId = jsonObj.getInt(RequestParamConstant.CATEGORY_ID);
        }
        
        Map<String, Object> result = projectCategoryService.getListData(categoryId, 
        		adminId, lanType, ParamUtil.initPage(jsonObj));
        
    	return ApiResultI18n.success(result, lanType);
    }
	
	/**
     * * 查询下拉发票列表
     * @return @AuthRuleAnnotation("/admin/api/crm/commodity/category/querySelectList")
     */
    @PostMapping(value = "/admin/api/crm/project/category/querySelectList")
    public ApiResultI18n querySelectList(@RequestBody String params,
            HttpServletRequest request) {
    	log.debug(params);
    	//JSONObject jsonObj = JSONObject.fromObject(params);
    	String lanType = request.getHeader("X-LanType");
    	String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        
        ProjectCategory record = new ProjectCategory();
        record.setLanType(lanType);
        record.setAdminId(adminId);
        
        List<ProjectCategorySelect> result = projectCategoryService.findSelectObject(record);
        
    	return ApiResultI18n.success(result, lanType);
    }
    
    /**
     * * 添加数据
     * @return
     */
    @AuthRuleAnnotation("admin/api/crm/project/category/addData")
    @PostMapping("/admin/api/crm/project/category/addData")
    public ApiResultI18n insertData(@RequestBody ProjectCategory record,
            HttpServletRequest request){
    	String lanType = request.getHeader("X-LanType");
    	String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        
    	
        ProjectCategory check = projectCategoryService.findByName(adminId, record.getCategoryName(), lanType);
    	if(check != null) {
    		return ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_PROJECT_CATEGORY_IS_EXIST.getCode(), 
    				ResponseCodeI18n.MODULE_CRM_PROJECT_CATEGORY_IS_EXIST.getMessage(), lanType);
    	}
    	
    	if(record.getParentId() != null && record.getParentId().intValue() > CommonConstant.INDEX_ZERO.intValue()) {
    		ProjectCategory parentCheck = projectCategoryService.findByPrimayKey(record.getParentId());
    		if(parentCheck == null) {
        		return ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_PROJECT_PARENT_CATEGORY_IS_NOT_EXIST.getCode(), 
        				ResponseCodeI18n.MODULE_CRM_PROJECT_PARENT_CATEGORY_IS_NOT_EXIST.getMessage(), lanType);
        	}
    	}
    	if(record.getParentId() == null) {
    		record.setParentId(CommonConstant.INDEX_ZERO);
    	}
    	
    	record.setAdminId(adminId);
    	record.setLanType(lanType);
    	projectCategoryService.insertData(record);
    	
    	return ApiResultI18n.success(lanType);
    }
    
    /**
     * * 更新数据
     * @return
     */
    @AuthRuleAnnotation("admin/api/crm/project/category/updateData")
    @PostMapping("/admin/api/crm/project/category/updateData")
    public ApiResultI18n updateData(@RequestBody ProjectCategory record,
            HttpServletRequest request) {
    	String lanType = request.getHeader("X-LanType");
    	String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);

        ProjectCategory check = projectCategoryService.findByPrimayKey(record.getCategoryId());
        if(check == null) {
        	return ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_PROJECT_CATEGORY_IS_NOT_EXIST.getCode(), 
    				ResponseCodeI18n.MODULE_CRM_PROJECT_CATEGORY_IS_NOT_EXIST.getMessage(), lanType);
        }
        ProjectCategory checkName = projectCategoryService.findByName(adminId, record.getCategoryName(), lanType);
    	if(checkName != null) {
    		if(!checkName.getCategoryId().equals(record.getCategoryId())) {
    			return ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_PROJECT_CATEGORY_IS_EXIST.getCode(), 
        				ResponseCodeI18n.MODULE_CRM_PROJECT_CATEGORY_IS_EXIST.getMessage(), lanType);
    		}
    	}
    	if(record.getParentId() != null && record.getParentId().intValue() > CommonConstant.INDEX_ZERO.intValue()) {
    		ProjectCategory parentCheck = projectCategoryService.findByPrimayKey(record.getParentId());
    		if(parentCheck == null) {
        		return ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_PROJECT_PARENT_CATEGORY_IS_NOT_EXIST.getCode(), 
        				ResponseCodeI18n.MODULE_CRM_PROJECT_PARENT_CATEGORY_IS_NOT_EXIST.getMessage(), lanType);
        	}
    	}
    	
    	record.setLanType(lanType);
    	record.setAdminId(adminId);
    	projectCategoryService.updateData(record);
    	return ApiResultI18n.success(lanType);
    }
    
    /**
     * * 删除数据
     * @return
     */
    @AuthRuleAnnotation("admin/api/crm/project/category/deleteData")
    @PostMapping("/admin/api/crm/project/category/deleteData")
    public ApiResultI18n deleteData(@RequestBody String params,
            HttpServletRequest request){
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	
    	String lanType = request.getHeader("X-LanType");
    	
    	Integer categoryId = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.CATEGORY_ID))){
        	categoryId = jsonObj.getInt(RequestParamConstant.CATEGORY_ID);
        }else {
        	return ApiResultI18n.failure(ResponseCodeI18n.PARAM_ERROR.getCode(), 
        			ResponseCodeI18n.PARAM_ERROR.getMessage(),lanType);
        }
        
        projectCategoryService.deleteByPrimayKey(categoryId);
        
    	return ApiResultI18n.success(lanType);
    }
    
}
