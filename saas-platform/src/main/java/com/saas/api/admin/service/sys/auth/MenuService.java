package com.saas.api.admin.service.sys.auth;


import java.util.List;
import java.util.Map;

import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.sys.auth.Menu;

public interface MenuService {

	Map<String, Object> getListData(Integer menuId, Integer pid, String lanType, Page page);
	
	List<Menu> findByObject(Menu record);
	
	List<Menu> listDataByIdIn(List<Integer> menuIdList);

	Menu findByPrimayKey(Integer menuId);
	
	Menu findByName(String menuTitle);
	
	int insertData(Menu record);
	
	int updateData(Menu record);
	
	int deleteByPrimayKey(Integer menuId);

}
