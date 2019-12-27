package com.saas.api.admin.service.common;

import java.util.List;

import com.saas.api.admin.res.select.common.AreaSelect;
import com.saas.api.admin.res.select.common.CitySelect;
import com.saas.api.admin.res.select.common.ProvinceSelect;
import com.saas.api.common.entity.common.Area;
import com.saas.api.common.entity.common.City;
import com.saas.api.common.entity.common.Province;


public interface AddressService {

	List<ProvinceSelect> getSelectProvinceList(Province record);
	
	List<CitySelect> querySelectCityList(City record);
	
	List<String> queryAllAutoCodes();
	
	List<AreaSelect> querySelectAreaList(Area record);
}
