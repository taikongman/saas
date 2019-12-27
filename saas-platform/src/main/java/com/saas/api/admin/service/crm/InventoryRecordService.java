package com.saas.api.admin.service.crm;

import java.util.List;
import java.util.Map;

import com.saas.api.admin.res.select.crm.InventoryRecordSelect;
import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.crm.InventoryRecord;

import net.sf.json.JSONObject;

public interface InventoryRecordService {
	
	InventoryRecord findByPrimayKey(Long id);
	
	void insertDataByJson(JSONObject params, Long adminId, String lanType);
	
	void updateDataByJson(JSONObject params, Long adminId, String lanType);
	
	int insertData(InventoryRecord record);
	
	int updateData(InventoryRecord record);
	
	int updateQuantity(Integer remainQuantity, Long id);
	
	void updateLocation(Long id, Long locationId, String lanType);
	
	int deleteByPrimayKey(Long id);
	
	void deleteByStatus(Long id, String lanType);

	Map<String, Object> getDataList(Map<String, Object> params, Long adminId);
	
	List<InventoryRecordSelect> findSelectObject(Long adminId, String lanType);
	
	List<InventoryRecord> findByObject(InventoryRecord record);
	
	List<InventoryRecord> selectByPurchaseId(Long purchaseId);
	
	List<InventoryRecord> selectByPurchaseCode(String purchaseCode);
	
	int countByCommodityId(Long commodityId);
	
	List<InventoryRecord> defaultMaterialList(Long adminId, String lanType);
	
	Map<String, Object> commodityList4OrCond(Long adminId, String lanType, String searchCond, Page page);

}
