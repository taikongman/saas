package com.saas.api.admin.service.common;

import java.util.List;
import java.util.Map;

import com.saas.api.admin.res.select.common.MaintainTypeSelect;
import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.common.MaintainType;

public interface MaintainTypeService {
	
	MaintainType findByPrimayKey(Integer id);
	
	MaintainType findByName(String name);
	
	int insertData(MaintainType record);
	
	int updateData(MaintainType record);
	
	int insertDataByParams(String name, String remark, String lanType);
	
	int updateDataByParams(Integer id, String name, String remark, String lanType);
	
	int deleteByPrimayKey(Integer id);
	
	Map<String, Object> getListData(Integer id, String lanType, Page page);
	
	List<MaintainType> findByObject(MaintainType record);
	
	List<MaintainTypeSelect> findSelectObject(String lanType);
	
}
