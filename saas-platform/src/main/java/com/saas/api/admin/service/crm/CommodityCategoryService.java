package com.saas.api.admin.service.crm;

import java.util.List;
import java.util.Map;

import com.saas.api.admin.res.select.crm.CommodityCategorySelect;
import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.crm.CommodityCategory;


public interface CommodityCategoryService {

	CommodityCategory findByPrimayKey(Integer categoryId);
	
	CommodityCategory findByName(Long adminId, String categoryName, String lanType);
	
	int insertData(CommodityCategory record);
	
	int updateData(CommodityCategory record);
	
	int deleteByPrimayKey(Integer categoryId);
	
	Map<String, Object> getListData(Integer categoryId, Long adminId, String lanType, Page page);
	
	List<CommodityCategory> findByObject(CommodityCategory record);
	
	List<CommodityCategorySelect> findSelectObject(CommodityCategory record);
	
}
