package com.saas.api.admin.service.crm.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.saas.api.admin.service.crm.CommodityAutoService;
import com.saas.api.common.dao.crm.CommodityAutoDao;
import com.saas.api.common.entity.crm.CommodityAuto;

@Service
public class CommodityAutoServiceImpl implements CommodityAutoService {

	@Resource
    private CommodityAutoDao commodityAutoDao;
	
	@Override
	public void addData(CommodityAuto record) {
		record.setCreateTime(new Date());
		commodityAutoDao.insert(record);

	}

	@Override
	public void deleteByCommodityId(Long commodityId) {
		commodityAutoDao.deleteByCommodityId(commodityId);
	}

	@Override
	public List<CommodityAuto> findByCommodityId(Long commodityId) {
		return commodityAutoDao.findByCommodityId(commodityId);
	}

	@Override
	public void deleteById(Long id) {
		commodityAutoDao.deleteByPrimaryKey(id);
	}

}
