package com.saas.api.admin.controller.crm;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.saas.api.admin.annotation.AuthRuleAnnotation;
import com.saas.api.admin.service.crm.PurchaseRecordService;
import com.saas.api.common.constant.RequestParamConstant;
import com.saas.api.common.constant.ResponseCodeI18n;
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
public class PurchaseRecordController {
	
	@Autowired
    private PurchaseRecordService purchaseRecordService;
	
	/**
     * * 查询发票列表
     * @return @AuthRuleAnnotation("/admin/api/common/unit/getSelectList")
     */
    @PostMapping(value = "/admin/api/crm/purchase/queryList")
    public ApiResultI18n queryList(@RequestBody String params,
            HttpServletRequest request) {
    	log.debug(params);
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	
    	String lanType = request.getHeader("X-LanType");
    	String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        
        Map<String, Object> paramsMap = JSON.parseObject(params);
        ParamUtil.initMapParam(paramsMap, jsonObj, lanType);
        
        Map<String, Object> result = purchaseRecordService.getDataList(paramsMap, adminId);
    	return ApiResultI18n.success(result, lanType);
    }
    
    /**
     * * 添加数据
     * @return
     */
    @AuthRuleAnnotation("admin/api/crm/purchase/addData")
    @PostMapping("/admin/api/crm/purchase/addData")
    public ApiResultI18n insertData(@RequestBody String params,
            HttpServletRequest request) {
    	log.debug(params);
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	
    	String lanType = request.getHeader("X-LanType");
    	String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        
    	purchaseRecordService.insertDataByJson(jsonObj, adminId, lanType);
    	
    	return ApiResultI18n.success(lanType);
    }
    
    /**
     * * 添加数据
     * @return
     */
    @AuthRuleAnnotation("admin/api/crm/purchase/updateData")
    @PostMapping("/admin/api/crm/purchase/updateData")
    public ApiResultI18n updateData(@RequestBody String params,
            HttpServletRequest request) {
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	String lanType = request.getHeader("X-LanType");
    	String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);

        purchaseRecordService.updateDataByJson(jsonObj, adminId, lanType);
    	return ApiResultI18n.success(lanType);
    }
    
    /**
     * * 添加数据
     * @return
     */
    @AuthRuleAnnotation("admin/api/crm/purchase/deleteData")
    @PostMapping("/admin/api/crm/purchase/deleteData")
    public ApiResultI18n deleteData(@RequestBody String params,
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
        
        //purchaseRecordService.deleteByPrimayKey(id);
        purchaseRecordService.deleteByStatus(id, lanType);
        
    	return ApiResultI18n.success(lanType);
    }
    
}
