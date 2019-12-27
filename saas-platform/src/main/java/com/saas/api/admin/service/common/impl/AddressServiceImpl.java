package com.saas.api.admin.service.common.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.saas.api.admin.res.select.common.AreaSelect;
import com.saas.api.admin.res.select.common.CitySelect;
import com.saas.api.admin.res.select.common.ProvinceSelect;
import com.saas.api.admin.service.common.AddressService;
import com.saas.api.common.dao.common.AreaDao;
import com.saas.api.common.dao.common.CityDao;
import com.saas.api.common.dao.common.ProvinceDao;
import com.saas.api.common.entity.common.Area;
import com.saas.api.common.entity.common.City;
import com.saas.api.common.entity.common.Province;

@Service
public class AddressServiceImpl implements AddressService {

	@Resource
    private ProvinceDao provinceDao;
	
	@Resource
    private CityDao cityDao;
	
	@Resource
    private AreaDao areaDao;

	@Override
	public List<ProvinceSelect> getSelectProvinceList(Province record) {
		List<ProvinceSelect>  resultList = provinceDao.findSelectObject(record);
		return resultList;
	}

	@Override
	public List<CitySelect> querySelectCityList(City record) {
		List<CitySelect>  resultList = cityDao.findSelectObject(record);
		return resultList;
	}

	@Override
	public List<String> queryAllAutoCodes() {
		List<String>  resultList = cityDao.findAllAutoCode();
		return resultList;
	}

	@Override
	public List<AreaSelect> querySelectAreaList(Area record) {
		List<AreaSelect>  resultList = areaDao.findSelectObject(record);
		return resultList;
	}
	
	
	

}
