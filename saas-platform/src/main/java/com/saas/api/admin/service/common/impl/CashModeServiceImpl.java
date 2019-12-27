package com.saas.api.admin.service.common.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.saas.api.admin.res.select.common.CashModeSelect;
import com.saas.api.admin.service.common.CashModeService;
import com.saas.api.common.dao.common.CashModeDao;
import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.common.CashMode;

@Service
public class CashModeServiceImpl implements CashModeService {

	@Resource
    private CashModeDao cashModeDao;

	@Override
	public CashMode findByPrimayKey(Integer id) {
		return cashModeDao.findById(id);
	}

	@Override
	public CashMode findByName(String name) {
		return cashModeDao.findByName(name);
	}

	@Override
	public int insertData(CashMode record) {
		record.setCreateTime(new Date());
		return cashModeDao.insertData(record);
	}

	@Override
	public int updateData(CashMode record) {
		record.setUpdateTime(new Date());
		return cashModeDao.updateData(record);
	}

	
	
	@Override
	public int insertDataByParams(String name, String remark, String lanType) {
		CashMode record = new CashMode();
		record.setName(name);
		record.setRemark(remark);
		record.setLanType(lanType);
		record.setCreateTime(new Date());
		return cashModeDao.insertSelective(record);
	}

	@Override
	public int updateDataByParams(Integer id, String name, String remark, String lanType) {
		CashMode record = new CashMode();
		record.setId(id);
		record.setName(name);
		record.setRemark(remark);
		record.setLanType(lanType);
		record.setUpdateTime(new Date());
		return cashModeDao.updateData(record);
	}

	@Override
	public int deleteByPrimayKey(Integer id) {
		return cashModeDao.deleteById(id);
	}

	@Override
	public Map<String, Object> getListData(Integer id, String lanType, Page page) {
		
		List<CashMode> resultList = cashModeDao.listData(id, lanType, page.getStartIndex(), page.getPageSize());
		Integer total = cashModeDao.countData(id, lanType);

		Map<String, Object> result = new HashMap<>();
		result.put("dataList", resultList);
        result.put("total", total);
        return result;
	}

	@Override
	public List<CashMode> findByObject(CashMode record) {
		return cashModeDao.findByObject(record);
	}

	@Override
	public List<CashModeSelect> findSelectObject(String lanType) {
		CashMode record = new CashMode();
		record.setLanType(lanType);
		return cashModeDao.findSelectObject(record);
	}
	
	
	
	
	

}
