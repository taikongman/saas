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
import com.saas.api.admin.res.select.crm.CardSelect;
import com.saas.api.admin.service.crm.CardCommodityService;
import com.saas.api.admin.service.crm.CardProjectService;
import com.saas.api.admin.service.crm.CardService;
import com.saas.api.common.constant.RequestParamConstant;
import com.saas.api.common.constant.ResponseCodeI18n;
import com.saas.api.common.util.ApiResultI18n;
import com.saas.api.common.util.ParamUtil;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

/**
 * @Description: 会员卡相关接口
 * @Author: 徐未
 * @Date: 2019/12/19
 */
@RestController
@Slf4j
public class CardController {
	
	@Autowired
    private CardService cardService;
	
	@Autowired
    private CardCommodityService cardCommodityService;
	
	@Autowired
    private CardProjectService cardProjectService;
	
	/**
     * * 查询发票列表
     * @return @AuthRuleAnnotation("/admin/api/crm/commodity/getSelectList")
     */
    @PostMapping(value = "/admin/api/crm/card/queryList")
    public ApiResultI18n queryList(@RequestBody String params,
            HttpServletRequest request) {
    	log.debug(params);
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	
    	String lanType = request.getHeader("X-LanType");
    	String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        
        String cardName = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.CARD_NAME))){
        	cardName = jsonObj.getString(RequestParamConstant.CARD_NAME);
        }
        
        Integer categoryId = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.CATEGORY_ID))){
        	categoryId = jsonObj.getInt(RequestParamConstant.CATEGORY_ID);
        }
        
        Map<String, Object> result = cardService.getListData(cardName, categoryId, 
        		adminId, lanType, ParamUtil.initPage(jsonObj));
        
    	return ApiResultI18n.success(result, lanType);
    }
	
	/**
     * * 查询下拉发票列表
     * @return @AuthRuleAnnotation("/admin/api/crm/commodity/querySelectList")
     */
    @PostMapping(value = "/admin/api/crm/card/querySelectList")
    public ApiResultI18n querySelectList(@RequestBody String params,
            HttpServletRequest request) {
    	log.debug(params);
    	//JSONObject jsonObj = JSONObject.fromObject(params);
    	String lanType = request.getHeader("X-LanType");
    	String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        
        
        List<CardSelect> result = cardService.findSelectObject(adminId, lanType);
    	return ApiResultI18n.success(result, lanType);
    }
    
    /**
     * * 添加数据
     * @return
     */
    @AuthRuleAnnotation("admin/api/crm/card/addData")
    @PostMapping("/admin/api/crm/card/addData")
    public ApiResultI18n insertData(@RequestBody String params,
            HttpServletRequest request){
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	String lanType = request.getHeader("X-LanType");
    	String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        
        cardService.addData(jsonObj, adminId, lanType);
    	
    	return ApiResultI18n.success(lanType);
    }
    
    /**
     * * 添加数据
     * @return
     */
    @AuthRuleAnnotation("admin/api/crm/card/updateData")
    @PostMapping("/admin/api/crm/card/updateData")
    public ApiResultI18n updateData(@RequestBody String params,
            HttpServletRequest request){
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	String lanType = request.getHeader("X-LanType");
    	String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);

        cardService.modifyData(jsonObj, adminId, lanType);
    	return ApiResultI18n.success(lanType);
    }
    
    /**
     * * 删除会员卡数据
     * @return
     */
    @AuthRuleAnnotation("admin/api/crm/card/deleteData")
    @PostMapping("/admin/api/crm/card/deleteData")
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
        
        cardService.deleteData(id);
        
    	return ApiResultI18n.success(lanType);
    }
    
    /**
     * * 删除数据
     * @return
     */
    @AuthRuleAnnotation("admin/api/crm/card/commodity/deleteData")
    @PostMapping("/admin/api/crm/card/commodity/deleteData")
    public ApiResultI18n deleteCommodityData(@RequestBody String params,
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
        
        cardCommodityService.deleteData(id);
        
    	return ApiResultI18n.success(lanType);
    }
    
    /**
     * * 删除数据
     * @return
     */
    @AuthRuleAnnotation("admin/api/crm/card/project/deleteData")
    @PostMapping("/admin/api/crm/card/project/deleteData")
    public ApiResultI18n deleteProjectData(@RequestBody String params,
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
        
        cardProjectService.deleteData(id);
        
    	return ApiResultI18n.success(lanType);
    }
    
}
