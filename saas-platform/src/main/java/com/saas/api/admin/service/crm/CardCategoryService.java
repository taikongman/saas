package com.saas.api.admin.service.crm;

import java.util.List;
import java.util.Map;

import com.saas.api.admin.res.select.crm.CardCategorySelect;
import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.crm.CardCategory;


public interface CardCategoryService {

	CardCategory findByPrimayKey(Integer categoryId);
	
	CardCategory findByName(Long adminId, String categoryName, String lanType);
	
	int insertData(CardCategory record);
	
	int updateData(CardCategory record);
	
	int deleteByPrimayKey(Integer categoryId);
	
	Map<String, Object> getListData(Integer categoryId, Long adminId, String lanType, Page page);
	
	List<CardCategory> findByObject(CardCategory record);
	
	List<CardCategorySelect> findSelectObject(CardCategory record);
	
}
