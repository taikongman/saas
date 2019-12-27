package com.saas.api.admin.service.crm;


import com.saas.api.common.entity.crm.*;
import com.saas.api.common.entity.sys.auth.User;

import net.sf.json.JSONObject;
import com.saas.api.common.dto.Page;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface BusinessService {

	List<User> userList(User record);
	
	Map<String, Object> inventoryWarn(Long adminId, String lanType, String title, String code, Integer warnType, Page page);

	void settle(JSONObject params, Long adminId, String lanType);

	void suspend(Long adminId, Long orderId);

	void batchSettle(JSONObject params, Long adminId, String lanType);
	
	void deleteRecharge(Long accountId, Long adminId, String lanType);

	void recharge(JSONObject params, Long adminId, String lanType) throws ParseException;

	void delay(JSONObject params, Long adminId, String lanType) throws ParseException;

	Map<String, Object> rechargeRecordList(Long adminId, Long customerId, Integer accountType, Page page);

	String ocr(JSONObject params, String lanType);

	List<SysData> sysData(Integer dataType);

	Map<String, Object> workData(Map<String, Object> params);

	void addFeedback(JSONObject params, Long adminId, String lanType);
}
