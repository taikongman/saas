package com.saas.api.admin.service.crm.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.saas.api.admin.res.crm.CommodityResponse;
import com.saas.api.admin.service.crm.CommodityService;
import com.saas.api.common.constant.CommonConstant;
import com.saas.api.common.constant.DbConstant;
import com.saas.api.common.constant.RequestParamConstant;
import com.saas.api.common.constant.ResponseCodeI18n;
import com.saas.api.common.dao.common.AreaDao;
import com.saas.api.common.dao.common.AutoBrandDao;
import com.saas.api.common.dao.common.CityDao;
import com.saas.api.common.dao.common.ProvinceDao;
import com.saas.api.common.dao.crm.CommodityAutoDao;
import com.saas.api.common.dao.crm.CommodityDao;
import com.saas.api.common.dao.sys.auth.UserDao;
import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.common.Area;
import com.saas.api.common.entity.common.AutoBrand;
import com.saas.api.common.entity.common.City;
import com.saas.api.common.entity.common.Province;
import com.saas.api.common.entity.crm.Commodity;
import com.saas.api.common.entity.crm.CommodityAuto;
import com.saas.api.common.entity.sys.auth.User;
import com.saas.api.common.exception.SaasException;
import com.saas.api.common.util.ApiResultI18n;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Service
public class CommodityServiceImpl implements CommodityService {
	
	@Resource
    private CommodityDao commodityDao;
	
	@Resource
    private CommodityAutoDao commodityAutoDao;
	
	@Resource
    private UserDao userDao;
	
	@Resource
    private AutoBrandDao autoBrandDao;
	
	@Resource
    private ProvinceDao provinceDao;
    
    @Resource
    private CityDao cityDao;
    
    @Resource
    private AreaDao areaDao;

	@Override
	public Integer countByCode(Integer groupId, Integer companyId, Integer departmentId, Long adminId,
			String lanType, String commodityCode) {
		
		return commodityDao.countByCode(groupId, companyId, null, null, lanType, commodityCode);
	}
	
	
	
	@Override
	public List<Commodity> findBySelectObject(Long adminId, String lanType) {
		Commodity record = new Commodity();
		User user = userDao.findById(adminId);
		record.setGroupId(user.getGroupId());
		record.setCompanyId(user.getCompanyId());
		record.setLanType(lanType);
		record.setStatus(DbConstant.STATUS_ONE);
		return commodityDao.findByObject(record);
	}



	@Override
	public List<Commodity> findByObject(Commodity record) {
		User user = userDao.findById(record.getAdminId());
		record.setGroupId(user.getGroupId());
		record.setCompanyId(user.getCompanyId());
		record.setAdminId(null);
		record.setStatus(DbConstant.STATUS_ONE);
		return commodityDao.findByObject(record);
	}

	@Override
	public Commodity findByCommodityName(Long adminId, String commodityName) {
		User user = userDao.findById(adminId);
		return commodityDao.findByCommodityName(user.getGroupId(), user.getDepartmentId(), null, null, commodityName);
	}

	@Override
	public Commodity selectByPrimaryKey(Long commodityId) {
		return commodityDao.selectByPrimaryKey(commodityId);
	}

	@Override
    public Map<String, Object> commodityList(String commodityCode, String commodityName, Integer categoryId, Long adminId, String lanType, Page page) {
    	User user = userDao.findById(adminId);
    	List<Commodity> commodityList = commodityDao.selectByCond(user.getGroupId(), user.getCompanyId(), 
        		null, null, categoryId, commodityCode, commodityName, null,
        		DbConstant.COMM_STATUS_NORMAL, lanType, page.getStartIndex(), page.getPageSize());
        
        int total = commodityDao.countByCond(user.getGroupId(), user.getCompanyId(), 
        		null, null, categoryId, 
        		commodityCode, commodityName, null, DbConstant.COMM_STATUS_NORMAL, lanType);
        
        List<CommodityResponse> dataList = new ArrayList<CommodityResponse>();
        for(Commodity forTemp : commodityList) {
        	CommodityResponse commodityResponse = new CommodityResponse();
        	BeanUtils.copyProperties(forTemp, commodityResponse);
        	
        	List<CommodityAuto> commodityAutoList = commodityAutoDao.findByCommodityId(commodityResponse.getCommodityId());
        	commodityResponse.setCommodityAutoList(commodityAutoList);
        	dataList.add(commodityResponse);
        }
        
        Map<String, Object> result = new HashMap<>();
        result.put("dataList", dataList);
        result.put("total", total);
        return result;
    }

