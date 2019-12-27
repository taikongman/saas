package com.saas.api.admin.controller.common;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.saas.api.admin.annotation.AuthRuleAnnotation;
import com.saas.api.admin.res.select.common.UnitSelect;
import com.saas.api.admin.service.common.UnitService;
import com.saas.api.common.constant.RequestParamConstant;
import com.saas.api.common.constant.ResponseCodeI18n;
import com.saas.api.common.entity.common.Unit;
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
public class UnitController {
	
	@Autowired
    private UnitService unitService;
	
	
	/**
     * * 查询发票列表
     * @return @AuthRuleAnnotation("/admin/api/common/unit/getSelectList")
     */
    @PostMapping(value = "/admin/api/common/unit/queryList")
    public ApiResultI18n queryList(@RequestBody String params,
            HttpServletRequest request) {
    	log.debug(params);
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	String lanType = request.getHeader("X-LanType");
        
        Integer id = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.ID))){
        	id = jsonObj.getInt(RequestParamConstant.ID);
        }
        
        Map<String, Object> result = unitService.getListData(id, lanType, ParamUtil.initPage(jsonObj));
    	return ApiResultI18n.success(result, lanType);
    }
	
	/**
     * * 查询下拉发票列表
     * @return @AuthRuleAnnotation("/admin/api/common/unit/querySelectList")
     */
    @PostMapping(value = "/admin/api/common/unit/querySelectList")
    public ApiResultI18n querySelectList(@RequestBody String params,
            HttpServletRequest request) {
    	log.debug(params);
    	//JSONObject jsonObj = JSONObject.fromObject(params);
    	String lanType = request.getHeader("X-LanType");
        
        Unit record = new Unit();
        record.setLanType(lanType);
        List<UnitSelect> result = unitService.findSelectObject(record);
    	return ApiResultI18n.success(result, lanType);
    }
    
    /**
     * * 添加数据
     * @return
     */
    @AuthRuleAnnotation("admin/api/common/unit/addData")
    @PostMapping("/admin/api/common/unit/addData")
    public ApiResultI18n insertData(@RequestBody Unit record,
            HttpServletRequest request){
    	String lanType = request.getHeader("X-LanType");
    	
    	Unit check = unitService.findByName(record.getUnitName());
    	if(check != null) {
    		return ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_UNIT_IS_EXIST.getCode(), 
    				ResponseCodeI18n.MODULE_CRM_UNIT_IS_EXIST.getMessage(), lanType);
    	}
    	
    	record.setLanType(lanType);
    	unitService.insertData(record);
    	return ApiResultI18n.success(lanType);
    }
    
    /**
     * * 添加数据
     * @return
     */
    @AuthRuleAnnotation("admin/api/common/unit/updateData")
    @PostMapping("/admin/api/common/unit/updateData")
    public ApiResultI18n updateData(@RequestBody Unit record,
            HttpServletRequest request){
    	String lanType = request.getHeader("X-LanType");
    	
    	Unit check = unitService.findByPrimayKey(record.getId());
    	if(check == null) {
    		return ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_UNIT_IS_NOT_EXIST.getCode(), 
    				ResponseCodeI18n.MODULE_CRM_UNIT_IS_NOT_EXIST.getMessage(), lanType);
    	}
    	Unit checkName = unitService.findByName(record.getUnitName());
    	if(checkName != null) {
    		if(!checkName.getId().equals(record.getId())) {
    			return ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_UNIT_IS_EXIST.getCode(), 
        				ResponseCodeI18n.MODULE_CRM_UNIT_IS_EXIST.getMessage(), lanType);
    		}
    	}
    	
    	unitService.updateData(record);
    	return ApiResultI18n.success(lanType);
    }
    
    /**
     * * 添加数据
     * @return
     */
    @AuthRuleAnnotation("admin/api/common/unit/deleteData")
    @PostMapping("/admin/api/common/unit/deleteData")
    public ApiResultI18n deleteData(@RequestBody String params,
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
        
        unitService.deleteByPrimayKey(id);
        
    	return ApiResultI18n.success(lanType);
    }
    
}
