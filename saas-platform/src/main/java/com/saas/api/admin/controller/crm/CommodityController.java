package com.saas.api.admin.controller.crm;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.saas.api.admin.annotation.AuthRuleAnnotation;
import com.saas.api.admin.service.crm.CommodityService;
import com.saas.api.common.constant.RequestParamConstant;
import com.saas.api.common.constant.ResponseCodeI18n;
import com.saas.api.common.entity.crm.Commodity;
import com.saas.api.common.util.ApiResultI18n;
import com.saas.api.common.util.ParamUtil;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @Description: 发票相关接口
 * @Author: 徐未
 * @Date: 2019/12/10
 */
@RestController
@Slf4j
public class CommodityController {
	
	@Autowired
    private CommodityService commodityService;
	
	
	
	/**
     * * 查询发票列表
     * @return @AuthRuleAnnotation("/admin/api/crm/commodity/getSelectList")
     */
    @PostMapping(value = "/admin/api/crm/commodity/queryList")
    public ApiResultI18n queryList(@RequestBody String params,
            HttpServletRequest request) {
    	log.debug(params);
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	
    	String lanType = request.getHeader("X-LanType");
    	String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        
        String commodityCode = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.COMMODITY_CODE))){
        	commodityCode = jsonObj.getString(RequestParamConstant.COMMODITY_CODE);
        }
        
        String commodityName = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.COMMODITY_NAME))){
        	commodityName = jsonObj.getString(RequestParamConstant.COMMODITY_NAME);
        }
        
        Integer categoryId = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.CATEGORY_ID))){
        	categoryId = jsonObj.getInt(RequestParamConstant.CATEGORY_ID);
        }
        
        Map<String, Object> result = commodityService.commodityList(commodityCode, commodityName, categoryId, 
        		adminId, lanType, ParamUtil.initPage(jsonObj));
        
    	return ApiResultI18n.success(result, lanType);
    }
	
	/**
     * * 查询下拉发票列表
     * @return @AuthRuleAnnotation("/admin/api/crm/commodity/querySelectList")
     */
    @PostMapping(value = "/admin/api/crm/commodity/querySelectList")
    public ApiResultI18n querySelectList(@RequestBody String params,
            HttpServletRequest request) {
    	log.debug(params);
    	//JSONObject jsonObj = JSONObject.fromObject(params);
    	String lanType = request.getHeader("X-LanType");
    	String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        
        
        List<Commodity> result = commodityService.findBySelectObject(adminId, lanType);
    	return ApiResultI18n.success(result, lanType);
    }
    
    /**
     * * 添加数据
     * @return
     */
    @AuthRuleAnnotation("admin/api/crm/commodity/addData")
    @PostMapping("/admin/api/crm/commodity/addData")
    public ApiResultI18n insertData(@RequestBody String params,
            HttpServletRequest request){
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	String lanType = request.getHeader("X-LanType");
    	String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        
    	JSONArray commodityAutoList = null;
        if(null != jsonObj.get(RequestParamConstant.COMMODITY_AUTO_LIST)){
        	commodityAutoList = jsonObj.getJSONArray(RequestParamConstant.COMMODITY_AUTO_LIST);
        }
        
        jsonObj.remove(RequestParamConstant.COMMODITY_AUTO_LIST);
        
        Commodity record = (Commodity) JSONObject.toBean(jsonObj, Commodity.class);
        
        Commodity check = commodityService.findByCommodityName(adminId, record.getCommodityName());
    	if(check != null) {
    		return ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_COMMODITY_IS_EXIST.getCode(), 
    				ResponseCodeI18n.MODULE_CRM_COMMODITY_IS_EXIST.getMessage(), lanType);
    	}
    	
    	record.setAdminId(adminId);
    	record.setLanType(lanType);
    	commodityService.addCommodity(record, commodityAutoList);
    	
    	return ApiResultI18n.success(lanType);
    }
    
    /**
     * * 添加数据
     * @return
     */
    @AuthRuleAnnotation("admin/api/crm/commodity/updateData")
    @PostMapping("/admin/api/crm/commodity/updateData")
    public ApiResultI18n updateData(@RequestBody String params,
            HttpServletRequest request){
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	String lanType = request.getHeader("X-LanType");
    	String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);

        JSONArray commodityAutoList = null;
        if(null != jsonObj.get(RequestParamConstant.COMMODITY_AUTO_LIST)){
        	commodityAutoList = jsonObj.getJSONArray(RequestParamConstant.COMMODITY_AUTO_LIST);
        }
        
        jsonObj.remove(RequestParamConstant.COMMODITY_AUTO_LIST);
        Commodity record = (Commodity) JSONObject.toBean(jsonObj, Commodity.class);
        
        Commodity check = commodityService.selectByPrimaryKey(record.getCommodityId());
        if(check == null) {
        	return ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_COMMODITY_IS_NOT_EXIST.getCode(), 
    				ResponseCodeI18n.MODULE_CRM_COMMODITY_IS_NOT_EXIST.getMessage(), lanType);
        }
        
        Commodity checkName = commodityService.findByCommodityName(adminId, record.getCommodityName());
    	if(checkName != null) {
    		if(!checkName.getCommodityId().equals(record.getCommodityId())) {
    			return ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_COMMODITY_IS_EXIST.getCode(), 
        				ResponseCodeI18n.MODULE_CRM_COMMODITY_IS_EXIST.getMessage(), lanType);
    		}
    	}
    	
    	record.setLanType(lanType);
    	record.setAdminId(adminId);
    	commodityService.modifyCommodity(record, commodityAutoList);
    	return ApiResultI18n.success(lanType);
    }
    
    /**
     * * 添加数据
     * @return
     */
    @AuthRuleAnnotation("admin/api/crm/commodity/deleteData")
    @PostMapping("/admin/api/crm/commodity/deleteData")
    public ApiResultI18n deleteData(@RequestBody String params,
            HttpServletRequest request){
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	
    	String lanType = request.getHeader("X-LanType");
    	
    	Long commodityId = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.COMMODITY_ID))){
        	commodityId = jsonObj.getLong(RequestParamConstant.COMMODITY_ID);
        }else {
        	return ApiResultI18n.failure(ResponseCodeI18n.PARAM_ERROR.getCode(), 
        			ResponseCodeI18n.PARAM_ERROR.getMessage(),lanType);
        }
        
        commodityService.delCommodity(commodityId);;
        
    	return ApiResultI18n.success(lanType);
    }
    
    
    
}
