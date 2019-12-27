package com.saas.api.admin.service.sys.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.saas.api.admin.service.sys.ConfigService;
import com.saas.api.common.dao.sys.ConfigDao;
import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.sys.Config;


@Service
public class ConfigServiceImpl implements ConfigService {

	@Resource
    private ConfigDao configDao;
	
	@Override
	public Map<String, Object> getListData(String keyName, Page page) {
		Map<String, Object> result = new HashMap<>();
		List<Config> configList = configDao.listData(keyName, page.getStartIndex(), page.getPageSize());
		Integer total = configDao.countData(keyName);

		result.put("list", configList);
        result.put("total", total);
        return result;
	}

	@Override
	public Config findById(Integer id) {
		Config config = configDao.findById(id);
		return config;
	}

	@Override
	public Config findByKeyName(String keyName) {
		Config config = configDao.findByKeyName(keyName);
		return config;
	}

	@Override
	public int insertData(Config config) {
		config.setCreateTime(new Date());
		configDao.insertData(config);
		return config.getId();
	}

	@Override
	public int updateData(Config config) {
		config.setUpdateTime(new Date());
		int result = configDao.updateData(config);
		return result;
	}

	@Override
	public int deleteById(Integer id) {
		int result = configDao.deleteById(id);
		return result;
	}

}
