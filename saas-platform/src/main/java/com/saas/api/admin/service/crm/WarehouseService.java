package com.saas.api.admin.service.crm;

import java.util.List;
import java.util.Map;

import com.saas.api.admin.res.crm.WarehouseResponse;
import com.saas.api.admin.res.select.crm.WarehouseSelect;
import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.crm.Warehouse;

import net.sf.json.JSONArray;

public interface WarehouseService {
	
	Warehouse findByPrimayKey(Integer warehouseId);
	
	Warehouse findByName(Long adminId, String warehouseName, String lanType);
	
	int insertData(Warehouse record, JSONArray locationList);
	
	int updateData(Warehouse record, JSONArray locationList);
	
	int deleteByPrimayKey(Integer warehouseId);
	
	Map<String, Object> getListData(Integer warehouseId, String warehouseCode, String warehouseName, 
			Long adminId, String lanType, Page page);
	
	List<WarehouseResponse> findByObject(Warehouse record);
	
	List<WarehouseSelect> findSelectObject(Long adminId, Warehouse record);
	
}
