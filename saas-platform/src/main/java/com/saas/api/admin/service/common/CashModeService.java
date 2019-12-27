package com.saas.api.admin.service.common;

import java.util.List;
import java.util.Map;

import com.saas.api.admin.res.select.common.CashModeSelect;
import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.common.CashMode;

public interface CashModeService {
	
	CashMode findByPrimayKey(Integer id);
	
	CashMode findByName(String name);
	
	int insertData(CashMode record);
	
	int updateData(CashMode record);
	
	int insertDataByParams(String name, String remark, String lanType);
	
	int updateDataByParams(Integer id, String name, String remark, String lanType);
	
	int deleteByPrimayKey(Integer id);
	
	Map<String, Object> getListData(Integer id, String lanType, Page page);
	
	List<CashMode> findByObject(CashMode record);
	
	List<CashModeSelect> findSelectObject(String lanType);
	
}
