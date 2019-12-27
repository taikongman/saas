package com.saas.api.admin.service.common.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.saas.api.admin.res.select.common.PricingModeSelect;
import com.saas.api.admin.service.common.PricingModeService;
import com.saas.api.common.dao.common.PricingModeDao;
import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.common.PricingMode;

@Service
public class PricingModeServiceImpl implements PricingModeService {

	@Resource
    private PricingModeDao pricingModeDao;
    
	@Override
	public PricingMode findByPrimayKey(Integer id) {
		return pricingModeDao.findById(id);
	}

	@Override
	public PricingMode findByName(String name) {
		return pricingModeDao.findByName(name);
	}

	@Override
	public int insertData(PricingMode record) {
		record.setCreateTime(new Date());
		return pricingModeDao.insertSelective(record);
	}

	@Override
	public int updateData(PricingMode record) {
		record.setUpdateTime(new Date());
		return pricingModeDao.insertSelective(record);
	}

	@Override
	public int insertDataByParams(String name, Integer isTime, String remark, String lanType) {
		PricingMode record = new PricingMode();
		record.setName(name);
		record.setIsTime(isTime);
		record.setRemark(remark);
		record.setLanType(lanType);
		record.setCreateTime(new Date());
		return pricingModeDao.insertSelective(record);
	}

	@Override
	public int updateDataByParams(Integer id, String name, Integer isTime, String remark, String lanType) {
		PricingMode record = new PricingMode();
		record.setId(id);
		record.setName(name);
		record.setIsTime(isTime);
		record.setRemark(remark);
		record.setLanType(lanType);
		record.setUpdateTime(new Date());
		return pricingModeDao.updateData(record);
	}

	@Override
	public int deleteByPrimayKey(Integer id) {
		return pricingModeDao.deleteById(id);
	}

	@Override
	public Map<String, Object> getListData(Integer id, String lanType, Page page) {
		List<PricingMode> resultList = pricingModeDao.listData(id, lanType, page.getStartIndex(), page.getPageSize());
		Integer total = pricingModeDao.countData(id, lanType);

		Map<String, Object> result = new HashMap<>();
		result.put("dataList", resultList);
        result.put("total", total);
        return result;
	}

	@Override
	public List<PricingMode> findByObject(PricingMode record) {
		return pricingModeDao.findByObject(record);
	}

	@Override
	public List<PricingModeSelect> findSelectObject(String lanType) {
		PricingMode record = new PricingMode();
		record.setLanType(lanType);
		return pricingModeDao.findSelectObject(record);
	}

}
