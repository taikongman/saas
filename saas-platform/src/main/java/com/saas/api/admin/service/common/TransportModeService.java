package com.saas.api.admin.service.common;

import java.util.List;
import java.util.Map;

import com.saas.api.admin.res.select.common.TransportModeSelect;
import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.common.TransportMode;

public interface TransportModeService {
	
	TransportMode findByPrimayKey(Integer id);
	
	TransportMode findByName(String transportName);
	
	int insertData(TransportMode record);
	
	int updateData(TransportMode record);
	
	int deleteByPrimayKey(Integer id);
	
	Map<String, Object> getListData(Integer id, String lanType, Page page);
	
	List<TransportMode> findByObject(TransportMode record);
	
	List<TransportModeSelect> findSelectObject(TransportMode record);
	
}
