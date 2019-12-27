package com.saas.api.admin.service.crm;

import java.util.List;
import java.util.Map;

import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.crm.WarehouseLocation;

public interface WarehouseLocationService {
	
	 WarehouseLocation findByPrimayKey(Long id);
	
	 WarehouseLocation findByName(Integer warehouseId, String locationName);
	
	int insertData(WarehouseLocation record);
	
	int updateData(WarehouseLocation record);
	
	int deleteByPrimayKey(Long id);
	
	int deleteBySupplierId(Integer warehouseId);
	
	Map<String, Object> getListData(Integer warehouseId, String locationCode, String locationName, Page page);
	
	List<WarehouseLocation> findByWarehouseId(Integer warehouseId);
	
	List<WarehouseLocation> findByObject(WarehouseLocation record);
	
	
}
