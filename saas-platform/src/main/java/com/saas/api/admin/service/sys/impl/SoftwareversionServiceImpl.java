package com.saas.api.admin.service.sys.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.saas.api.admin.service.sys.SoftwareversionService;
import com.saas.api.common.dao.sys.SoftwareversionDao;
import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.sys.Softwareversion;

@Service
public class SoftwareversionServiceImpl implements SoftwareversionService {

	@Resource
    private SoftwareversionDao softversionDao;
	
	@Override
	public Map<String, Object> getListData(String appname, String osname, String version, Page page) {
		Map<String, Object> result = new HashMap<>();
		List<Softwareversion> configList = softversionDao.listData(appname, osname, version, page.getStartIndex(), page.getPageSize());
		Integer total = softversionDao.countData(appname, osname, version);

		result.put("list", configList);
        result.put("total", total);
        return result;
	}

	@Override
	public Softwareversion findById(Integer id) {
		Softwareversion softwareversion = softversionDao.findById(id);
		return softwareversion;
	}

	@Override
	public Softwareversion findCheckVersion(String appname, String osname, String version) {
		Softwareversion softwareversion = softversionDao.findCheckVersion(appname, osname, version);
		return softwareversion;
	}

	@Override
	public int insertData(Softwareversion softversion) {
		softversion.setCreateTime(new Date());
		softversionDao.insertData(softversion);
		return softversion.getId();
	}

	@Override
	public int updateData(Softwareversion softversion) {
		softversion.setUpdateTime(new Date());
		int result = softversionDao.updateData(softversion);
		return result;
	}

	@Override
	public int deleteById(Integer id) {
		int result = softversionDao.deleteById(id);
		return result;
	}
	
	
	
}