    @Override
    public void addCommodity(Commodity commodity, JSONArray commodityAutoList) {
    	if(!StringUtils.isEmpty(commodity.getProvinceId())) {
        	Province province = provinceDao.findById(commodity.getProvinceId());
        	if(province != null) {
        		commodity.setProvince(province.getProvinceName());
        	}
        }
        if(!StringUtils.isEmpty(commodity.getCityId())) {
        	City city = cityDao.findById(commodity.getCityId());
        	if(city != null) {
        		commodity.setCity(city.getCityName());
        	}
        }
        if(!StringUtils.isEmpty(commodity.getAreaId())) {
        	Area area = areaDao.findById(commodity.getAreaId());
        	if(area != null) {
        		commodity.setArea(area.getAreaName());
        	}
        }
        if(!StringUtils.isEmpty(commodity.getProvinceId()) 
        		&& !StringUtils.isEmpty(commodity.getCityId())
        		&& !StringUtils.isEmpty(commodity.getAreaId())
        		&& !StringUtils.isEmpty(commodity.getAddress())) {
        	commodity.setFullAddress(new StringBuffer(commodity.getProvince()).append(commodity.getCity())
					.append(commodity.getArea()).append(commodity.getAddress()).toString());
        }
        
        if(commodity.getCostHasTax().equals(CommonConstant.INT_ONE)) {
        	if(commodity.getTaxRatio().intValue() > CommonConstant.INT_ZERO.intValue()) {
        		BigDecimal taxPrice = commodity.getCostPrice().divide(new BigDecimal(100 + commodity.getTaxRatio().intValue()),2);
        		commodity.setTaxPrice(taxPrice);
        		commodity.setTaxAmount(commodity.getCostPrice().subtract(taxPrice));
        	}else {
        		commodity.setTaxPrice(commodity.getCostPrice());
        		commodity.setTaxAmount(BigDecimal.ZERO);
        	}
        }
        if(commodity.getAdjustRatio().intValue() > CommonConstant.INT_ZERO.intValue()) {
			commodity.setSalePrice(
					commodity.getCostPrice().multiply(new BigDecimal(100 + commodity.getAdjustRatio().intValue()))
							.divide(new BigDecimal(100), 2));
        }
        commodity.setAmount(commodity.getCostPrice().multiply(new BigDecimal(commodity.getQuantity())));
        
        commodity.setCreateTime(new Date());
        commodity.setStatus(DbConstant.STATUS_ONE);
        commodity.setVersion(DbConstant.INIT_VERSION);
        commodityDao.insert(commodity);
        
        if (null != commodityAutoList && commodityAutoList.size() > 0) {
        	CommodityAuto commodityAuto = null;
            for (int i = 0; i < commodityAutoList.size(); i++) {
            	commodityAuto = new CommodityAuto();
            	JSONObject jsonObject = commodityAutoList.getJSONObject(i);
            	Integer brandId = jsonObject.getInt(RequestParamConstant.BRAND_ID);
                AutoBrand autoBrand = autoBrandDao.findById(brandId);		
            	commodityAuto.setBrandId(brandId);
            	commodityAuto.setBrandName(autoBrand.getBrandName());
            	commodityAuto.setCommodityId(commodity.getCommodityId());
            	commodityAuto.setCreateTime(new Date());
            	commodityAutoDao.insert(commodityAuto);
            }
        }
    }

    @Override
    public void delCommodity(Long commodityId) {
        Commodity commodity = commodityDao.selectByPrimaryKey(commodityId);
        commodity.setStatus(DbConstant.COMM_STATUS_DEL);
        commodity.setUpdateTime(new Date());
        commodityDao.updateByPrimaryKeySelective(commodity);
    }

