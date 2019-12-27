package com.saas.api.admin.service.crm;

import java.util.List;
import java.util.Map;

import com.saas.api.admin.res.select.crm.ProjectSelect;
import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.crm.Project;

import net.sf.json.JSONObject;

public interface ProjectService {
	
	Project findByPrimayKey(Long id);
	
	void addProject(JSONObject params, Long adminId, String lanType);

	void delProject(Long adminId, Long id);

	void modifyProject(JSONObject params, Long adminId, String lanType);
	
	Map<String, Object> projectList(Long adminId, String lanType, String projectName, String projectCode, 
			Integer projectTypeId, Page page);

	List<Project> defaultProjectList(Long adminId, Integer projectTypeId, String lanType);
	
	List<ProjectSelect> findSelectObject(Integer projectTypeId, Long adminId, String lanType);
	
	Map<String, Object> projectList4OrCond(Long adminId, String lanType, Integer projectTypeId, String searchCond, Page page);
}
