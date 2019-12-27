package com.saas.api.admin.service.sys;


import java.util.List;
import java.util.Map;

import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.sys.Group;

public interface GroupService {

	Map<String, Object> getListData(Integer groupId, String lanType, Page page);
	
	List<Group> findByObject(Group record);

	Group findByPrimayKey(Integer groupId);
	
	Group findByName(String groupName);
	
	int insertData(Group record);
	
	int updateData(Group record);
	
	int deleteByPrimayKey(Integer groupId);
	
	
	
}
