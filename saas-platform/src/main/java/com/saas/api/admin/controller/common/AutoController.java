package com.saas.api.admin.controller.common;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.saas.api.admin.res.select.common.AutoBrandSelect;
import com.saas.api.admin.res.select.common.AutoModelSelect;
import com.saas.api.admin.res.select.common.AutoSeriesSelect;
import com.saas.api.admin.service.common.AutoService;
import com.saas.api.common.constant.RequestParamConstant;
import com.saas.api.common.entity.common.AutoBrand;
import com.saas.api.common.entity.common.AutoModel;
import com.saas.api.common.entity.common.AutoSeries;
import com.saas.api.common.util.ApiResultI18n;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

/**
 * @Description: 汽车相关接口
 * @Author: 徐未
 * @Date: 2019/12/04
 */
@RestController
@Slf4j
public class AutoController {
	
	@Autowired
    private AutoService autoService;
	
	
	/**
     * * 查询汽车品牌列表
     * @return @AuthRuleAnnotation("admin/system/company/queryList")
     */
    @PostMapping(value = "/admin/api/common/autobrand/getSelectList")
    public ApiResultI18n querySelectAutoBrandList(@RequestBody String params,
            HttpServletRequest request) {
    	log.debug(params);
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	String lanType = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.LAN_TYPE))){
        	lanType = jsonObj.getString(RequestParamConstant.LAN_TYPE);
        }
        
        Integer brandId = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.BRAND_ID))){
        	brandId = jsonObj.getInt(RequestParamConstant.BRAND_ID);
        }
        
        AutoBrand record = new AutoBrand();
        record.setBrandId(brandId);
        record.setLanType(lanType);
        
        List<AutoBrandSelect> result = autoService.getSelectAutoBrandList(record);
    	return ApiResultI18n.success(result, lanType);
    }
    
    /**
     * * 查询汽车系列列表
     * @return @AuthRuleAnnotation("admin/system/company/queryList")
     */
    @PostMapping(value = "/admin/api/common/autoseries/getSelectList")
    public ApiResultI18n querySelectAutoSeriesList(@RequestBody String params,
            HttpServletRequest request) {
    	log.debug(params);
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	String lanType = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.LAN_TYPE))){
        	lanType = jsonObj.getString(RequestParamConstant.LAN_TYPE);
        }
        
        Integer brandId = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.BRAND_ID))){
        	brandId = jsonObj.getInt(RequestParamConstant.BRAND_ID);
        }
        
        Integer seriesId = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.SERIES_ID))){
        	seriesId = jsonObj.getInt(RequestParamConstant.SERIES_ID);
        }
        
        AutoSeries record = new AutoSeries();
        record.setBrandId(brandId);
        record.setSeriesId(seriesId);
        record.setLanType(lanType);
        
        List<AutoSeriesSelect> result = autoService.getSelectAutoSeriesList(record);
    	return ApiResultI18n.success(result, lanType);
    }
    
    /**
     * * 查询汽车年份列表
     * @return @AuthRuleAnnotation("admin/system/company/queryList")
     */
    @PostMapping(value = "/admin/api/common/automodel/getSelectYearList")
    public ApiResultI18n querySelectAutoModelYearList(@RequestBody String params,
            HttpServletRequest request) {
    	log.debug(params);
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	String lanType = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.LAN_TYPE))){
        	lanType = jsonObj.getString(RequestParamConstant.LAN_TYPE);
        }
        
        Integer brandId = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.BRAND_ID))){
        	brandId = jsonObj.getInt(RequestParamConstant.BRAND_ID);
        }
        
        Integer seriesId = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.SERIES_ID))){
        	seriesId = jsonObj.getInt(RequestParamConstant.SERIES_ID);
        }
        
        Integer modelId = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.MODEL_ID))){
        	modelId = jsonObj.getInt(RequestParamConstant.MODEL_ID);
        }
        
        AutoModel record = new AutoModel();
        record.setBrandId(brandId);
        record.setSeriesId(seriesId);
        record.setModelId(modelId);
        record.setLanType(lanType);
        
        List<Integer> result = autoService.getSelectAutoYear(record);
    	return ApiResultI18n.success(result, lanType);
    }
    
    /**
     * * 查询汽车型号列表
     * @return @AuthRuleAnnotation("admin/system/company/queryList")
     */
    @PostMapping(value = "/admin/api/common/automodel/getSelectList")
    public ApiResultI18n querySelectAutoModelList(@RequestBody String params,
            HttpServletRequest request) {
    	log.debug(params);
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	String lanType = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.LAN_TYPE))){
        	lanType = jsonObj.getString(RequestParamConstant.LAN_TYPE);
        }
        
        Integer brandId = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.BRAND_ID))){
        	brandId = jsonObj.getInt(RequestParamConstant.BRAND_ID);
        }
        
        Integer seriesId = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.SERIES_ID))){
        	seriesId = jsonObj.getInt(RequestParamConstant.SERIES_ID);
        }
        
        AutoModel record = new AutoModel();
        record.setBrandId(brandId);
        record.setSeriesId(seriesId);
        record.setLanType(lanType);
        
        List<AutoModelSelect> result = autoService.getSelectAutoModelList(record);
    	return ApiResultI18n.success(result, lanType);
    }
    
    
    
}
