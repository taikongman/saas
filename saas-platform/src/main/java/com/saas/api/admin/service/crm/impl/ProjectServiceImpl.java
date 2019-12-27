package com.saas.api.admin.service.crm.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.saas.api.admin.res.select.crm.ProjectSelect;
import com.saas.api.admin.service.crm.ProjectService;
import com.saas.api.common.constant.CommonConstant;
import com.saas.api.common.constant.DbConstant;
import com.saas.api.common.constant.RequestParamConstant;
import com.saas.api.common.constant.ResponseCodeI18n;
import com.saas.api.common.dao.common.PricingModeDao;
import com.saas.api.common.dao.common.ProjectTypeDao;
import com.saas.api.common.dao.common.UnitDao;
import com.saas.api.common.dao.crm.ProjectCategoryDao;
import com.saas.api.common.dao.crm.ProjectDao;
import com.saas.api.common.dao.sys.auth.UserDao;
import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.common.PricingMode;
import com.saas.api.common.entity.common.ProjectType;
import com.saas.api.common.entity.common.Unit;
import com.saas.api.common.entity.crm.Project;
import com.saas.api.common.entity.crm.ProjectCategory;
import com.saas.api.common.entity.sys.auth.User;
import com.saas.api.common.exception.SaasException;
import com.saas.api.common.util.ApiResultI18n;

