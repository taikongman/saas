package com.saas.api.admin.service.crm;

import java.util.List;
import java.util.Map;

import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.crm.OrderCommodity;

import net.sf.json.JSONObject;


public interface CommodityRecordService {

	OrderCommodity findByPrimayKey(Long id);
	
	int insertData(OrderCommodity record);
	
	int updateData(OrderCommodity record);
	
	int deleteByPrimayKey(Long id);
	
	Map<String, Object> getListData(Integer commodityId, 
			Integer supplierId, Long operatorId, String carNo, 
			Integer operateType, Long adminId, String lanType, Page page);
	
	List<OrderCommodity> findByObject(OrderCommodity record);
	
	void addDataByJson(JSONObject params, Long adminId, String lanType);

	Map<String, Object> getListDataByMap(Map<?,?> params);
	
	
}
