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
import com.saas.api.admin.res.select.crm.WarehouseSelect;
import com.saas.api.admin.service.crm.WarehouseService;
import com.saas.api.common.constant.RequestParamConstant;
import com.saas.api.common.constant.ResponseCodeI18n;
import com.saas.api.common.entity.crm.Warehouse;
import com.saas.api.common.util.ApiResultI18n;
import com.saas.api.common.util.ParamUtil;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @Description: 仓库相关接口
 * @Author: 徐未
 * @Date: 2019/12/13
 */
@RestController
@Slf4j
public class WarehouseController {
	
	@Autowired
    private WarehouseService warehouseService;
	
	
	
	/**
     * * 查询发票列表
     * @return @AuthRuleAnnotation("/admin/api/common/unit/getSelectList")
     */
    @PostMapping(value = "/admin/api/crm/warehouse/queryList")
    public ApiResultI18n queryList(@RequestBody String params,
            HttpServletRequest request) {
    	log.debug(params);
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	
    	String lanType = request.getHeader("X-LanType");
    	String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        
        Integer warehouseId = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.WAREHOUSE_ID))){
        	warehouseId = jsonObj.getInt(RequestParamConstant.WAREHOUSE_ID);
        }
        String warehouseCode = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.WAREHOUSE_CODE))){
        	warehouseCode = jsonObj.getString(RequestParamConstant.WAREHOUSE_CODE);
        }
        String warehouseName = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.WAREHOUSE_NAME))){
        	warehouseName = jsonObj.getString(RequestParamConstant.WAREHOUSE_NAME);
        }
        
        
        Map<String, Object> result = warehouseService.getListData(warehouseId, warehouseCode, warehouseName, adminId, 
        		lanType, ParamUtil.initPage(jsonObj));
    	return ApiResultI18n.success(result, lanType);
    }
	
	/**
     * * 查询下拉发票列表
     * @return @AuthRuleAnnotation("/admin/api/common/unit/querySelectList")
     */
    @PostMapping(value = "/admin/api/crm/warehouse/querySelectList")
    public ApiResultI18n querySelectList(@RequestBody String params,
            HttpServletRequest request) {
    	log.debug(params);
    	//JSONObject jsonObj = JSONObject.fromObject(params);
    	String lanType = request.getHeader("X-LanType");
    	String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        
        Warehouse record = new Warehouse();
        record.setLanType(lanType);
        List<WarehouseSelect> result = warehouseService.findSelectObject(adminId, record);
    	return ApiResultI18n.success(result, lanType);
    }
    
    /**
     * * 添加数据
     * @return
     */
    @AuthRuleAnnotation("admin/api/crm/warehouse/addData")
    @PostMapping("/admin/api/crm/warehouse/addData")
    public ApiResultI18n insertData(@RequestBody String params,
            HttpServletRequest request){
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	String lanType = request.getHeader("X-LanType");
    	String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        
    	JSONArray locationList = null;
        if(null != jsonObj.get(RequestParamConstant.WAREHOUSE_LOCATION_LIST)){
        	locationList = jsonObj.getJSONArray(RequestParamConstant.WAREHOUSE_LOCATION_LIST);
        }
        
        jsonObj.remove(RequestParamConstant.WAREHOUSE_LOCATION_LIST);
        Warehouse record = (Warehouse) JSONObject.toBean(jsonObj, Warehouse.class);
    	
    	Warehouse check = warehouseService.findByName(adminId, record.getWarehouseName(), lanType);
    	if(check != null) {
    		return ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_WAREHOUSE_IS_EXIST.getCode(), 
    				ResponseCodeI18n.MODULE_CRM_WAREHOUSE_IS_EXIST.getMessage(), lanType);
    	}
    	
    	record.setAdminId(adminId);
    	record.setLanType(lanType);
    	warehouseService.insertData(record, locationList);
    	
    	return ApiResultI18n.success(lanType);
    }
    
    /**
     * * 添加数据
     * @return
     */
    @AuthRuleAnnotation("admin/api/crm/warehouse/updateData")
    @PostMapping("/admin/api/crm/warehouse/updateData")
    public ApiResultI18n updateData(@RequestBody String params,
            HttpServletRequest request) {
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	String lanType = request.getHeader("X-LanType");
    	String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);

        JSONArray locationList = null;
        if(null != jsonObj.get(RequestParamConstant.WAREHOUSE_LOCATION_LIST)){
        	locationList = jsonObj.getJSONArray(RequestParamConstant.WAREHOUSE_LOCATION_LIST);
        }
        
        jsonObj.remove(RequestParamConstant.WAREHOUSE_LOCATION_LIST);
        Warehouse record = (Warehouse) JSONObject.toBean(jsonObj, Warehouse.class);
    	
        Warehouse check = warehouseService.findByPrimayKey(record.getWarehouseId());
    	if(check== null) {
    		return ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_WAREHOUSE_IS_NOT_EXIST.getCode(), 
    				ResponseCodeI18n.MODULE_CRM_WAREHOUSE_IS_NOT_EXIST.getMessage(), lanType);
    	}
    	
    	Warehouse checkName = warehouseService.findByName(adminId, record.getWarehouseName(), lanType);
    	if(checkName != null) {
    		if(!checkName.getWarehouseId().equals(record.getWarehouseId())) {
    			return ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_WAREHOUSE_IS_EXIST.getCode(), 
        				ResponseCodeI18n.MODULE_CRM_WAREHOUSE_IS_EXIST.getMessage(), lanType);
    		}
    	}
    	
    	record.setAdminId(adminId);
    	warehouseService.updateData(record, locationList);
    	return ApiResultI18n.success(lanType);
    }
    
    /**
     * * 添加数据
     * @return
     */
    @AuthRuleAnnotation("admin/api/crm/warehouse/deleteData")
    @PostMapping("/admin/api/crm/warehouse/deleteData")
    public ApiResultI18n deleteData(@RequestBody String params,
            HttpServletRequest request){
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	
    	String lanType = request.getHeader("X-LanType");
    	
    	Integer warehouseId = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.WAREHOUSE_ID))){
        	warehouseId = jsonObj.getInt(RequestParamConstant.WAREHOUSE_ID);
        }else {
        	return ApiResultI18n.failure(ResponseCodeI18n.PARAM_ERROR.getCode(), 
        			ResponseCodeI18n.PARAM_ERROR.getMessage(),lanType);
        }
        
        warehouseService.deleteByPrimayKey(warehouseId);
        
    	return ApiResultI18n.success(lanType);
    }
    
}
