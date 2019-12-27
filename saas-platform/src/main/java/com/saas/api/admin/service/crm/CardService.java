package com.saas.api.admin.service.crm;

import java.util.List;
import java.util.Map;

import com.saas.api.admin.res.select.crm.CardSelect;
import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.crm.Card;

import net.sf.json.JSONObject;

public interface CardService {

	Map<String, Object> getListData(String cardName, Integer categoryId, Long adminId, String lanType, Page page);

	void addData(JSONObject jsonObj , Long adminId, String lanType);

	void deleteData(Long id);

	void modifyData(JSONObject jsonObj , Long adminId, String lanType);
	
	Card selectByPrimaryKey(Long id);
	
	Card selectByName(Long adminId, String cardName, String lanType);
	
	List<Card> findByObject(Card record);
	
	List<CardSelect> findSelectObject(Long adminId, String lanType);
	
	
}
