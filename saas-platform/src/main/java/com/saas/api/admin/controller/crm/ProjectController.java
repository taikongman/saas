package com.saas.api.admin.controller.crm;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.saas.api.admin.annotation.AuthRuleAnnotation;
import com.saas.api.admin.res.select.crm.ProjectSelect;
import com.saas.api.admin.service.crm.ProjectService;
import com.saas.api.common.constant.RequestParamConstant;
import com.saas.api.common.constant.ResponseCodeI18n;
import com.saas.api.common.entity.crm.Project;
import com.saas.api.common.util.ApiResultI18n;
import com.saas.api.common.util.ParamUtil;

import net.sf.json.JSONObject;

/**
 * @Description: 项目相关接口
 * @Author: 徐未
 * @Date: 2019/12/17
 */
@RestController
public class ProjectController {

	@Autowired
    private ProjectService projectService;
	
	@RequestMapping(value = "/admin/api/crm/project/projectList", method = RequestMethod.POST)
    public ApiResultI18n projectList(@RequestBody String params,
            HttpServletRequest request) {
        JSONObject jsonObj = JSONObject.fromObject(params);
        String lanType = request.getHeader("X-LanType");
        
        String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        
        Integer projectTypeId = null;
        if (null != jsonObj.get(RequestParamConstant.PROJECT_TYPE_ID)){
        	projectTypeId = jsonObj.getInt(RequestParamConstant.PROJECT_TYPE_ID);
        }
        String projectCode = null;
        String projectName = null;
        if (null != jsonObj.get(RequestParamConstant.PROJECT_CODE)){
        	projectCode = jsonObj.getString(RequestParamConstant.PROJECT_CODE);
        }
        if (null != jsonObj.get(RequestParamConstant.PROJECT_NAME)){
        	projectName = jsonObj.getString(RequestParamConstant.PROJECT_NAME);
        }
        Map<String, Object> result = projectService.projectList(adminId, lanType, projectName, projectCode, 
        		projectTypeId, ParamUtil.initPage(jsonObj));
        
        return ApiResultI18n.success(result, lanType);
    }
	
	@RequestMapping(value = "/admin/api/crm/project/querySelectList", method = RequestMethod.POST)
    public ApiResultI18n querySelectList(@RequestBody String params,
            HttpServletRequest request) {
        JSONObject jsonObj = JSONObject.fromObject(params);
        String lanType = request.getHeader("X-LanType");
        String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        
        Integer projectTypeId = null;
        if (null != jsonObj.get(RequestParamConstant.PROJECT_TYPE_ID)){
        	projectTypeId = jsonObj.getInt(RequestParamConstant.PROJECT_TYPE_ID);
        }
        
        List<ProjectSelect> result = projectService.findSelectObject(projectTypeId, adminId, lanType);
        return ApiResultI18n.success(result, lanType);
    }
	
	@RequestMapping(value = "/admin/api/crm/project/detailData", method = RequestMethod.POST)
    public ApiResultI18n detailData(@RequestBody String params,
            HttpServletRequest request) {
        JSONObject jsonObj = JSONObject.fromObject(params);
        String lanType = request.getHeader("X-LanType");
        
        Long id = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.ID))){
        	id = jsonObj.getLong(RequestParamConstant.ID);
        }else {
        	return ApiResultI18n.failure(ResponseCodeI18n.PARAM_ERROR.getCode(), 
        			ResponseCodeI18n.PARAM_ERROR.getMessage(),lanType);
        }
        Project result = projectService.findByPrimayKey(id);
        
        return ApiResultI18n.success(result, lanType);
    }

    @AuthRuleAnnotation("admin/api/crm/project/addData")
    @PostMapping("/admin/api/crm/project/addData")
    public ApiResultI18n addProject(@RequestBody String params,
            HttpServletRequest request) {
        JSONObject jsonObj = JSONObject.fromObject(params);
        String lanType = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.LAN_TYPE))){
        	lanType = jsonObj.getString(RequestParamConstant.LAN_TYPE);
        }
        
        String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        projectService.addProject(jsonObj, adminId, lanType);
        return ApiResultI18n.success(lanType);
    }

    @AuthRuleAnnotation("admin/api/crm/project/deleteData")
    @PostMapping("/admin/api/crm/project/deleteData")
    public ApiResultI18n delProject(@RequestBody String params,
            HttpServletRequest request) {
        JSONObject jsonObj = JSONObject.fromObject(params);
        String lanType = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.LAN_TYPE))){
        	lanType = jsonObj.getString(RequestParamConstant.LAN_TYPE);
        }
        
        String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        Long id = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.ID))){
        	id = jsonObj.getLong(RequestParamConstant.ID);
        }else {
        	return ApiResultI18n.failure(ResponseCodeI18n.PARAM_ERROR.getCode(), 
        			ResponseCodeI18n.PARAM_ERROR.getMessage(),lanType);
        }
        projectService.delProject(adminId, id);
        return ApiResultI18n.success(lanType);
    }
    
    @AuthRuleAnnotation("admin/api/crm/project/updateData")
    @PostMapping("/admin/api/crm/project/updateData")
    public ApiResultI18n modifyProject(@RequestBody String params,
            HttpServletRequest request) {
        JSONObject jsonObj = JSONObject.fromObject(params);
        String lanType = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.LAN_TYPE))){
        	lanType = jsonObj.getString(RequestParamConstant.LAN_TYPE);
        }
        
        String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        
        projectService.modifyProject(jsonObj, adminId, lanType);
        return ApiResultI18n.success(lanType);
    }
	

    @RequestMapping(value = "/admin/api/crm/project/defaultProjectList", method = RequestMethod.POST)
    public ApiResultI18n defaultProjectList(@RequestBody String params,
            HttpServletRequest request) {
        JSONObject jsonObj = JSONObject.fromObject(params);
        String lanType = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.LAN_TYPE))){
        	lanType = jsonObj.getString(RequestParamConstant.LAN_TYPE);
        }
        
        String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        
        Integer projectTypeId = jsonObj.getInt(RequestParamConstant.PROJECT_TYPE_ID);
        List<Project> result = projectService.defaultProjectList(adminId, projectTypeId, lanType);
        return ApiResultI18n.success(result, lanType);
    }
	
    @RequestMapping(value = "/admin/api/crm/project/projectList4OrCond", method = RequestMethod.POST)
    public ApiResultI18n projectList4OrCond(@RequestBody String params,
            HttpServletRequest request) {
        JSONObject jsonObj = JSONObject.fromObject(params);
        String lanType = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.LAN_TYPE))){
        	lanType = jsonObj.getString(RequestParamConstant.LAN_TYPE);
        }
        
        String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        
        Integer projectTypeId = jsonObj.getInt(RequestParamConstant.PROJECT_TYPE_ID);
        String searchCond = jsonObj.getString(RequestParamConstant.SEARCH_COND);
        Map<String, Object> result = projectService.projectList4OrCond(adminId, lanType, projectTypeId, 
        		searchCond, ParamUtil.initPage(jsonObj));
        return ApiResultI18n.success(result, lanType);
    }
}
