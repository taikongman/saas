package com.saas.api.admin.service.crm.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.saas.api.admin.res.select.crm.CommodityCategorySelect;
import com.saas.api.admin.service.crm.CommodityCategoryService;
import com.saas.api.common.constant.CommonConstant;
import com.saas.api.common.constant.DbConstant;
import com.saas.api.common.dao.crm.CommodityCategoryDao;
import com.saas.api.common.dao.sys.auth.UserDao;
import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.crm.CommodityCategory;
import com.saas.api.common.entity.sys.auth.User;

@Service
public class CommodityCategoryServiceImpl implements CommodityCategoryService {

	@Resource
    private CommodityCategoryDao commodityCategoryDao;
    
	@Resource
    private UserDao userDao;
    
	@Override
	public CommodityCategory findByPrimayKey(Integer categoryId) {		
		return commodityCategoryDao.selectByPrimaryKey(categoryId);
	}

	@Override
	public CommodityCategory findByName(Long adminId, String categoryName, String lanType) {
		User user = userDao.findById(adminId);
		return commodityCategoryDao.selectByName(user.getGroupId(), user.getCompanyId(), categoryName, lanType);
	}

	@Override
	public int insertData(CommodityCategory record) {
		record.setStatus(DbConstant.STATUS_ONE);
		record.setCreateTime(new Date());
		return commodityCategoryDao.insert(record);
	}

	@Override
	public int updateData(CommodityCategory record) {
		record.setUpdateTime(new Date());
		return commodityCategoryDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int deleteByPrimayKey(Integer categoryId) {
		return commodityCategoryDao.deleteByPrimaryKey(categoryId);
	}

	@Override
	public Map<String, Object> getListData(Integer categoryId, Long adminId, String lanType, Page page) {
		User user = userDao.findById(adminId);
		List<CommodityCategory> resultList = commodityCategoryDao.listData(user.getGroupId(), user.getCompanyId(), 
				null, null, 
				categoryId, lanType, page.getStartIndex(), page.getPageSize());
		Integer total = commodityCategoryDao.countData(user.getGroupId(), user.getCompanyId(), 
				null, null, 
				categoryId, lanType);
		
		Map<String, Object> result = new HashMap<>();
        result.put("dataList", resultList);
        result.put("total", total);
        return result;
	}

	@Override
	public List<CommodityCategory> findByObject(CommodityCategory record) {
		return commodityCategoryDao.findByObject(record);
	}

	@Override
	public List<CommodityCategorySelect> findSelectObject(CommodityCategory record) {
		User user = userDao.findById(record.getAdminId());
		record.setGroupId(user.getGroupId());
		record.setCompanyId(user.getCompanyId());
		record.setParentId(CommonConstant.INDEX_ZERO);
		record.setStatus(DbConstant.STATUS_ONE);
		List<CommodityCategorySelect> resultList = commodityCategoryDao.findSelectObject(record);
		for(CommodityCategorySelect forTemp : resultList) {
			CommodityCategory queryFist = new CommodityCategory();
			queryFist.setParentId(forTemp.getCategoryId());
			queryFist.setStatus(DbConstant.STATUS_ONE);
			List<CommodityCategorySelect> childList = commodityCategoryDao.findSelectObject(queryFist);
			for(CommodityCategorySelect forTwoTemp : childList) {
				CommodityCategory queryTwoFist = new CommodityCategory();
				queryTwoFist.setParentId(forTwoTemp.getCategoryId());
				queryTwoFist.setStatus(DbConstant.STATUS_ONE);
				forTwoTemp.setChildList(commodityCategoryDao.findSelectObject(queryTwoFist));
			}
			
			forTemp.setChildList(childList);
		}
		
		return resultList;
	}

}