import net.sf.json.JSONObject;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Resource
    private UserDao userDao;
	
	@Resource
    private ProjectDao projectDao;
	
	@Resource
    private UnitDao unitDao;
	
	@Resource
    private ProjectTypeDao projectTypeDao;
	
	@Resource
    private ProjectCategoryDao projectCategoryDao;
	
	@Resource
    private PricingModeDao pricingModeDao;
	
	
	@Override
	public Project findByPrimayKey(Long id) {
		return projectDao.selectByPrimaryKey(id);
	}

	@Override
	public void addProject(JSONObject params, Long adminId, String lanType) {
		User user = userDao.findById(adminId);
    	int existCount = projectDao.countByCode(user.getGroupId(), user.getCompanyId(), 
        		user.getDepartmentId(), adminId.intValue(), 
    			lanType, params.getString(RequestParamConstant.PROJECT_CODE));
        if (existCount > CommonConstant.INT_ZERO) {
        	ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_PROJECT_IS_EXIST.getCode(), 
    				ResponseCodeI18n.MODULE_CRM_PROJECT_IS_EXIST.getMessage(), lanType);
            throw new SaasException(exception18n.getCode(), exception18n.getMessage());
        }
        
        Project record = (Project) JSONObject.toBean(params, Project.class);
        
        ProjectType projectType = projectTypeDao.findById(record.getProjectTypeId());
		if(projectType != null) {
			record.setProjectTypeName(projectType.getName());
		}else {
			ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_PROJECT_TYPE_IS_NOT_EXIST.getCode(), 
    				ResponseCodeI18n.MODULE_CRM_PROJECT_TYPE_IS_NOT_EXIST.getMessage(), lanType);
            throw new SaasException(exception18n.getCode(), exception18n.getMessage());
		}
		ProjectCategory projectCategory = projectCategoryDao.selectByPrimaryKey(record.getCategoryId());
		if(projectCategory != null) {
			record.setCategoryName(projectCategory.getCategoryName());
		}else {
			ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_PROJECT_CATEGORY_IS_NOT_EXIST.getCode(), 
    				ResponseCodeI18n.MODULE_CRM_PROJECT_CATEGORY_IS_NOT_EXIST.getMessage(), lanType);
            throw new SaasException(exception18n.getCode(), exception18n.getMessage());
		}
		PricingMode pricingMode = pricingModeDao.findById(record.getPriceMode());
		if(pricingMode != null) {
			record.setPriceName(pricingMode.getName());
		}else {
			ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_PROJECT_TYPE_IS_NOT_EXIST.getCode(), 
    				ResponseCodeI18n.MODULE_CRM_PROJECT_TYPE_IS_NOT_EXIST.getMessage(), lanType);
            throw new SaasException(exception18n.getCode(), exception18n.getMessage());
		}
        Unit unit = unitDao.findById(record.getUnitId());
		if(unit != null) {
			record.setUnitName(unit.getUnitName());
		}else {
			ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_UNIT_IS_NOT_EXIST.getCode(), 
    				ResponseCodeI18n.MODULE_CRM_UNIT_IS_NOT_EXIST.getMessage(), lanType);
            throw new SaasException(exception18n.getCode(), exception18n.getMessage());
		}
		
		
		record.setGroupId(user.getGroupId());
		record.setCompanyId(user.getCompanyId());
		record.setDepartmentId(user.getDepartmentId());
		record.setAdminId(user.getAdminId());
		record.setStatus(DbConstant.PROJECT_STATUS_NORMAL);
		record.setCreateTime(new Date());
		record.setVersion(DbConstant.INIT_VERSION);
		record.setLanType(lanType);
        projectDao.insert(record);
	}

	@Override
	public void delProject(Long adminId, Long id) {
		Project project = projectDao.selectByPrimaryKey(id);
        project.setStatus(DbConstant.PROJECT_STATUS_DEL);
        project.setUpdateTime(new Date());
        projectDao.updateByPrimaryKeySelective(project);
	}

	@Override
	public void modifyProject(JSONObject params, Long adminId, String lanType) {
		User user = userDao.findById(adminId);
    	int existCount = projectDao.countByCode(user.getGroupId(), user.getCompanyId(), 
        		user.getDepartmentId(), adminId.intValue(), 
    			lanType, params.getString(RequestParamConstant.PROJECT_CODE));
        if (existCount > CommonConstant.INT_ONE) {
        	ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_PROJECT_IS_EXIST.getCode(), 
    				ResponseCodeI18n.MODULE_CRM_PROJECT_IS_EXIST.getMessage(), lanType);
            throw new SaasException(exception18n.getCode(), exception18n.getMessage());
        } else {
            Project existCode = projectDao.selectByCode(user.getGroupId(), user.getCompanyId(), 
            		user.getDepartmentId(), adminId.intValue(), 
            		lanType, params.getString(RequestParamConstant.PROJECT_CODE));
            if (null != existCode && !existCode.getId().equals(params.getLong(RequestParamConstant.ID))) {
            	ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_PROJECT_IS_EXIST.getCode(), 
        				ResponseCodeI18n.MODULE_CRM_PROJECT_IS_EXIST.getMessage(), lanType);
                throw new SaasException(exception18n.getCode(), exception18n.getMessage());
            }
        }
        Project record = (Project) JSONObject.toBean(params, Project.class);
        
        ProjectType projectType = projectTypeDao.findById(record.getProjectTypeId());
		if(projectType != null) {
			record.setProjectTypeName(projectType.getName());
		}else {
			ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_PROJECT_TYPE_IS_NOT_EXIST.getCode(), 
    				ResponseCodeI18n.MODULE_CRM_PROJECT_TYPE_IS_NOT_EXIST.getMessage(), lanType);
            throw new SaasException(exception18n.getCode(), exception18n.getMessage());
		}
		ProjectCategory projectCategory = projectCategoryDao.selectByPrimaryKey(record.getCategoryId());
		if(projectCategory != null) {
			record.setCategoryName(projectCategory.getCategoryName());
		}else {
			ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_PROJECT_CATEGORY_IS_NOT_EXIST.getCode(), 
    				ResponseCodeI18n.MODULE_CRM_PROJECT_CATEGORY_IS_NOT_EXIST.getMessage(), lanType);
            throw new SaasException(exception18n.getCode(), exception18n.getMessage());
		}
		PricingMode pricingMode = pricingModeDao.findById(record.getPriceMode());
		if(pricingMode != null) {
			record.setPriceName(pricingMode.getName());
		}else {
			ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_PROJECT_TYPE_IS_NOT_EXIST.getCode(), 
    				ResponseCodeI18n.MODULE_CRM_PROJECT_TYPE_IS_NOT_EXIST.getMessage(), lanType);
            throw new SaasException(exception18n.getCode(), exception18n.getMessage());
		}
        Unit unit = unitDao.findById(record.getUnitId());
		if(unit != null) {
			record.setUnitName(unit.getUnitName());
		}else {
			ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_UNIT_IS_NOT_EXIST.getCode(), 
    				ResponseCodeI18n.MODULE_CRM_UNIT_IS_NOT_EXIST.getMessage(), lanType);
            throw new SaasException(exception18n.getCode(), exception18n.getMessage());
		}
		
		record.setUpdateTime(new Date());
        projectDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public Map<String, Object> projectList(Long adminId, String lanType, String projectName, String projectCode,
			Integer projectTypeId, Page page) {
		User user = userDao.findById(adminId);
    	List<Project> projectList = projectDao.projectList(user.getGroupId(), user.getCompanyId(), 
        		null, null, 
    			lanType, projectName, projectCode, projectTypeId, 
    			DbConstant.PROJECT_STATUS_NORMAL, page.getStartIndex(), page.getPageSize());
    	int total = projectDao.countProject(user.getGroupId(), user.getCompanyId(), 
    			null, null,
    			lanType, projectName, projectCode, projectTypeId, 
    			DbConstant.PROJECT_STATUS_NORMAL);
    	
        Map<String, Object> result = new HashMap<>();
        result.put("dataList", projectList);
        result.put("total", total);
        return result;
	}

	@Override
	public List<ProjectSelect> findSelectObject(Integer projectTypeId, Long adminId, String lanType) {
		User user = userDao.findById(adminId);
		Project record = new Project();
		record.setGroupId(user.getGroupId());
		record.setCompanyId(user.getCompanyId());
		record.setProjectTypeId(projectTypeId);
		record.setStatus(DbConstant.STATUS_ONE);
		record.setLanType(lanType);
		return projectDao.findSelectObject(record);
	}

	@Override
	public List<Project> defaultProjectList(Long adminId, Integer projectTypeId, String lanType) {
		User user = userDao.findById(adminId);
        List<Project> projectList = projectDao.defaultProjectList(user.getGroupId(), user.getCompanyId(), 
        		user.getDepartmentId(), adminId.intValue(), 
        		lanType, projectTypeId);
        return projectList;
	}
	
	@Override
    public Map<String, Object> projectList4OrCond(Long adminId, String lanType, Integer projectTypeId, String searchCond, Page page) {
    	User user = userDao.findById(adminId);
    	List<Project> projectList = projectDao.projectList4OrCond(user.getGroupId(), user.getCompanyId(), 
        		user.getDepartmentId(), adminId.intValue(),
    			lanType, searchCond, projectTypeId, page.getStartIndex(), page.getPageSize());
    	int total = projectDao.countProject4OrCond(user.getGroupId(), user.getCompanyId(), 
        		user.getDepartmentId(), adminId.intValue(),
    			lanType, searchCond, projectTypeId);
        
        Map<String, Object> result = new HashMap<>();
        result.put("dataList", projectList);
        result.put("total", total);
        return result;
    }
	
	
}
