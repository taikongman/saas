package com.saas.api.admin.service.crm;

import java.util.List;
import java.util.Map;

import com.saas.api.common.entity.crm.PurchaseRecord;

import net.sf.json.JSONObject;

public interface PurchaseRecordService {
	
	PurchaseRecord findByPrimayKey(Long id);
	
	void insertDataByJson(JSONObject params, Long adminId, String lanType);
	
	void updateDataByJson(JSONObject params, Long adminId, String lanType);
	
	int insertData(PurchaseRecord record);
	
	int updateData(PurchaseRecord record);
	
	int deleteByPrimayKey(Long id);
	
	void deleteByStatus(Long id, String lanType);

	Map<String, Object> getDataList(Map<String, Object> params, Long adminId);
	
	List<PurchaseRecord> findByObject(PurchaseRecord record);
	
	List<PurchaseRecord> selectByPurchaseCode(String purchaseCode);
	
	int countByCommodityId(Long commodityId);

}
