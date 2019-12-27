package com.saas.api.admin.service.common;

import java.util.List;
import java.util.Map;

import com.saas.api.admin.res.select.common.SettleModeSelect;
import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.common.SettleMode;

public interface SettleModeService {
	
	SettleMode findByPrimayKey(Integer id);
	
	SettleMode findByName(String name);
	
	int insertData(SettleMode record);
	
	int updateData(SettleMode record);
	
	int insertDataByParams(String name, String remark, String lanType);
	
	int updateDataByParams(Integer id, String name, String remark, String lanType);
	
	int deleteByPrimayKey(Integer id);
	
	Map<String, Object> getListData(Integer id, String lanType, Page page);
	
	List<SettleMode> findByObject(SettleMode record);
	
	List<SettleModeSelect> findSelectObject(String lanType);
	
}
