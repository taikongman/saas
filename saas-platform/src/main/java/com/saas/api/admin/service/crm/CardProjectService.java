package com.saas.api.admin.service.crm;

import java.util.List;

import com.saas.api.common.entity.crm.CardProject;


public interface CardProjectService {

	void addData(CardProject record);

	void deleteData(Long id);
	
	void deleteByCardId(Long cardId);

	void modifyData(CardProject record);
	
	CardProject selectByPrimaryKey(Long id);
	
	List<CardProject> selectByCardId(Long cardId);
	
	
}
