package com.saas.api.admin.service.sys.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.saas.api.admin.service.sys.GroupService;
import com.saas.api.common.dao.common.AreaDao;
import com.saas.api.common.dao.common.CityDao;
import com.saas.api.common.dao.common.ProvinceDao;
import com.saas.api.common.dao.sys.GroupDao;
import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.common.Area;
import com.saas.api.common.entity.common.City;
import com.saas.api.common.entity.common.Province;
import com.saas.api.common.entity.sys.Group;
import com.saas.api.common.util.PublicFileUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GroupServiceImple implements GroupService {

	@Resource
    private GroupDao groupDao;
	
	@Resource
    private ProvinceDao provinceDao;
	
	@Resource
    private CityDao cityDao;
	
	@Resource
    private AreaDao areaDao;
	
	@Override
	public Map<String, Object> getListData(Integer groupId, String lanType, Page page) {
		log.debug("GroupServiceImple");
		Map<String, Object> result = new HashMap<>();
		List<Group> resultList = groupDao.listData(groupId, lanType, page.getStartIndex(), page.getPageSize());
		for(Group forTemp : resultList) {
			forTemp.setLogo_url(PublicFileUtils.createUploadUrl(forTemp.getLogo()));
		}
		Integer total = groupDao.countData(groupId, lanType);

		result.put("dataList", resultList);
        result.put("total", total);
        return result;
	}

	@Override
	public List<Group> findByObject(Group record) {
		List<Group> resultList = groupDao.findByObject(record);
		return resultList;
	}

	@Override
	public Group findByPrimayKey(Integer groupId) {
		Group result = groupDao.findById(groupId);
		return result;
	}

	@Override
	public Group findByName(String groupName) {
		Group result = groupDao.findByName(groupName);
		return result;
	}

	@Override
	public int insertData(Group record) {
		Province province = provinceDao.findById(record.getProvinceId());
		City city = cityDao.findById(record.getCityId());
		Area area = areaDao.findById(record.getAreaId());
		if(province != null) {
			record.setProvince(province.getProvinceName());
		}
		if(city != null) {
			record.setCity(city.getCityName());
		}
		if(area != null) {
			record.setArea(area.getAreaName());
		}
		record.setDetailAddress(new StringBuffer(record.getProvince()).append(record.getCity())
				.append(record.getArea()).append(record.getAddress()).toString());
		
		groupDao.insertData(record);
		return record.getGroupId();
	}

	@Override
	public int updateData(Group record) {
		Province province = provinceDao.findById(record.getProvinceId());
		City city = cityDao.findById(record.getCityId());
		Area area = areaDao.findById(record.getAreaId());
		if(province != null) {
			record.setProvince(province.getProvinceName());
		}
		if(city != null) {
			record.setCity(city.getCityName());
		}
		if(area != null) {
			record.setArea(area.getAreaName());
		}
		record.setDetailAddress(new StringBuffer(record.getProvince()).append(record.getCity())
				.append(record.getArea()).append(record.getAddress()).toString());
		
		int result = groupDao.updateData(record);
		return result;
	}

	@Override
	public int deleteByPrimayKey(Integer groupId) {
		int result = groupDao.deleteById(groupId);
		return result;
	}

}
