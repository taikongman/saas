package com.saas.api.admin.service.crm.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.saas.api.admin.service.crm.SupplierBankService;
import com.saas.api.common.constant.DbConstant;
import com.saas.api.common.dao.common.AreaDao;
import com.saas.api.common.dao.common.CityDao;
import com.saas.api.common.dao.common.ProvinceDao;
import com.saas.api.common.dao.crm.SupplierBankDao;
import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.common.Area;
import com.saas.api.common.entity.common.City;
import com.saas.api.common.entity.common.Province;
import com.saas.api.common.entity.crm.SupplierBank;

@Service
public class SupplierBankServiceImpl implements SupplierBankService {

	
	@Resource
    private SupplierBankDao supplierBankDao;
	
	@Resource
    private ProvinceDao provinceDao;
    
    @Resource
    private CityDao cityDao;
    
    @Resource
    private AreaDao areaDao;
    
    
	@Override
	public SupplierBank findByPrimayKey(Integer id) {
		return supplierBankDao.selectByPrimaryKey(id);
	}

	@Override
	public SupplierBank findByBankNo(Integer supplierId, String bankNo) {
		return supplierBankDao.findByBankNo(supplierId, bankNo);
	}

	@Override
	public int insertData(SupplierBank record) {
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
		return supplierBankDao.insert(record);
	}

	@Override
	public int updateData(SupplierBank record) {
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
		record.setUpdateTime(new Date());
		return supplierBankDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int deleteByPrimayKey(Integer id) {
		return supplierBankDao.deleteByPrimaryKey(id);
	}
	
	@Override
	public int deleteBySupplierId(Integer supplierId) {
		return supplierBankDao.deleteBySupplierId(supplierId);
	}

	@Override
	public Map<String, Object> getListData(Integer supplierId, String accountName, 
			String taxNo, String bankNo, String phone, 
			Page page) {
		
		List<SupplierBank> resultList = supplierBankDao.listData(supplierId, accountName, taxNo, bankNo, phone, 
				DbConstant.STATUS_ONE,
				page.getStartIndex(), page.getPageSize());
		
		Integer total = supplierBankDao.countData(supplierId, accountName, taxNo, bankNo, phone, 
				DbConstant.STATUS_ONE);
		
		Map<String, Object> result = new HashMap<>();
        result.put("dataList", resultList);
        result.put("total", total);
        return result;
	}

	@Override
	public List<SupplierBank> findByObject(SupplierBank record) {
		return supplierBankDao.findByObject(record);
	}

}
