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

import com.saas.api.admin.res.crm.SupplierResponse;
import com.saas.api.admin.res.select.crm.SupplierSelect;
import com.saas.api.admin.service.crm.SupplierService;
import com.saas.api.common.constant.DbConstant;
import com.saas.api.common.dao.common.AreaDao;
import com.saas.api.common.dao.common.CityDao;
import com.saas.api.common.dao.common.ProvinceDao;
import com.saas.api.common.dao.crm.SupplierBankDao;
import com.saas.api.common.dao.crm.SupplierDao;
import com.saas.api.common.dao.sys.auth.UserDao;
import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.common.Area;
import com.saas.api.common.entity.common.City;
import com.saas.api.common.entity.common.Province;
import com.saas.api.common.entity.crm.Supplier;
import com.saas.api.common.entity.crm.SupplierBank;
import com.saas.api.common.entity.sys.auth.User;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class SupplierServiceImpl implements SupplierService {

	@Resource
    private SupplierDao supplierDao;
	
	@Resource
    private SupplierBankDao supplierBankDao;
	
	@Resource
    private UserDao userDao;
	
	@Resource
    private ProvinceDao provinceDao;
    
    @Resource
    private CityDao cityDao;
    
    @Resource
    private AreaDao areaDao;
    
    
	@Override
	public Supplier findByPrimayKey(Integer supplierId) {
		return supplierDao.selectByPrimaryKey(supplierId);
	}

	@Override
	public Supplier findByName(Long adminId, String supplierName, String lanType) {
		User user = userDao.findById(adminId);
		
		return supplierDao.selectBySupplierName(user.getGroupId(), user.getCompanyId(), null, null, 
				lanType, supplierName);
	}

	@Override
	@Transactional
	public int insertData(Supplier record, JSONArray supplierBankList) {
		User user = userDao.findById(Long.valueOf(record.getAdminId()));
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
        int result = supplierDao.insert(record);
        
        if (null != supplierBankList && supplierBankList.size() > 0) {
        	SupplierBank supplierBank = null;
            for (int i = 0; i < supplierBankList.size(); i++) {
            	supplierBank = (SupplierBank) JSONObject.toBean(supplierBankList.getJSONObject(i), SupplierBank.class);
            	supplierBank.setSupplierId(record.getSupplierId());
            	if(!StringUtils.isEmpty(supplierBank.getProvinceId())) {
                	Province province = provinceDao.findById(supplierBank.getProvinceId());
                	if(province != null) {
                		supplierBank.setProvince(province.getProvinceName());
                	}
                }
                if(!StringUtils.isEmpty(supplierBank.getCityId())) {
                	City city = cityDao.findById(supplierBank.getCityId());
                	if(city != null) {
                		supplierBank.setCity(city.getCityName());
                	}
                }
                if(!StringUtils.isEmpty(supplierBank.getAreaId())) {
                	Area area = areaDao.findById(supplierBank.getAreaId());
                	if(area != null) {
                		supplierBank.setArea(area.getAreaName());
                	}
                }
                if(!StringUtils.isEmpty(supplierBank.getProvinceId()) 
                		&& !StringUtils.isEmpty(supplierBank.getCityId())
                		&& !StringUtils.isEmpty(supplierBank.getAreaId())
                		&& !StringUtils.isEmpty(supplierBank.getAddress())) {
                	supplierBank.setFullAddress(new StringBuffer(supplierBank.getProvince()).append(supplierBank.getCity())
        					.append(supplierBank.getArea()).append(supplierBank.getAddress()).toString());
                }
                supplierBank.setStatus(DbConstant.STATUS_ONE);
                supplierBank.setCreateTime(new Date());
                supplierBankDao.insertSelective(supplierBank);
            }
        }
		return result;
	}

	@Override
	@Transactional
	public int updateData(Supplier record, JSONArray supplierBankList) {
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
		int result = supplierDao.updateByPrimaryKeySelective(record);
        
        if (null != supplierBankList && supplierBankList.size() > 0) {
        	supplierBankDao.deleteBySupplierId(record.getSupplierId());
        	SupplierBank supplierBank = null;
            for (int i = 0; i < supplierBankList.size(); i++) {
            	supplierBank = (SupplierBank) JSONObject.toBean(supplierBankList.getJSONObject(i), SupplierBank.class);
            	if(!StringUtils.isEmpty(supplierBank.getProvinceId())) {
                	Province province = provinceDao.findById(supplierBank.getProvinceId());
                	if(province != null) {
                		supplierBank.setProvince(province.getProvinceName());
                	}
                }
                if(!StringUtils.isEmpty(supplierBank.getCityId())) {
                	City city = cityDao.findById(supplierBank.getCityId());
                	if(city != null) {
                		supplierBank.setCity(city.getCityName());
                	}
                }
                if(!StringUtils.isEmpty(supplierBank.getAreaId())) {
                	Area area = areaDao.findById(supplierBank.getAreaId());
                	if(area != null) {
                		supplierBank.setArea(area.getAreaName());
                	}
                }
                if(!StringUtils.isEmpty(supplierBank.getProvinceId()) 
                		&& !StringUtils.isEmpty(supplierBank.getCityId())
                		&& !StringUtils.isEmpty(supplierBank.getAreaId())
                		&& !StringUtils.isEmpty(supplierBank.getAddress())) {
                	supplierBank.setFullAddress(new StringBuffer(supplierBank.getProvince()).append(supplierBank.getCity())
        					.append(supplierBank.getArea()).append(supplierBank.getAddress()).toString());
                }
                
                supplierBank.setStatus(DbConstant.STATUS_ONE);
                supplierBank.setCreateTime(new Date());
            	supplierBank.setSupplierId(record.getSupplierId());
        		supplierBankDao.insertSelective(supplierBank);
            }
        }
		
		return result;
	}

	@Override
	public int deleteByPrimayKey(Integer supplierId) {
		supplierBankDao.deleteBySupplierId(supplierId);
		return supplierDao.deleteByPrimaryKey(supplierId);
	}

	@Override
	public Map<String, Object> getListData(Integer supplierId, String supplierName, Long adminId, String lanType,
			Page page) {
		User user = userDao.findById(adminId);
		List<Supplier> resultList = supplierDao.listData(user.getGroupId(), user.getCompanyId(), null, null, 
				lanType, supplierName, DbConstant.STATUS_ONE, page.getStartIndex(), page.getPageSize());
		
		Integer total = supplierDao.countData(user.getGroupId(), user.getCompanyId(), null, null, 
				lanType, supplierName, DbConstant.STATUS_ONE);
		
		List<SupplierResponse> dataList = new ArrayList<SupplierResponse>();
		for(Supplier forTemp : resultList) {
			SupplierResponse supplierResponse = new SupplierResponse();
			BeanUtils.copyProperties(forTemp, supplierResponse);
			
			SupplierBank record = new SupplierBank();
			record.setSupplierId(supplierResponse.getSupplierId());
			supplierResponse.setSupplierBankList(supplierBankDao.findByObject(record));
			
			dataList.add(supplierResponse);
		}
		
		Map<String, Object> result = new HashMap<>();
        result.put("dataList", dataList);
        result.put("total", total);
        return result;
	}

	@Override
	public List<Supplier> findByObject(Supplier record) {
		User user = userDao.findById(record.getAdminId());
		record.setGroupId(user.getGroupId());
		record.setCompanyId(user.getCompanyId());
		
		return supplierDao.findByObject(record);
	}

	@Override
	public List<SupplierSelect> findSelectObject(Long adminId, Supplier record) {
		User user = userDao.findById(adminId);
		record.setGroupId(user.getGroupId());
		record.setCompanyId(user.getCompanyId());
		return supplierDao.findSelectObject(record);
	}
	
	
}
