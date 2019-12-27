package com.saas.api.admin.service.crm;

import java.util.List;
import java.util.Map;

import com.saas.api.admin.res.select.crm.ProjectCategorySelect;
import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.crm.ProjectCategory;


public interface ProjectCategoryService {

	ProjectCategory findByPrimayKey(Integer categoryId);
	
	ProjectCategory findByName(Long adminId, String categoryName, String lanType);
	
	int insertData(ProjectCategory record);
	
	int updateData(ProjectCategory record);
	
	int deleteByPrimayKey(Integer categoryId);
	
	Map<String, Object> getListData(Integer categoryId, Long adminId, String lanType, Page page);
	
	List<ProjectCategory> findByObject(ProjectCategory record);
	
	List<ProjectCategorySelect> findSelectObject(ProjectCategory record);
	
}
