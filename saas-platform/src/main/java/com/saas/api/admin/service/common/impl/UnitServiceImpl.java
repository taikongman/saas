package com.saas.api.admin.service.common.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.saas.api.admin.res.select.common.UnitSelect;
import com.saas.api.admin.service.common.UnitService;
import com.saas.api.common.dao.common.UnitDao;
import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.common.Unit;

@Service
public class UnitServiceImpl implements UnitService {

	@Resource
    private UnitDao unitDao;

	@Override
	public Unit findByPrimayKey(Integer id) {
		return unitDao.findById(id);
	}

	@Override
	public Unit findByName(String unitName) {
		return unitDao.findByName(unitName);
	}

	@Override
	public int insertData(Unit record) {
		record.setCreateTime(new Date());
		return unitDao.insertData(record);
	}

	@Override
	public int updateData(Unit record) {
		record.setUpdateTime(new Date());
		return unitDao.updateData(record);
	}

	@Override
	public int deleteByPrimayKey(Integer id) {
		return unitDao.deleteById(id);
	}

	@Override
	public Map<String, Object> getListData(Integer id, String lanType, Page page) {
		
		List<Unit> resultList = unitDao.listData(id, lanType, page.getStartIndex(), page.getPageSize());
		Integer total = unitDao.countData(id, lanType);

		Map<String, Object> result = new HashMap<>();
		result.put("dataList", resultList);
        result.put("total", total);
        return result;
	}

	@Override
	public List<Unit> findByObject(Unit record) {
		return unitDao.findByObject(record);
	}

	@Override
	public List<UnitSelect> findSelectObject(Unit record) {
		return unitDao.findSelectObject(record);
	}
	
	
	
	
	

}
