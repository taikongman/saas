package com.saas.api.admin.service.common;

import java.util.List;
import java.util.Map;

import com.saas.api.admin.res.select.common.ProjectTypeSelect;
import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.common.ProjectType;

public interface ProjectTypeService {
	
	ProjectType findByPrimayKey(Integer id);
	
	ProjectType findByName(String name);
	
	int insertData(ProjectType record);
	
	int updateData(ProjectType record);
	
	int insertDataByParams(String name, String remark, String lanType);
	
	int updateDataByParams(Integer id, String name, String remark, String lanType);
	
	int deleteByPrimayKey(Integer id);
	
	Map<String, Object> getListData(Integer id, String lanType, Page page);
	
	List<ProjectType> findByObject(ProjectType record);
	
	List<ProjectTypeSelect> findSelectObject(String lanType);
	
}
