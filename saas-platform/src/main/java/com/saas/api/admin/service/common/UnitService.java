package com.saas.api.admin.service.common;

import java.util.List;
import java.util.Map;

import com.saas.api.admin.res.select.common.UnitSelect;
import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.common.Unit;

public interface UnitService {
	
	Unit findByPrimayKey(Integer id);
	
	Unit findByName(String unitName);
	
	int insertData(Unit record);
	
	int updateData(Unit record);
	
	int deleteByPrimayKey(Integer id);
	
	Map<String, Object> getListData(Integer id, String lanType, Page page);
	
	List<Unit> findByObject(Unit record);
	
	List<UnitSelect> findSelectObject(Unit record);
	
}
