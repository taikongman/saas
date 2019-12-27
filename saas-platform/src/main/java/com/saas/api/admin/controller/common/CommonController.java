package com.saas.api.admin.controller.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.saas.api.admin.annotation.AuthRuleAnnotation;
import com.saas.api.admin.service.common.CashModeService;
import com.saas.api.admin.service.common.MaintainTypeService;
import com.saas.api.admin.service.common.PricingModeService;
import com.saas.api.admin.service.common.ProjectTypeService;
import com.saas.api.admin.service.common.SettleModeService;
import com.saas.api.common.constant.CommonConstant;
import com.saas.api.common.constant.RequestParamConstant;
import com.saas.api.common.constant.ResponseCodeI18n;
import com.saas.api.common.entity.common.CashMode;
import com.saas.api.common.entity.common.MaintainType;
import com.saas.api.common.entity.common.PricingMode;
import com.saas.api.common.entity.common.ProjectType;
import com.saas.api.common.entity.common.SettleMode;
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
public class CommonController {
	
	@Autowired
    private CashModeService cashModeService;
	
	@Autowired
    private MaintainTypeService maintainTypeService;
	
	@Autowired
    private PricingModeService pricingModeService;
	
	@Autowired
    private SettleModeService settleModeService;
	
	@Autowired
	private ProjectTypeService projectTypeService;
	
