package com.saas.api.admin.service.crm.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.saas.api.admin.service.crm.CardCommodityService;
import com.saas.api.common.dao.crm.CardCommodityDao;
import com.saas.api.common.entity.crm.CardCommodity;

@Service
public class CardCommodityServiceImpl implements CardCommodityService{

	@Resource
    private CardCommodityDao cardCommodityDao;
	
	@Override
	public void addData(CardCommodity record) {
		record.setCreateTime(new Date());
		cardCommodityDao.insertSelective(record);
	}

	@Override
	public void deleteData(Long id) {
		cardCommodityDao.deleteByPrimaryKey(id);
	}

	@Override
	public void deleteByCardId(Long cardId) {
		cardCommodityDao.deleteByCardId(cardId);
	}

	@Override
	public void modifyData(CardCommodity record) {
		record.setUpdateTime(new Date());
		cardCommodityDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public CardCommodity selectByPrimaryKey(Long id) {
		return cardCommodityDao.selectByPrimaryKey(id);
	}

	@Override
	public List<CardCommodity> selectByCardId(Long cardId) {
		return cardCommodityDao.selectByCardId(cardId);
	}

}
