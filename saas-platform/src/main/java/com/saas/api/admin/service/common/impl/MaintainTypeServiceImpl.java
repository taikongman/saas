package com.saas.api.admin.service.common.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.saas.api.admin.res.select.common.MaintainTypeSelect;
import com.saas.api.admin.service.common.MaintainTypeService;
import com.saas.api.common.dao.common.MaintainTypeDao;
import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.common.MaintainType;

@Service
public class MaintainTypeServiceImpl implements MaintainTypeService {

	@Resource
    private MaintainTypeDao maintainTypeDao;
    
	@Override
	public MaintainType findByPrimayKey(Integer id) {
		return maintainTypeDao.findById(id);
	}

	@Override
	public MaintainType findByName(String name) {
		return maintainTypeDao.findByName(name);
	}

	@Override
	public int insertData(MaintainType record) {
		record.setCreateTime(new Date());
		return maintainTypeDao.insertSelective(record);
	}

	@Override
	public int updateData(MaintainType record) {
		record.setUpdateTime(new Date());
		return maintainTypeDao.updateData(record);
	}

	@Override
	public int insertDataByParams(String name, String remark, String lanType) {
		MaintainType record = new MaintainType();
		record.setName(name);
		record.setRemark(remark);
		record.setLanType(lanType);
		record.setCreateTime(new Date());
		
		return maintainTypeDao.insertSelective(record);
	}

	@Override
	public int updateDataByParams(Integer id, String name, String remark, String lanType) {
		MaintainType record = new MaintainType();
		record.setId(id);
		record.setName(name);
		record.setRemark(remark);
		record.setLanType(lanType);
		record.setUpdateTime(new Date());
		
		return maintainTypeDao.updateData(record);
	}

	@Override
	public int deleteByPrimayKey(Integer id) {
		return maintainTypeDao.deleteById(id);
	}

	@Override
	public Map<String, Object> getListData(Integer id, String lanType, Page page) {
		List<MaintainType> resultList = maintainTypeDao.listData(id, lanType, page.getStartIndex(), page.getPageSize());
		Integer total = maintainTypeDao.countData(id, lanType);

		Map<String, Object> result = new HashMap<>();
		result.put("dataList", resultList);
        result.put("total", total);
        return result;
	}

	@Override
	public List<MaintainType> findByObject(MaintainType record) {
		return maintainTypeDao.findByObject(record);
	}

	@Override
	public List<MaintainTypeSelect> findSelectObject(String lanType) {
		MaintainType record = new MaintainType();
		record.setLanType(lanType);
		return maintainTypeDao.findSelectObject(record);
	}

}