	/**
     * * 查询列表
     * @return @AuthRuleAnnotation("/admin/api/common/unit/getSelectList")
     */
    @PostMapping(value = "/admin/api/common/common/queryList")
    public ApiResultI18n queryList(@RequestBody String params,
            HttpServletRequest request) {
    	log.debug(params);
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	String lanType = request.getHeader("X-LanType");
        
        Integer id = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.ID))){
        	id = jsonObj.getInt(RequestParamConstant.ID);
        }
        Integer dataType = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.DATA_TYPE))){
        	dataType = jsonObj.getInt(RequestParamConstant.DATA_TYPE);
        }
        
        Map<String, Object> result = new HashMap<String, Object>();
        if(dataType.equals(CommonConstant.DATA_TYPE_MAINTAIN_TYPE)) {
        	result = maintainTypeService.getListData(id, lanType, ParamUtil.initPage(jsonObj));
        } else if(dataType.equals(CommonConstant.DATA_TYPE_PRICING_MODE)) {
        	result = pricingModeService.getListData(id, lanType, ParamUtil.initPage(jsonObj));
        } else if(dataType.equals(CommonConstant.DATA_TYPE_SETTLE_MODE)) {
        	result = settleModeService.getListData(id, lanType, ParamUtil.initPage(jsonObj));
        } else if(dataType.equals(CommonConstant.DATA_TYPE_CASH_MODE)) {
        	result = cashModeService.getListData(id, lanType, ParamUtil.initPage(jsonObj));
        } else if(dataType.equals(CommonConstant.DATA_TYPE_PROJECT_TYPE)) {
        	result = projectTypeService.getListData(id, lanType, ParamUtil.initPage(jsonObj));
        }
        
    	return ApiResultI18n.success(result, lanType);
    }
	
	/**
     * * 查询下拉列表
     * @return @AuthRuleAnnotation("/admin/api/common/unit/querySelectList")
     */
    @PostMapping(value = "/admin/api/common/common/querySelectList")
    public ApiResultI18n querySelectList(@RequestBody String params,
            HttpServletRequest request) {
    	log.debug(params);
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	String lanType = request.getHeader("X-LanType");
    	
        Integer dataType = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.DATA_TYPE))){
        	dataType = jsonObj.getInt(RequestParamConstant.DATA_TYPE);
        }
        
        List<?> result = new ArrayList<>();
        if(dataType.equals(CommonConstant.DATA_TYPE_MAINTAIN_TYPE)) {
        	result = maintainTypeService.findSelectObject(lanType);
        } else if(dataType.equals(CommonConstant.DATA_TYPE_PRICING_MODE)) {
        	result = pricingModeService.findSelectObject(lanType);
        } else if(dataType.equals(CommonConstant.DATA_TYPE_SETTLE_MODE)) {
        	result = settleModeService.findSelectObject(lanType);
        } else if(dataType.equals(CommonConstant.DATA_TYPE_CASH_MODE)) {
        	result = cashModeService.findSelectObject(lanType);
        } else if(dataType.equals(CommonConstant.DATA_TYPE_PROJECT_TYPE)) {
        	result = projectTypeService.findSelectObject(lanType);
        }
    	
        return ApiResultI18n.success(result, lanType);
    }
    
    /**
     * * 添加数据
     * @return
     */
    @AuthRuleAnnotation("admin/api/common/common/addData")
    @PostMapping("/admin/api/common/common/addData")
    public ApiResultI18n insertData(@RequestBody String params,
            HttpServletRequest request){
    	String lanType = request.getHeader("X-LanType");
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	
    	Integer dataType = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.DATA_TYPE))){
        	dataType = jsonObj.getInt(RequestParamConstant.DATA_TYPE);
        }
        
        String name = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.NAME))){
        	name = jsonObj.getString(RequestParamConstant.NAME);
        }
        String remark = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.REMARK))){
        	remark = jsonObj.getString(RequestParamConstant.REMARK);
        }
        
    	if(dataType.equals(CommonConstant.DATA_TYPE_MAINTAIN_TYPE)) {
    		MaintainType check = maintainTypeService.findByName(name);
    		if(check != null) {
    			return ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_MAINTAIN_TYPE_IS_EXIST.getCode(), 
        				ResponseCodeI18n.MODULE_CRM_MAINTAIN_TYPE_IS_EXIST.getMessage(), lanType);
    		}
    		maintainTypeService.insertDataByParams(name, remark, lanType);
        } else if(dataType.equals(CommonConstant.DATA_TYPE_PRICING_MODE)) {
        	Integer isTime = null;
            if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.IS_TIME))){
            	isTime = jsonObj.getInt(RequestParamConstant.IS_TIME);
            }
        	PricingMode check = pricingModeService.findByName(name);
    		if(check != null) {
    			return ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_PRICING_MODE_IS_EXIST.getCode(), 
        				ResponseCodeI18n.MODULE_CRM_PRICING_MODE_IS_EXIST.getMessage(), lanType);
    		}
    		pricingModeService.insertDataByParams(name, isTime, remark, lanType);
        } else if(dataType.equals(CommonConstant.DATA_TYPE_SETTLE_MODE)) {
        	SettleMode check = settleModeService.findByName(name);
    		if(check != null) {
    			return ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_SETTLE_MODE_IS_EXIST.getCode(), 
        				ResponseCodeI18n.MODULE_CRM_SETTLE_MODE_IS_EXIST.getMessage(), lanType);
    		}
    		settleModeService.insertDataByParams(name, remark, lanType);
        } else if(dataType.equals(CommonConstant.DATA_TYPE_CASH_MODE)) {
        	CashMode check = cashModeService.findByName(name);
    		if(check != null) {
    			return ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_CASH_MODE_IS_EXIST.getCode(), 
        				ResponseCodeI18n.MODULE_CRM_CASH_MODE_IS_EXIST.getMessage(), lanType);
    		}
    		cashModeService.insertDataByParams(name, remark, lanType);
        }else if(dataType.equals(CommonConstant.DATA_TYPE_PROJECT_TYPE)) {
        	ProjectType check = projectTypeService.findByName(name);
    		if(check != null) {
    			return ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_PROJECT_TYPE_IS_EXIST.getCode(), 
        				ResponseCodeI18n.MODULE_CRM_PROJECT_TYPE_IS_EXIST.getMessage(), lanType);
    		}
    		projectTypeService.insertDataByParams(name, remark, lanType);
        }
    	
    	return ApiResultI18n.success(lanType);
    }
    
    /**
     * * 更新数据
     * @return
     */
    @AuthRuleAnnotation("admin/api/common/common/updateData")
    @PostMapping("/admin/api/common/common/updateData")
    public ApiResultI18n updateData(@RequestBody String params,
            HttpServletRequest request){
    	String lanType = request.getHeader("X-LanType");
    	
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	Integer dataType = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.DATA_TYPE))){
        	dataType = jsonObj.getInt(RequestParamConstant.DATA_TYPE);
        }
        Integer id = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.ID))){
        	id = jsonObj.getInt(RequestParamConstant.ID);
        }
        String name = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.NAME))){
        	name = jsonObj.getString(RequestParamConstant.NAME);
        }
        String remark = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.REMARK))){
        	remark = jsonObj.getString(RequestParamConstant.REMARK);
        }  	
        
    	if(dataType.equals(CommonConstant.DATA_TYPE_MAINTAIN_TYPE)) {
    		MaintainType check = maintainTypeService.findByPrimayKey(id);
        	if(check == null) {
        		return ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_MAINTAIN_TYPE_IS_NOT_EXIST.getCode(), 
        				ResponseCodeI18n.MODULE_CRM_MAINTAIN_TYPE_IS_NOT_EXIST.getMessage(), lanType);
        	}
    		MaintainType checkName = maintainTypeService.findByName(name);
    		if(checkName != null) {
    			if(!checkName.getId().equals(id)) {
    				return ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_MAINTAIN_TYPE_IS_EXIST.getCode(), 
            				ResponseCodeI18n.MODULE_CRM_MAINTAIN_TYPE_IS_EXIST.getMessage(), lanType);
    			}
    		}
    		maintainTypeService.updateDataByParams(id, name, remark, lanType);
        } else if(dataType.equals(CommonConstant.DATA_TYPE_PRICING_MODE)) {
        	Integer isTime = null;
            if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.IS_TIME))){
            	isTime = jsonObj.getInt(RequestParamConstant.IS_TIME);
            }
    		PricingMode check = pricingModeService.findByPrimayKey(id);
        	if(check == null) {
        		return ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_PRICING_MODE_IS_NOT_EXIST.getCode(), 
        				ResponseCodeI18n.MODULE_CRM_PRICING_MODE_IS_NOT_EXIST.getMessage(), lanType);
        	}
        	PricingMode checkName = pricingModeService.findByName(name);
    		if(checkName != null) {
    			if(!checkName.getId().equals(id)) {
    				return ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_PRICING_MODE_IS_EXIST.getCode(), 
            				ResponseCodeI18n.MODULE_CRM_PRICING_MODE_IS_EXIST.getMessage(), lanType);
    			}
    		}
    		pricingModeService.updateDataByParams(id, name, isTime, remark, lanType);
        } else if(dataType.equals(CommonConstant.DATA_TYPE_SETTLE_MODE)) {
        	SettleMode check = settleModeService.findByPrimayKey(id);
        	if(check == null) {
        		return ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_SETTLE_MODE_IS_NOT_EXIST.getCode(), 
        				ResponseCodeI18n.MODULE_CRM_SETTLE_MODE_IS_NOT_EXIST.getMessage(), lanType);
        	}
        	SettleMode checkName = settleModeService.findByName(name);
    		if(checkName != null) {
    			if(!checkName.getId().equals(id)) {
    				return ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_SETTLE_MODE_IS_EXIST.getCode(), 
            				ResponseCodeI18n.MODULE_CRM_SETTLE_MODE_IS_EXIST.getMessage(), lanType);
    			}
    		}
    		settleModeService.updateDataByParams(id, name, remark, lanType);
        } else if(dataType.equals(CommonConstant.DATA_TYPE_CASH_MODE)) {    		
    		CashMode check = cashModeService.findByPrimayKey(id);
        	if(check == null) {
        		return ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_CASH_MODE_IS_NOT_EXIST.getCode(), 
        				ResponseCodeI18n.MODULE_CRM_CASH_MODE_IS_NOT_EXIST.getMessage(), lanType);
        	}
        	CashMode checkName = cashModeService.findByName(name);
    		if(checkName != null) {
    			if(!checkName.getId().equals(id)) {
    				return ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_CASH_MODE_IS_EXIST.getCode(), 
            				ResponseCodeI18n.MODULE_CRM_CASH_MODE_IS_EXIST.getMessage(), lanType);
    			}
    		}
    		cashModeService.updateDataByParams(id, name, remark, lanType);
        }else if(dataType.equals(CommonConstant.DATA_TYPE_PROJECT_TYPE)) {
        	ProjectType check = projectTypeService.findByPrimayKey(id);
        	if(check == null) {
        		return ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_PROJECT_TYPE_IS_NOT_EXIST.getCode(), 
        				ResponseCodeI18n.MODULE_CRM_PROJECT_TYPE_IS_NOT_EXIST.getMessage(), lanType);
        	}
        	ProjectType checkName = projectTypeService.findByName(name);
    		if(checkName != null) {
    			if(!checkName.getId().equals(id)) {
    				return ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_PROJECT_TYPE_IS_EXIST.getCode(), 
            				ResponseCodeI18n.MODULE_CRM_PROJECT_TYPE_IS_EXIST.getMessage(), lanType);
    			}
    		}
    		projectTypeService.updateDataByParams(id, name, remark, lanType);
        }
    	
    	return ApiResultI18n.success(lanType);
    }
    
    /**
     * * 删除数据
     * @return
     */
    @AuthRuleAnnotation("admin/api/common/common/deleteData")
    @PostMapping("/admin/api/common/common/deleteData")
    public ApiResultI18n deleteData(@RequestBody String params,
            HttpServletRequest request){
    	String lanType = request.getHeader("X-LanType");
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	
    	Integer dataType = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.DATA_TYPE))){
        	dataType = jsonObj.getInt(RequestParamConstant.DATA_TYPE);
        }
        
    	Integer id = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.ID))){
        	id = jsonObj.getInt(RequestParamConstant.ID);
        }else {
        	return ApiResultI18n.failure(ResponseCodeI18n.PARAM_ERROR.getCode(), 
        			ResponseCodeI18n.PARAM_ERROR.getMessage(),lanType);
        }
        
        if(dataType.equals(CommonConstant.DATA_TYPE_MAINTAIN_TYPE)) {
        	maintainTypeService.deleteByPrimayKey(id);
        } else if(dataType.equals(CommonConstant.DATA_TYPE_PRICING_MODE)) {
        	pricingModeService.deleteByPrimayKey(id);
        } else if(dataType.equals(CommonConstant.DATA_TYPE_SETTLE_MODE)) {
        	settleModeService.deleteByPrimayKey(id);
        } else if(dataType.equals(CommonConstant.DATA_TYPE_CASH_MODE)) {
        	cashModeService.deleteByPrimayKey(id);
        } else if(dataType.equals(CommonConstant.DATA_TYPE_PROJECT_TYPE)) {
        	projectTypeService.deleteByPrimayKey(id);
        }
        
    	return ApiResultI18n.success(lanType);
    }
    
}
