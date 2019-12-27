package com.saas.api.admin.service.crm.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.saas.api.admin.service.crm.WarehouseLocationService;
import com.saas.api.common.constant.DbConstant;
import com.saas.api.common.dao.crm.WarehouseLocationDao;
import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.crm.WarehouseLocation;

@Service
public class WarehouseLocationServiceImpl implements WarehouseLocationService {

	@Resource
    private WarehouseLocationDao locationDao;
    
	@Override
	public WarehouseLocation findByPrimayKey(Long id) {
		return locationDao.selectByPrimaryKey(id);
	}

	@Override
	public WarehouseLocation findByName(Integer warehouseId, String locationName) {
		return locationDao.findByName(warehouseId, locationName);
	}

	@Override
	public int insertData(WarehouseLocation record) {
		
		record.setCreateTime(new Date());
        record.setStatus(DbConstant.STATUS_ONE);
		return locationDao.insert(record);
	}

	@Override
	public int updateData(WarehouseLocation record) {
		
		record.setUpdateTime(new Date());
		return locationDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int deleteByPrimayKey(Long id) {
		return locationDao.deleteByPrimaryKey(id);
	}

	@Override
	public int deleteBySupplierId(Integer warehouseId) {
		return locationDao.deleteByWarehouseId(warehouseId);
	}

	@Override
	public Map<String, Object> getListData(Integer warehouseId, String locationCode, String locationName, Page page) {
		
		List<WarehouseLocation> resultList = locationDao.listData(warehouseId, locationCode, locationName, 
				DbConstant.STATUS_ONE,
				page.getStartIndex(), page.getPageSize());
		
		Integer total = locationDao.countData(warehouseId, locationCode, locationName, 
				DbConstant.STATUS_ONE);
		
		Map<String, Object> result = new HashMap<>();
        result.put("dataList", resultList);
        result.put("total", total);
        return result;
	}

	@Override
	public List<WarehouseLocation> findByWarehouseId(Integer warehouseId) {
		return locationDao.findByWarehouseId(warehouseId);
	}

	@Override
	public List<WarehouseLocation> findByObject(WarehouseLocation record) {
		return locationDao.findByObject(record);
	}

}
