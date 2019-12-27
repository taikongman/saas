package com.saas.api.admin.service.common;

import java.util.List;
import java.util.Map;

import com.saas.api.admin.res.select.common.PricingModeSelect;
import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.common.PricingMode;

public interface PricingModeService {
	
	PricingMode findByPrimayKey(Integer id);
	
	PricingMode findByName(String name);
	
	int insertData(PricingMode record);
	
	int updateData(PricingMode record);
	
	int insertDataByParams(String name, Integer isTime, String remark, String lanType);
	
	int updateDataByParams(Integer id, String name, Integer isTime, String remark, String lanType);
	
	int deleteByPrimayKey(Integer id);
	
	Map<String, Object> getListData(Integer id, String lanType, Page page);
	
	List<PricingMode> findByObject(PricingMode record);
	
	List<PricingModeSelect> findSelectObject(String lanType);
	
}
