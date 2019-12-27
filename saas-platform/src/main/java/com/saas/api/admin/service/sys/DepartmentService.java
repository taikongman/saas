package com.saas.api.admin.service.sys;


import java.util.List;
import java.util.Map;
import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.sys.Department;

public interface DepartmentService {

	Map<String, Object> getListData(Integer groupId, Integer companyId, Integer departmentId, String lanType, Page page);
	
	List<Department> findByObject(Department record);

	Department findByPrimayKey(Integer departmentId);
	
	Department findByName(String departmentName);
	
	int insertData(Department record);
	
	int updateData(Department record);
	
	int deleteByPrimayKey(Integer departmentId);
	

}
