package com.saas.api.admin.service.common.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.saas.api.admin.res.select.common.AutoBrandSelect;
import com.saas.api.admin.res.select.common.AutoModelSelect;
import com.saas.api.admin.res.select.common.AutoSeriesSelect;
import com.saas.api.admin.service.common.AutoService;
import com.saas.api.common.dao.common.AutoBrandDao;
import com.saas.api.common.dao.common.AutoModelDao;
import com.saas.api.common.dao.common.AutoSeriesDao;
import com.saas.api.common.entity.common.AutoBrand;
import com.saas.api.common.entity.common.AutoModel;
import com.saas.api.common.entity.common.AutoSeries;

@Service
public class AutoServiceImpl implements AutoService {
	
	@Resource
    private AutoBrandDao autoBrandDao;
	
	@Resource
    private AutoSeriesDao autoSeriesDao;
	
	@Resource
    private AutoModelDao autoModelDao;

	@Override
	public List<AutoBrandSelect> getSelectAutoBrandList(AutoBrand record) {
		List<AutoBrandSelect>  resultList = autoBrandDao.findSelectObject(record);
		return resultList;
	}

	@Override
	public List<AutoSeriesSelect> getSelectAutoSeriesList(AutoSeries record) {
		List<AutoSeriesSelect>  resultList = autoSeriesDao.findSelectObject(record);
		return resultList;
	}

	@Override
	public List<AutoModelSelect> getSelectAutoModelList(AutoModel record) {
		List<AutoModelSelect>  resultList = autoModelDao.findSelectObject(record);
		return resultList;
	}

	@Override
	public List<Integer> getSelectAutoYear(AutoModel record) {
		return autoModelDao.findAutoYears(record);
	}

}
