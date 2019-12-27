package com.saas.api.admin.service.crm.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.saas.api.admin.service.crm.CardProjectService;
import com.saas.api.common.dao.crm.CardProjectDao;
import com.saas.api.common.entity.crm.CardProject;

@Service
public class CardProjectServiceImple implements CardProjectService {
	
	@Resource
    private CardProjectDao cardProjectDao;
	
	@Override
	public void addData(CardProject record) {
		record.setCreateTime(new Date());
		cardProjectDao.insertSelective(record);
	}

	@Override
	public void deleteData(Long id) {
		cardProjectDao.deleteByPrimaryKey(id);
	}

	@Override
	public void deleteByCardId(Long cardId) {
		cardProjectDao.deleteByByCardId(cardId);
	}

	@Override
	public void modifyData(CardProject record) {
		record.setUpdateTime(new Date());
		cardProjectDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public CardProject selectByPrimaryKey(Long id) {
		return cardProjectDao.selectByPrimaryKey(id);
	}

	@Override
	public List<CardProject> selectByCardId(Long cardId) {
		return cardProjectDao.selectByCardId(cardId);
	}

}
