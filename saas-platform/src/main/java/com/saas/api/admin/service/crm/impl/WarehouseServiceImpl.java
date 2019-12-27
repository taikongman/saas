package com.saas.api.admin.service.crm.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.saas.api.admin.res.crm.WarehouseResponse;
import com.saas.api.admin.res.select.crm.WarehouseSelect;
import com.saas.api.admin.service.crm.WarehouseService;
import com.saas.api.common.constant.DbConstant;
import com.saas.api.common.dao.common.AreaDao;
import com.saas.api.common.dao.common.CityDao;
import com.saas.api.common.dao.common.ProvinceDao;
import com.saas.api.common.dao.crm.WarehouseDao;
import com.saas.api.common.dao.crm.WarehouseLocationDao;
import com.saas.api.common.dao.sys.auth.UserDao;
import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.common.Area;
import com.saas.api.common.entity.common.City;
import com.saas.api.common.entity.common.Province;
import com.saas.api.common.entity.crm.Warehouse;
import com.saas.api.common.entity.crm.WarehouseLocation;
import com.saas.api.common.entity.sys.auth.User;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class WarehouseServiceImpl implements WarehouseService {

	@Resource
    private WarehouseDao warehouseDao;
	
	@Resource
    private WarehouseLocationDao warehouseLocationDao;
	
	@Resource
    private UserDao userDao;
	
	@Resource
    private ProvinceDao provinceDao;
    
    @Resource
    private CityDao cityDao;
    
    @Resource
    private AreaDao areaDao;
	
	@Override
	public Warehouse findByPrimayKey(Integer warehouseId) {
		return warehouseDao.selectByPrimaryKey(warehouseId);
	}

	@Override
	public Warehouse findByName(Long adminId, String warehouseName, String lanType) {
		User user = userDao.findById(adminId);
		return warehouseDao.selectByWarehouseName(user.getGroupId(), user.getCompanyId(), null, null, 
				lanType, warehouseName);
	}

	@Override
	@Transactional
	public int insertData(Warehouse record, JSONArray locationList) {
		User user = userDao.findById(record.getAdminId());
		record.setGroupId(user.getGroupId());
		record.setCompanyId(user.getCompanyId());
		record.setDepartmentId(user.getDepartmentId());
		
		if(!StringUtils.isEmpty(record.getProvinceId())) {
        	Province province = provinceDao.findById(record.getProvinceId());
        	if(province != null) {
        		record.setProvince(province.getProvinceName());
        	}
        }
        if(!StringUtils.isEmpty(record.getCityId())) {
        	City city = cityDao.findById(record.getCityId());
        	if(city != null) {
        		record.setCity(city.getCityName());
        	}
        }
        if(!StringUtils.isEmpty(record.getAreaId())) {
        	Area area = areaDao.findById(record.getAreaId());
        	if(area != null) {
        		record.setArea(area.getAreaName());
        	}
        }
        if(!StringUtils.isEmpty(record.getProvinceId()) 
        		&& !StringUtils.isEmpty(record.getCityId())
        		&& !StringUtils.isEmpty(record.getAreaId())
        		&& !StringUtils.isEmpty(record.getAddress())) {
        	record.setFullAddress(new StringBuffer(record.getProvince()).append(record.getCity())
					.append(record.getArea()).append(record.getAddress()).toString());
        }
        
        record.setCreateTime(new Date());
        record.setStatus(DbConstant.STATUS_ONE);
        int result = warehouseDao.insert(record);
        
        if (null != locationList && locationList.size() > 0) {
        	WarehouseLocation location = null;
            for (int i = 0; i < locationList.size(); i++) {
            	location = (WarehouseLocation) JSONObject.toBean(locationList.getJSONObject(i), WarehouseLocation.class);
            	location.setWarehouseId(record.getWarehouseId());
            	
            	location.setStatus(DbConstant.STATUS_ONE);
            	location.setCreateTime(new Date());
            	warehouseLocationDao.insert(location);
            }
        }
		return result;
	}

	@Override
	@Transactional
	public int updateData(Warehouse record, JSONArray locationList) {
		record.setUpdateTime(new Date());
		if(!StringUtils.isEmpty(record.getProvinceId())) {
        	Province province = provinceDao.findById(record.getProvinceId());
        	if(province != null) {
        		record.setProvince(province.getProvinceName());
        	}
        }
        if(!StringUtils.isEmpty(record.getCityId())) {
        	City city = cityDao.findById(record.getCityId());
        	if(city != null) {
        		record.setCity(city.getCityName());
        	}
        }
        if(!StringUtils.isEmpty(record.getAreaId())) {
        	Area area = areaDao.findById(record.getAreaId());
        	if(area != null) {
        		record.setArea(area.getAreaName());
        	}
        }
        if(!StringUtils.isEmpty(record.getProvinceId()) 
        		&& !StringUtils.isEmpty(record.getCityId())
        		&& !StringUtils.isEmpty(record.getAreaId())
        		&& !StringUtils.isEmpty(record.getAddress())) {
        	record.setFullAddress(new StringBuffer(record.getProvince()).append(record.getCity())
					.append(record.getArea()).append(record.getAddress()).toString());
        }
		int result = warehouseDao.updateByPrimaryKeySelective(record);
        
        if (null != locationList && locationList.size() > 0) {
        	WarehouseLocation location = null;
            for (int i = 0; i < locationList.size(); i++) {
            	location = (WarehouseLocation) JSONObject.toBean(locationList.getJSONObject(i), WarehouseLocation.class);
            	                
                if(location.getId() == null) {
                	location.setStatus(DbConstant.STATUS_ONE);
                	location.setCreateTime(new Date());
                	location.setWarehouseId(record.getWarehouseId());
                	warehouseLocationDao.insert(location);
            	}else {
            		location.setUpdateTime(new Date());
            		warehouseLocationDao.updateByPrimaryKeySelective(location);
            	}
            }
        }
		
		return result;
	}

	@Override
	@Transactional
	public int deleteByPrimayKey(Integer warehouseId) {
		warehouseLocationDao.deleteByWarehouseId(warehouseId);
		return warehouseDao.deleteByPrimaryKey(warehouseId);
	}

	@Override
	public Map<String, Object> getListData(Integer warehouseId, String warehouseCode, String warehouseName,
			Long adminId, String lanType, Page page) {
		User user = userDao.findById(adminId);
		List<Warehouse> resultList = warehouseDao.listData(user.getGroupId(), user.getCompanyId(), null, null, 
				lanType, warehouseCode, warehouseName, 
				DbConstant.STATUS_ONE, page.getStartIndex(), page.getPageSize());
		
		Integer total = warehouseDao.countData(user.getGroupId(), user.getCompanyId(), null, null, 
				lanType, warehouseCode, warehouseName, 
				DbConstant.STATUS_ONE);
		
		List<WarehouseResponse> dataList = new ArrayList<WarehouseResponse>();
		for(Warehouse forTemp : resultList) {
			WarehouseResponse wareResponse = new WarehouseResponse();
			BeanUtils.copyProperties(forTemp, wareResponse);
			wareResponse.setLocationList(warehouseLocationDao.findByWarehouseId(forTemp.getWarehouseId()));
			
			dataList.add(wareResponse);
		}
		
		Map<String, Object> result = new HashMap<>();
        result.put("dataList", dataList);
        result.put("total", total);
        return result;
	}

	@Override
	public List<WarehouseResponse> findByObject(Warehouse record) {
		User user = userDao.findById(record.getAdminId());
		record.setGroupId(user.getGroupId());
		record.setCompanyId(user.getCompanyId());
		List<Warehouse> resultList = warehouseDao.findByObject(record);
		
		List<WarehouseResponse> dataList = new ArrayList<WarehouseResponse>();
		for(Warehouse forTemp : resultList) {
			WarehouseResponse wareResponse = new WarehouseResponse();
			BeanUtils.copyProperties(forTemp, wareResponse);
			wareResponse.setLocationList(warehouseLocationDao.findByWarehouseId(forTemp.getWarehouseId()));
			
			dataList.add(wareResponse);
		}
				
		return dataList;
	}

	@Override
	public List<WarehouseSelect> findSelectObject(Long adminId, Warehouse record) {
		User user = userDao.findById(adminId);
		record.setGroupId(user.getGroupId());
		record.setCompanyId(user.getCompanyId());
		return warehouseDao.findSelectObject(record);
	}

}
