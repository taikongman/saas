package com.saas.api.admin.service.crm.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.saas.api.admin.res.select.crm.CardCategorySelect;
import com.saas.api.admin.service.crm.CardCategoryService;
import com.saas.api.common.constant.CommonConstant;
import com.saas.api.common.constant.DbConstant;
import com.saas.api.common.dao.crm.CardCategoryDao;
import com.saas.api.common.dao.sys.auth.UserDao;
import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.crm.CardCategory;
import com.saas.api.common.entity.sys.auth.User;

@Service
public class CardCategoryServiceImpl implements CardCategoryService {

	@Resource
    private CardCategoryDao cardCategoryDao;
    
	@Resource
    private UserDao userDao;
    
	@Override
	public CardCategory findByPrimayKey(Integer categoryId) {		
		return cardCategoryDao.selectByPrimaryKey(categoryId);
	}

	@Override
	public CardCategory findByName(Long adminId, String categoryName, String lanType) {
		User user = userDao.findById(adminId);
		return cardCategoryDao.selectByName(user.getGroupId(), user.getCompanyId(), null, null, categoryName, lanType);
	}

	@Override
	public int insertData(CardCategory record) {
		User user = userDao.findById(record.getAdminId());
		record.setGroupId(user.getGroupId());
		record.setCompanyId(user.getCompanyId());
		record.setDepartmentId(user.getDepartmentId());
		record.setStatus(DbConstant.STATUS_ONE);
		record.setCreateTime(new Date());
		return cardCategoryDao.insert(record);
	}

	@Override
	public int updateData(CardCategory record) {
		record.setUpdateTime(new Date());
		return cardCategoryDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int deleteByPrimayKey(Integer categoryId) {
		return cardCategoryDao.deleteByPrimaryKey(categoryId);
	}

	@Override
	public Map<String, Object> getListData(Integer categoryId, Long adminId, String lanType, Page page) {
		User user = userDao.findById(adminId);
		List<CardCategory> resultList = cardCategoryDao.listData(user.getGroupId(), user.getCompanyId(), 
				null, null, null, 
				categoryId, lanType, page.getStartIndex(), page.getPageSize());
		Integer total = cardCategoryDao.countData(user.getGroupId(), user.getCompanyId(), 
				null, null, null, 
				categoryId, lanType);
		
		Map<String, Object> result = new HashMap<>();
        result.put("dataList", resultList);
        result.put("total", total);
        return result;
	}

	@Override
	public List<CardCategory> findByObject(CardCategory record) {
		return cardCategoryDao.findByObject(record);
	}

	@Override
	public List<CardCategorySelect> findSelectObject(CardCategory record) {
		User user = userDao.findById(record.getAdminId());
		record.setGroupId(user.getGroupId());
		record.setCompanyId(user.getCompanyId());
		record.setParentId(CommonConstant.INDEX_ZERO);
		record.setStatus(DbConstant.STATUS_ONE);
		List<CardCategorySelect> resultList = cardCategoryDao.findSelectObject(record);
		for(CardCategorySelect forTemp : resultList) {
			CardCategory queryFist = new CardCategory();
			queryFist.setParentId(forTemp.getCategoryId());
			queryFist.setStatus(DbConstant.STATUS_ONE);
			
			List<CardCategorySelect> childList = cardCategoryDao.findSelectObject(queryFist);
			for(CardCategorySelect forTwoTemp : childList) {
				CardCategory queryTwoFist = new CardCategory();
				queryTwoFist.setParentId(forTwoTemp.getCategoryId());
				queryTwoFist.setStatus(DbConstant.STATUS_ONE);
				forTwoTemp.setChildList(cardCategoryDao.findSelectObject(queryTwoFist));
			}
			
			forTemp.setChildList(childList);
		}
		
		return resultList;
	}

}
