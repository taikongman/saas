package com.saas.api.admin.service.sys.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.saas.api.admin.service.sys.CompanyService;
import com.saas.api.common.dao.common.AreaDao;
import com.saas.api.common.dao.common.CityDao;
import com.saas.api.common.dao.common.ProvinceDao;
import com.saas.api.common.dao.sys.CompanyDao;
import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.common.Area;
import com.saas.api.common.entity.common.City;
import com.saas.api.common.entity.common.Province;
import com.saas.api.common.entity.sys.Company;
import com.saas.api.common.util.PublicFileUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CompanyServiceImpl implements CompanyService {

	@Resource
    private CompanyDao companyDao;
	
	@Resource
    private ProvinceDao provinceDao;
	
	@Resource
    private CityDao cityDao;
	
	@Resource
    private AreaDao areaDao;
	
	@Override
	public Map<String, Object> getListData(Integer groupId, Integer companyId, String lanType, Page page) {
		log.debug("CompanyServiceImpl");
		Map<String, Object> result = new HashMap<>();
		List<Company> resultList = companyDao.listData(groupId, companyId, lanType, page.getStartIndex(), page.getPageSize());
		for(Company forTemp : resultList) {
			forTemp.setLogo_url(PublicFileUtils.createUploadUrl(forTemp.getLogo()));
		}
		Integer total = companyDao.countData(groupId, companyId, lanType);

		result.put("dataList", resultList);
        result.put("total", total);
        return result;
	}
	
	@Override
	public List<Company> findByObject(Company company) {
		List<Company> resultList = companyDao.findByObject(company);
		return resultList;
	}

	@Override
	public Company findByPrimayKey(Integer companyId) {
		Company result = companyDao.findById(companyId);
		return result;
	}
	
	@Override
	public Company findByName(String companyName) {
		Company result = companyDao.findByName(companyName);
		return result;
	}

	@Override
	public int insertData(Company company) {
		Province province = provinceDao.findById(company.getProvinceId());
		City city = cityDao.findById(company.getCityId());
		Area area = areaDao.findById(company.getAreaId());
		if(province != null) {
			company.setProvince(province.getProvinceName());
		}
		if(city != null) {
			company.setCity(city.getCityName());
		}
		if(area != null) {
			company.setArea(area.getAreaName());
		}
		company.setDetailAddress(new StringBuffer(company.getProvince()).append(company.getCity())
				.append(company.getArea()).append(company.getAddress()).toString());
		
		companyDao.insertData(company);
		return company.getCompanyId();
	}

	@Override
	public int updateData(Company company) {
		Province province = provinceDao.findById(company.getProvinceId());
		City city = cityDao.findById(company.getCityId());
		Area area = areaDao.findById(company.getAreaId());
		if(province != null) {
			company.setProvince(province.getProvinceName());
		}
		if(city != null) {
			company.setCity(city.getCityName());
		}
		if(area != null) {
			company.setArea(area.getAreaName());
		}
		company.setDetailAddress(new StringBuffer(company.getProvince()).append(company.getCity())
				.append(company.getArea()).append(company.getAddress()).toString());
		
		int result = companyDao.updateData(company);
		return result;
	}

	@Override
	public int deleteByPrimayKey(Integer companyId) {
		int result = companyDao.deleteById(companyId);
		return result;
	}
	

}