    @Override
    public void modifyCommodity(Commodity commodity, JSONArray commodityAutoList) {
    	User user = userDao.findById(commodity.getAdminId());
    	String lanType = commodity.getLanType();
        int existCount = commodityDao.countByCode(user.getGroupId(), user.getCompanyId(), user.getDepartmentId(), 
        		user.getAdminId(), lanType, 
        		commodity.getCommodityCode());
        if (existCount > CommonConstant.INT_ONE) {
        	ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_ORDER_COMMODITY_CODE_IS_EXIST.getCode(), 
    				ResponseCodeI18n.MODULE_CRM_ORDER_COMMODITY_CODE_IS_EXIST.getMessage(), lanType);
            throw new SaasException(exception18n.getCode(), exception18n.getMessage());
        } else {
            Commodity existCommodity = commodityDao.selectByCode(user.getGroupId(), user.getCompanyId(), user.getDepartmentId(), 
            		commodity.getAdminId(), lanType, commodity.getCommodityCode());
            if (null != existCommodity && !existCommodity.getCommodityId().equals(commodity.getCommodityId())) {
            	ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_ORDER_COMMODITY_CODE_IS_EXIST.getCode(), 
        				ResponseCodeI18n.MODULE_CRM_ORDER_COMMODITY_CODE_IS_EXIST.getMessage(), lanType);
                throw new SaasException(exception18n.getCode(), exception18n.getMessage());
            }
        }
        
        if(!StringUtils.isEmpty(commodity.getProvinceId())) {
        	Province province = provinceDao.findById(commodity.getProvinceId());
        	if(province != null) {
        		commodity.setProvince(province.getProvinceName());
        	}
        }
        if(!StringUtils.isEmpty(commodity.getCityId())) {
        	City city = cityDao.findById(commodity.getCityId());
        	if(city != null) {
        		commodity.setCity(city.getCityName());
        	}
        }
        if(!StringUtils.isEmpty(commodity.getAreaId())) {
        	Area area = areaDao.findById(commodity.getAreaId());
        	if(area != null) {
        		commodity.setArea(area.getAreaName());
        	}
        }
        if(!StringUtils.isEmpty(commodity.getProvinceId()) 
        		&& !StringUtils.isEmpty(commodity.getCityId())
        		&& !StringUtils.isEmpty(commodity.getAreaId())
        		&& !StringUtils.isEmpty(commodity.getAddress())) {
        	commodity.setFullAddress(new StringBuffer(commodity.getProvince()).append(commodity.getCity())
					.append(commodity.getArea()).append(commodity.getAddress()).toString());
        }
        
        if(commodity.getCostHasTax().equals(CommonConstant.INT_ONE)) {
        	if(commodity.getTaxRatio().intValue() > CommonConstant.INT_ZERO.intValue()) {
        		BigDecimal taxPrice = commodity.getCostPrice().divide(new BigDecimal(100 + commodity.getTaxRatio().intValue()),2);
        		commodity.setTaxPrice(taxPrice);
        		commodity.setTaxAmount(commodity.getCostPrice().subtract(taxPrice));
        	}else {
        		commodity.setTaxPrice(commodity.getCostPrice());
        		commodity.setTaxAmount(BigDecimal.ZERO);
        	}
        }
        if(commodity.getAdjustRatio().intValue() > CommonConstant.INT_ZERO.intValue()) {
			commodity.setSalePrice(
					commodity.getCostPrice().multiply(new BigDecimal(100 + commodity.getAdjustRatio().intValue()))
							.divide(new BigDecimal(100), 2));
        }
        commodity.setAmount(commodity.getCostPrice().multiply(new BigDecimal(commodity.getQuantity())));
        
        commodity.setUpdateTime(new Date());
        commodityDao.updateByPrimaryKeySelective(commodity);
        
        if (null != commodityAutoList && commodityAutoList.size() > 0) {
        	CommodityAuto commodityAuto = null;
        	commodityAutoDao.deleteByCommodityId(commodity.getCommodityId());
            for (int i = 0; i < commodityAutoList.size(); i++) {
            	commodityAuto = new CommodityAuto();
            	JSONObject jsonObject = commodityAutoList.getJSONObject(i);
            	Integer brandId = jsonObject.getInt(RequestParamConstant.BRAND_ID);
            	Long id = null;
            	if (!StringUtils.isEmpty(jsonObject.get(RequestParamConstant.ID))){
            		id = jsonObject.getLong(RequestParamConstant.ID);
                }
            	
                AutoBrand autoBrand = autoBrandDao.findById(brandId);	
                commodityAuto.setId(id);
            	commodityAuto.setBrandId(brandId);
            	commodityAuto.setBrandName(autoBrand.getBrandName());
            	commodityAuto.setCreateTime(new Date());
        		commodityAuto.setCommodityId(commodity.getCommodityId());
            	commodityAutoDao.insert(commodityAuto);
            }
        }
    }

}
