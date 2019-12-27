package com.saas.api.admin.service.crm;

import java.util.List;

import com.saas.api.common.entity.crm.CardCommodity;


public interface CardCommodityService {

	void addData(CardCommodity record);

	void deleteData(Long id);
	
	void deleteByCardId(Long cardId);

	void modifyData(CardCommodity record);
	
	CardCommodity selectByPrimaryKey(Long id);
	
	List<CardCommodity> selectByCardId(Long cardId);
	
	
}
