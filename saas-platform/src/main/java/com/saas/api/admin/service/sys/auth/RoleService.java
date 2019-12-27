package com.saas.api.admin.service.sys.auth;


import java.util.List;
import java.util.Map;

import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.sys.auth.Role;

public interface RoleService {
    
    Map<String, Object> getListData(Integer groupId, Integer companyId, Long roleId, String lanType, Page page);
	
	List<Role> findByObject(Role record);

	Role findByPrimayKey(Long roleId);
	
	Role findByName(String roleName);
	
	int insertData(Role record);
	
	int updateData(Role record);
	
	int deleteByPrimayKey(Long roleId);

}
