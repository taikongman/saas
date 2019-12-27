package com.saas.api.admin.service.common.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.saas.api.admin.res.select.common.TransportModeSelect;
import com.saas.api.admin.service.common.TransportModeService;
import com.saas.api.common.dao.common.TransportModeDao;
import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.common.TransportMode;

@Service
public class TransportModeServiceImpl implements TransportModeService {

	@Resource
    private TransportModeDao transportDao;

	@Override
	public TransportMode findByPrimayKey(Integer id) {
		return transportDao.findById(id);
	}

	@Override
	public TransportMode findByName(String transportName) {
		return transportDao.findByName(transportName);
	}

	@Override
	public int insertData(TransportMode record) {
		record.setCreateTime(new Date());
		return transportDao.insertData(record);
	}

	@Override
	public int updateData(TransportMode record) {
		record.setUpdateTime(new Date());
		return transportDao.updateData(record);
	}

	@Override
	public int deleteByPrimayKey(Integer id) {
		return transportDao.deleteById(id);
	}

	@Override
	public Map<String, Object> getListData(Integer id, String lanType, Page page) {
		List<TransportMode> resultList = transportDao.listData(id, lanType, page.getStartIndex(), page.getPageSize());
		Integer total = transportDao.countData(id, lanType);

		Map<String, Object> result = new HashMap<>();
		result.put("dataList", resultList);
        result.put("total", total);
        return result;
	}

	@Override
	public List<TransportMode> findByObject(TransportMode record) {
		return transportDao.findByObject(record);
	}

	@Override
	public List<TransportModeSelect> findSelectObject(TransportMode record) {
		return transportDao.findSelectObject(record);
	}
	
	

}
