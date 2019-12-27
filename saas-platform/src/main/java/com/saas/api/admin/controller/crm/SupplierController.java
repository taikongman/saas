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
import com.saas.api.admin.res.select.crm.SupplierSelect;
import com.saas.api.admin.service.crm.SupplierBankService;
import com.saas.api.admin.service.crm.SupplierService;
import com.saas.api.common.constant.RequestParamConstant;
import com.saas.api.common.constant.ResponseCodeI18n;
import com.saas.api.common.entity.crm.Supplier;
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
public class SupplierController {
	
	@Autowired
    private SupplierService supplierService;
	
	@Autowired
    private SupplierBankService supplierBankService;
	
	
	/**
     * * 查询发票列表
     * @return @AuthRuleAnnotation("/admin/api/common/unit/getSelectList")
     */
    @PostMapping(value = "/admin/api/crm/supplier/queryList")
    public ApiResultI18n queryList(@RequestBody String params,
            HttpServletRequest request) {
    	log.debug(params);
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	
    	String lanType = request.getHeader("X-LanType");
    	String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        
        Integer supplierId = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.SUPPLIER_ID))){
        	supplierId = jsonObj.getInt(RequestParamConstant.SUPPLIER_ID);
        }
        
        String supplierName = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.SUPPLIER_NAME))){
        	supplierName = jsonObj.getString(RequestParamConstant.SUPPLIER_NAME);
        }
        
        
        Map<String, Object> result = supplierService.getListData(supplierId, supplierName, adminId, 
        		lanType, ParamUtil.initPage(jsonObj));
    	return ApiResultI18n.success(result, lanType);
    }
	
	/**
     * * 查询下拉发票列表
     * @return @AuthRuleAnnotation("/admin/api/common/unit/querySelectList")
     */
    @PostMapping(value = "/admin/api/crm/supplier/querySelectList")
    public ApiResultI18n querySelectList(@RequestBody String params,
            HttpServletRequest request) {
    	log.debug(params);
    	//JSONObject jsonObj = JSONObject.fromObject(params);
    	String lanType = request.getHeader("X-LanType");
    	String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        
        Supplier record = new Supplier();
        record.setLanType(lanType);
        List<SupplierSelect> result = supplierService.findSelectObject(adminId, record);
    	return ApiResultI18n.success(result, lanType);
    }
    
    /**
     * * 添加数据
     * @return
     */
    @AuthRuleAnnotation("admin/api/crm/supplier/addData")
    @PostMapping("/admin/api/crm/supplier/addData")
    public ApiResultI18n insertData(@RequestBody String params,
            HttpServletRequest request){
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	String lanType = request.getHeader("X-LanType");
    	String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        
    	JSONArray supplierBankList = null;
        if(null != jsonObj.get(RequestParamConstant.SUPPLIER_BANK_LIST)){
        	supplierBankList = jsonObj.getJSONArray(RequestParamConstant.SUPPLIER_BANK_LIST);
        }
        
        jsonObj.remove(RequestParamConstant.SUPPLIER_BANK_LIST);
    	Supplier record = (Supplier) JSONObject.toBean(jsonObj, Supplier.class);
    	
    	Supplier check = supplierService.findByName(adminId, record.getSupplierName(), lanType);
    	if(check != null) {
    		return ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_SUPPLIER_IS_EXIST.getCode(), 
    				ResponseCodeI18n.MODULE_CRM_SUPPLIER_IS_EXIST.getMessage(), lanType);
    	}
    	
    	record.setAdminId(adminId);
    	record.setLanType(lanType);
    	supplierService.insertData(record, supplierBankList);
    	
    	return ApiResultI18n.success(lanType);
    }
    
    /**
     * * 添加数据
     * @return
     */
    @AuthRuleAnnotation("admin/api/crm/supplier/updateData")
    @PostMapping("/admin/api/crm/supplier/updateData")
    public ApiResultI18n updateData(@RequestBody String params,
            HttpServletRequest request) {
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	String lanType = request.getHeader("X-LanType");
    	String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);

    	JSONArray supplierBankList = null;
        if(null != jsonObj.get(RequestParamConstant.SUPPLIER_BANK_LIST)){
        	supplierBankList = jsonObj.getJSONArray(RequestParamConstant.SUPPLIER_BANK_LIST);
        }
        
        jsonObj.remove(RequestParamConstant.SUPPLIER_BANK_LIST);
    	Supplier record = (Supplier) JSONObject.toBean(jsonObj, Supplier.class);
    	
    	Supplier check = supplierService.findByPrimayKey(record.getSupplierId());
    	if(check== null) {
    		return ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_SUPPLIER_IS_NOT_EXIST.getCode(), 
    				ResponseCodeI18n.MODULE_CRM_SUPPLIER_IS_NOT_EXIST.getMessage(), lanType);
    	}
    	
    	Supplier checkName = supplierService.findByName(adminId, record.getSupplierName(), lanType);
    	if(checkName != null) {
    		if(!checkName.getSupplierId().equals(record.getSupplierId())) {
    			return ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_SUPPLIER_IS_EXIST.getCode(), 
        				ResponseCodeI18n.MODULE_CRM_SUPPLIER_IS_EXIST.getMessage(), lanType);
    		}
    	}
    	
    	record.setAdminId(adminId);
    	supplierService.updateData(record, supplierBankList);
    	return ApiResultI18n.success(lanType);
    }
    
    /**
     * * 添加数据
     * @return
     */
    @AuthRuleAnnotation("admin/api/crm/supplier/deleteData")
    @PostMapping("/admin/api/crm/supplier/deleteData")
    public ApiResultI18n deleteData(@RequestBody String params,
            HttpServletRequest request){
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	
    	String lanType = request.getHeader("X-LanType");
    	
    	Integer supplierId = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.SUPPLIER_ID))){
        	supplierId = jsonObj.getInt(RequestParamConstant.SUPPLIER_ID);
        }else {
        	return ApiResultI18n.failure(ResponseCodeI18n.PARAM_ERROR.getCode(), 
        			ResponseCodeI18n.PARAM_ERROR.getMessage(),lanType);
        }
        
        supplierService.deleteByPrimayKey(supplierId);
        
    	return ApiResultI18n.success(lanType);
    }
    
    /**
     * * 添加数据
     * @return
     */
    @AuthRuleAnnotation("admin/api/crm/supplier/bank/deleteData")
    @PostMapping("/admin/api/crm/supplier/bank/deleteData")
    public ApiResultI18n deleteBankData(@RequestBody String params,
            HttpServletRequest request){
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	
    	String lanType = request.getHeader("X-LanType");
    	
    	Integer id = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.ID))){
        	id = jsonObj.getInt(RequestParamConstant.ID);
        }else {
        	return ApiResultI18n.failure(ResponseCodeI18n.PARAM_ERROR.getCode(), 
        			ResponseCodeI18n.PARAM_ERROR.getMessage(),lanType);
        }
        
        supplierBankService.deleteByPrimayKey(id);
        
    	return ApiResultI18n.success(lanType);
    }
    
}
