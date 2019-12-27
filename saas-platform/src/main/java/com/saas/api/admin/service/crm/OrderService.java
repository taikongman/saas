package com.saas.api.admin.service.crm;

import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface OrderService {
	
	Map<String, Object> orderList(Map<String, Object> params);

	Map<String, Object> orderList4OrCond(Map<String, Object> params);

	Map<String, Object> orderDetail(Long orderId, Long adminId);
	
	void addOrder(JSONObject params, Long adminId, String lanType);
	
	void updateData(JSONObject params, Long adminId, String lanType);

	void dispachOrder(Long doAdminId, Long orderId);
	
	void operateCommodity(JSONArray commodityList, Long orderId, Long adminId, String lanType);

	void finishWork(Long orderId);
	
	

}
