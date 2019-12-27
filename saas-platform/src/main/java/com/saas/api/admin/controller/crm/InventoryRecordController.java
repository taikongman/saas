package com.saas.api.admin.controller.crm;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.saas.api.admin.annotation.AuthRuleAnnotation;
import com.saas.api.admin.res.select.crm.InventoryRecordSelect;
import com.saas.api.admin.service.crm.InventoryRecordService;
import com.saas.api.common.constant.RequestParamConstant;
import com.saas.api.common.constant.ResponseCodeI18n;
import com.saas.api.common.entity.crm.InventoryRecord;
import com.saas.api.common.util.ApiResultI18n;
import com.saas.api.common.util.ParamUtil;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

/**
 * @Description: 发票相关接口
 * @Author: 徐未
 * @Date: 2019/12/10
 */
@RestController
@Slf4j
public class InventoryRecordController {
	
	@Autowired
    private InventoryRecordService inventoryRecordService;
    
    
    /**
     * * 查询发票列表
     * @return @AuthRuleAnnotation("/admin/api/crm/inventory/getSelectList")
     */
    @PostMapping(value = "/admin/api/crm/inventory/queryList")
    public ApiResultI18n queryInventoryList(@RequestBody String params,
            HttpServletRequest request) {
    	log.debug(params);
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	
    	String lanType = request.getHeader("X-LanType");
    	String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        
        Map<String, Object> paramsMap = JSON.parseObject(params);
        ParamUtil.initMapParam(paramsMap, jsonObj, lanType);
        
        Map<String, Object> result = inventoryRecordService.getDataList(paramsMap, adminId);
    	return ApiResultI18n.success(result, lanType);
    }
    
    /**
     * * 查询发票列表
     * @return @AuthRuleAnnotation("/admin/api/crm/inventory/getSelectList")
     */
    @PostMapping(value = "/admin/api/crm/inventory/querySelectList")
    public ApiResultI18n querySelectList(@RequestBody String params,
            HttpServletRequest request) {
    	log.debug(params);
    	
    	String lanType = request.getHeader("X-LanType");
    	String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        
        List<InventoryRecordSelect> result = inventoryRecordService.findSelectObject(adminId, lanType);
    	return ApiResultI18n.success(result, lanType);
    }
    
    /**
     * * 查询发票列表
     * @return @AuthRuleAnnotation("/admin/api/crm/inventory/getSelectList")
     */
    @PostMapping(value = "/admin/api/crm/inventory/detailData")
    public ApiResultI18n detailData(@RequestBody String params,
            HttpServletRequest request) {
    	log.debug(params);
    	
    	String lanType = request.getHeader("X-LanType");
    	JSONObject jsonObj = JSONObject.fromObject(params);
        Long id = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.ID))){
        	id = jsonObj.getLong(RequestParamConstant.ID);
        }else {
        	return ApiResultI18n.failure(ResponseCodeI18n.PARAM_ERROR.getCode(), 
        			ResponseCodeI18n.PARAM_ERROR.getMessage(),lanType);
        }
        
        InventoryRecord result = inventoryRecordService.findByPrimayKey(id);
    	return ApiResultI18n.success(result, lanType);
    }
    
    /**
     * * 添加数据
     * @return
     */
    @AuthRuleAnnotation("admin/api/crm/inventory/addData")
    @PostMapping("/admin/api/crm/inventory/addData")
    public ApiResultI18n insertInventoryData(@RequestBody String params,
            HttpServletRequest request) {
    	log.debug(params);
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	
    	String lanType = request.getHeader("X-LanType");
    	String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
    	
    	inventoryRecordService.insertDataByJson(jsonObj, adminId, lanType);
    	
    	return ApiResultI18n.success(lanType);
    }
    
    /**
     * * 更新数据
     * @return
     */
    @AuthRuleAnnotation("admin/api/crm/inventory/updateData")
    @PostMapping("/admin/api/crm/inventory/updateData")
    public ApiResultI18n updateInventoryData(@RequestBody String params,
            HttpServletRequest request) {
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	String lanType = request.getHeader("X-LanType");
    	String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);

        inventoryRecordService.updateDataByJson(jsonObj, adminId, lanType);
    	return ApiResultI18n.success(lanType);
    }
    
    /**
     * * 删除数据
     * @return
     */
    @AuthRuleAnnotation("admin/api/crm/inventory/deleteData")
    @PostMapping("/admin/api/crm/inventory/deleteData")
    public ApiResultI18n deleteInventoryData(@RequestBody String params,
            HttpServletRequest request){
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	
    	String lanType = request.getHeader("X-LanType");
    	
    	Long id = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.ID))){
        	id = jsonObj.getLong(RequestParamConstant.ID);
        }else {
        	return ApiResultI18n.failure(ResponseCodeI18n.PARAM_ERROR.getCode(), 
        			ResponseCodeI18n.PARAM_ERROR.getMessage(),lanType);
        }
        
        inventoryRecordService.deleteByStatus(id, lanType);
        
    	return ApiResultI18n.success(lanType);
    }
    
    /**
     * * 添加数据
     * @return
     */
    @AuthRuleAnnotation("admin/api/crm/inventory/updateLocation")
    @PostMapping("/admin/api/crm/inventory/updateLocation")
    public ApiResultI18n updateLocation(@RequestBody String params,
            HttpServletRequest request) {
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	String lanType = request.getHeader("X-LanType");
//    	String xAdminId = request.getHeader("X-AdminId");
//        Long adminId = Long.valueOf(xAdminId);
        
        Long id = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.ID))){
        	id = jsonObj.getLong(RequestParamConstant.ID);
        }
        
        Long locationId = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.LOCATION_ID))){
        	locationId = jsonObj.getLong(RequestParamConstant.LOCATION_ID);
        }
        

        inventoryRecordService.updateLocation(id, locationId, lanType);
    	return ApiResultI18n.success(lanType);
    }
    
    
    @AuthRuleAnnotation("")
    @PostMapping("/admin/api/crm/commodity/defaultMaterialList")
    public ApiResultI18n defaultMaterialList(@RequestBody String params,
            HttpServletRequest request) {
        JSONObject jsonObj = JSONObject.fromObject(params);
        String lanType = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.LAN_TYPE))){
        	lanType = jsonObj.getString(RequestParamConstant.LAN_TYPE);
        }
        
        String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        
        List<InventoryRecord> result = inventoryRecordService.defaultMaterialList(adminId, lanType);
        return ApiResultI18n.success(result, lanType);
    }
    
    @AuthRuleAnnotation("")
    @PostMapping("/admin/api/crm/commodity/commodityList4OrCond")
    public ApiResultI18n commodityList4OrCond(@RequestBody String params,
            HttpServletRequest request) {
        JSONObject jsonObj = JSONObject.fromObject(params);
        String lanType = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.LAN_TYPE))){
        	lanType = jsonObj.getString(RequestParamConstant.LAN_TYPE);
        }
        
        String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        
        String searchCond = jsonObj.getString(RequestParamConstant.SEARCH_COND);
        Map<String, Object> result = inventoryRecordService.commodityList4OrCond(adminId, lanType, searchCond, ParamUtil.initPage(jsonObj));
        return ApiResultI18n.success(result, lanType);
    }
}
