package com.saas.api.admin.service.sys.auth;


import java.util.List;
import java.util.Map;

import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.sys.auth.User;

public interface UserService {
	
	Map<String, Object> getListData(Integer groupId, Integer companyId, Integer departmentId, Integer adminId, String lanType, Page page);
	
	Map<String, Object> queryListData(Integer groupId, Integer companyId, Integer departmentId, Integer adminId, String lanType, Page page);
	
	List<User> findByObject(User record);

	User findByPrimayKey(Long adminId);
	
	User findByName(String userName);
	
	int insertData(User record);
	
	int resetPassword(User record);
	
	int updateData(User record);
	
	int deleteByPrimayKey(Long adminId);

}
