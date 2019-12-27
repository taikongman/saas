package com.saas.api.admin.service.common.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.saas.api.admin.res.select.common.SettleModeSelect;
import com.saas.api.admin.service.common.SettleModeService;
import com.saas.api.common.dao.common.SettleModeDao;
import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.common.SettleMode;

@Service
public class SettleModeServiceImpl implements SettleModeService {

	@Resource
    private SettleModeDao settleModeDao;
    
	@Override
	public SettleMode findByPrimayKey(Integer id) {
		return settleModeDao.findById(id);
	}

	@Override
	public SettleMode findByName(String name) {
		return settleModeDao.findByName(name);
	}

	@Override
	public int insertData(SettleMode record) {
		record.setCreateTime(new Date());
		return settleModeDao.insertSelective(record);
	}

	@Override
	public int updateData(SettleMode record) {
		record.setUpdateTime(new Date());
		return settleModeDao.updateData(record);
	}

	@Override
	public int insertDataByParams(String name, String remark, String lanType) {
		SettleMode record = new SettleMode();
		record.setName(name);
		record.setRemark(remark);
		record.setLanType(lanType);
		record.setCreateTime(new Date());
		return settleModeDao.insertSelective(record);
	}

	@Override
	public int updateDataByParams(Integer id, String name, String remark, String lanType) {
		SettleMode record = new SettleMode();
		record.setId(id);
		record.setName(name);
		record.setRemark(remark);
		record.setLanType(lanType);
		record.setUpdateTime(new Date());
		return settleModeDao.updateData(record);
	}

	@Override
	public int deleteByPrimayKey(Integer id) {
		return settleModeDao.deleteById(id);
	}

	@Override
	public Map<String, Object> getListData(Integer id, String lanType, Page page) {
		List<SettleMode> resultList = settleModeDao.listData(id, lanType, page.getStartIndex(), page.getPageSize());
		Integer total = settleModeDao.countData(id, lanType);

		Map<String, Object> result = new HashMap<>();
		result.put("dataList", resultList);
        result.put("total", total);
        return result;
	}

	@Override
	public List<SettleMode> findByObject(SettleMode record) {
		return settleModeDao.findByObject(record);
	}

	@Override
	public List<SettleModeSelect> findSelectObject(String lanType) {
		SettleMode record = new SettleMode();
		record.setLanType(lanType);
		return settleModeDao.findSelectObject(record);
	}

}
