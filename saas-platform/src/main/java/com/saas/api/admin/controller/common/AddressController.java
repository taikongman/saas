package com.saas.api.admin.controller.common;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.saas.api.admin.res.select.common.AreaSelect;
import com.saas.api.admin.res.select.common.CityAll;
import com.saas.api.admin.res.select.common.CitySelect;
import com.saas.api.admin.res.select.common.ProvinceAll;
import com.saas.api.admin.res.select.common.ProvinceSelect;
import com.saas.api.admin.service.common.AddressService;
import com.saas.api.common.constant.CommonConstant;
import com.saas.api.common.constant.RequestParamConstant;
import com.saas.api.common.entity.common.Area;
import com.saas.api.common.entity.common.City;
import com.saas.api.common.entity.common.Province;
import com.saas.api.common.util.ApiResultI18n;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

/**
 * @Description: 公司相关接口
 * @Author: 徐未
 * @Date: 2019/11/12
 */
@RestController
@Slf4j
public class AddressController {

	@Autowired
    private AddressService addressService;
    
	/**
     * * 查询省份列表
     * @return @AuthRuleAnnotation("admin/system/company/queryList")
     */
    @PostMapping(value = "/admin/api/common/province/getSelectList")
    public ApiResultI18n querySelectAutoBrandList(@RequestBody String params,
            HttpServletRequest request) {
    	log.debug(params);
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	String lanType = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.LAN_TYPE))){
        	lanType = jsonObj.getString(RequestParamConstant.LAN_TYPE);
        }
        
        String provinceId = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.PROVINCE_ID))){
        	provinceId = jsonObj.getString(RequestParamConstant.PROVINCE_ID);
        }
        
        Province record = new Province();
        record.setProvinceId(provinceId);
        record.setFlag(CommonConstant.INT_ONE);
        
        List<ProvinceSelect> result = addressService.getSelectProvinceList(record);
    	return ApiResultI18n.success(result, lanType);
    }
    
    /**
     * * 查询城市列表
     * @return @AuthRuleAnnotation("admin/system/company/queryList")
     */
    @PostMapping(value = "/admin/api/common/city/getSelectList")
    public ApiResultI18n querySelectCityList(@RequestBody String params,
            HttpServletRequest request) {
    	log.debug(params);
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	String lanType = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.LAN_TYPE))){
        	lanType = jsonObj.getString(RequestParamConstant.LAN_TYPE);
        }
        
        String provinceId = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.PROVINCE_ID))){
        	provinceId = jsonObj.getString(RequestParamConstant.PROVINCE_ID);
        }
        String cityId = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.CITY_ID))){
        	cityId = jsonObj.getString(RequestParamConstant.CITY_ID);
        }
        
        City record = new City();
        record.setProvinceId(provinceId);
        record.setCityId(cityId);
        
        List<CitySelect> result = addressService.querySelectCityList(record);
    	return ApiResultI18n.success(result, lanType);
    }
    
    /**
     * * 查询城市车牌号码前两问列表
     * @return @AuthRuleAnnotation("admin/system/company/queryList")
     */
    @PostMapping(value = "/admin/api/common/city/getSelectAutoCodeList")
    public ApiResultI18n querySelectAutoCodeList(@RequestBody String params,
            HttpServletRequest request) {
    	log.debug(params);
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	String lanType = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.LAN_TYPE))){
        	lanType = jsonObj.getString(RequestParamConstant.LAN_TYPE);
        }
        
        List<String> result = addressService.queryAllAutoCodes();
    	return ApiResultI18n.success(result, lanType);
    }
    
    
    /**
     * * 查询区县列表
     * @return @AuthRuleAnnotation("admin/system/company/queryList")
     */
    @PostMapping(value = "/admin/api/common/area/getSelectList")
    public ApiResultI18n querySelectAreaList(@RequestBody String params,
            HttpServletRequest request) {
    	log.debug(params);
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	String lanType = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.LAN_TYPE))){
        	lanType = jsonObj.getString(RequestParamConstant.LAN_TYPE);
        }
        
        String cityId = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.CITY_ID))){
        	cityId = jsonObj.getString(RequestParamConstant.CITY_ID);
        }
        String areaId = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.AREA_ID))){
        	areaId = jsonObj.getString(RequestParamConstant.AREA_ID);
        }
        
        Area record = new Area();
        record.setCityId(cityId);
        record.setAreaId(areaId);
        
        List<AreaSelect> result = addressService.querySelectAreaList(record);
    	return ApiResultI18n.success(result, lanType);
    }
    
    /**
     * * 查询所有省份城市区县列表
     * @return @AuthRuleAnnotation("admin/system/company/queryList")
     */
    @PostMapping(value = "/admin/api/common/address/getSelectList")
    public ApiResultI18n querySelectAddressList(@RequestBody String params,
            HttpServletRequest request) {
    	log.debug(params);
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	String lanType = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.LAN_TYPE))){
        	lanType = jsonObj.getString(RequestParamConstant.LAN_TYPE);
        }
        
        Province record = new Province();
        record.setFlag(CommonConstant.INT_ONE);
        List<ProvinceSelect> provinceList = addressService.getSelectProvinceList(record);
        List<ProvinceAll> result = new ArrayList<ProvinceAll>();
        City city = null;
        for(ProvinceSelect forProvince : provinceList) {
        	ProvinceAll provinceAll = new ProvinceAll();
        	provinceAll.setProvinceId(forProvince.getProvinceId());
        	provinceAll.setProvinceName(forProvince.getProvinceName());
        	city = new City();
        	city.setProvinceId(forProvince.getProvinceId());
            
            List<CitySelect> cityList = addressService.querySelectCityList(city);
            List<CityAll> cityAllList = new ArrayList<CityAll>();
            for(CitySelect forCity : cityList) {
            	CityAll cityAll = new CityAll();
            	cityAll.setCityId(forCity.getCityId());
            	cityAll.setCityName(forCity.getCityName());
            	
            	Area area = new Area();
            	area.setCityId(forCity.getCityId());
            	List<AreaSelect> areaList = addressService.querySelectAreaList(area);
            	cityAll.setAreaList(areaList);
            	cityAllList.add(cityAll);
            }
            
            provinceAll.setCityList(cityAllList);
            
            result.add(provinceAll);
        }
        
    	return ApiResultI18n.success(result, lanType);
    }

    
}
