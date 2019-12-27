package com.saas.api.admin.controller.crm;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.saas.api.admin.annotation.AuthRuleAnnotation;
import com.saas.api.admin.service.crm.OrderService;
import com.saas.api.admin.service.sys.auth.UserService;
import com.saas.api.common.constant.CommonConstant;
import com.saas.api.common.constant.RequestParamConstant;
import com.saas.api.common.constant.ResponseCodeI18n;
import com.saas.api.common.entity.sys.auth.User;
import com.saas.api.common.util.ApiResultI18n;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@RestController
@Slf4j
public class OrderController {
	
	@Autowired
    private OrderService orderService;
	
	@Autowired
    private UserService userService;
	
	/**
     * * 查询订单列表
     * @return
     */
    @AuthRuleAnnotation("")
    @RequestMapping(value = "/module/crm/orderList", method = RequestMethod.POST)
    public ApiResultI18n orderList(@RequestBody String params,
            HttpServletRequest request) {
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	String lanType = request.getHeader("X-LanType");

    	String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        
        Map<String, Object> paramsMap = JSON.parseObject(params);
        initMapParam(paramsMap, jsonObj, adminId, lanType);
        Map<String, Object> result = orderService.orderList(paramsMap);
        
        return ApiResultI18n.success(result, lanType);
    }

    @RequestMapping(value = "/module/crm/orderList4OrCond", method = RequestMethod.POST)
    public ApiResultI18n orderList4OrCond(@RequestBody String params,
            HttpServletRequest request) {
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	String lanType = request.getHeader("X-LanType");

    	String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        
        Map<String, Object> paramsMap = JSON.parseObject(params);
        initMapParam(paramsMap, jsonObj, adminId, lanType);
        
        Map<String, Object> result = orderService.orderList4OrCond(paramsMap);
        return ApiResultI18n.success(result, lanType);
    }

    @RequestMapping(value = "/admin/api/crm/order/detailData", method = RequestMethod.POST)
    public ApiResultI18n orderDetail(@RequestBody String params,
            HttpServletRequest request) {
        JSONObject jsonObj = JSONObject.fromObject(params);
        String lanType = request.getHeader("X-LanType");
        
        Long orderId = null;
        if (null != jsonObj.get(RequestParamConstant.ORDER_ID)){
        	orderId = jsonObj.getLong(RequestParamConstant.ORDER_ID);
        }
        
        String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        
        Map<String, Object> result = orderService.orderDetail(orderId, adminId);
        return ApiResultI18n.success(result, lanType);
    }

    @RequestMapping(value = "/module/crm/dispatchOrder", method = RequestMethod.POST)
    public ApiResultI18n dispatchOrder(@RequestBody String params,
            HttpServletRequest request) {
        JSONObject jsonObj = JSONObject.fromObject(params);
        String lanType = request.getHeader("X-LanType");
        
        Long orderId = null;
        if (null != jsonObj.get(RequestParamConstant.ORDER_ID)){
        	orderId = jsonObj.getLong(RequestParamConstant.ORDER_ID);
        }
        Long doAdminId = null;
        if (null != jsonObj.get(RequestParamConstant.DO_ADMIN_ID)){
        	doAdminId = jsonObj.getLong(RequestParamConstant.DO_ADMIN_ID);
        }
        
        orderService.dispachOrder(doAdminId, orderId);
        
        return ApiResultI18n.success(lanType);
    }

    @RequestMapping(value = "/module/crm/finishWork", method = RequestMethod.POST)
    public ApiResultI18n finishWork(@RequestBody String params,
            HttpServletRequest request) {
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	String lanType = request.getHeader("X-LanType");
    	
        Long orderId = null;
        if (null != jsonObj.get(RequestParamConstant.ORDER_ID)){
        	orderId = jsonObj.getLong(RequestParamConstant.ORDER_ID);
        }
        
        orderService.finishWork(orderId);        
        
        return ApiResultI18n.success(lanType);
    }

    @RequestMapping(value = "/module/crm/addOrder", method = RequestMethod.POST)
    public ApiResultI18n addOrder(@RequestBody String params,
            HttpServletRequest request) {
        log.info(params);
        JSONObject jsonObj = JSONObject.fromObject(params);
        String lanType = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.LAN_TYPE))){
        	lanType = jsonObj.getString(RequestParamConstant.LAN_TYPE);
        }
        String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        
        JSONObject orderObj = JSONObject.fromObject(jsonObj.getString(RequestParamConstant.ORDER));
        
        JSONArray projectList = orderObj.getJSONArray(RequestParamConstant.PROJECT_LIST);
        if(projectList == null || projectList.size() == 0) {
        	return ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_ORDER_PROJECT_IS_EMPTY.getCode(), 
    				ResponseCodeI18n.MODULE_CRM_ORDER_PROJECT_IS_EMPTY.getMessage(), lanType);
        }
        
        orderService.addOrder(orderObj, adminId, lanType);;
        return ApiResultI18n.success(lanType);
    }
    
    @AuthRuleAnnotation("admin/api/crm/order/operateCommodity")
    @PostMapping("/admin/api/crm/order/operateCommodity")
    public ApiResultI18n operateCommodity(@RequestBody String params,
            HttpServletRequest request) {
        JSONObject jsonObj = JSONObject.fromObject(params);
        String lanType = request.getHeader("X-LanType");
        String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        
        Long orderId = null;
        if (null != jsonObj.get(RequestParamConstant.ORDER_ID)){
        	orderId = jsonObj.getLong(RequestParamConstant.ORDER_ID);
        }
        
        if(orderId == null) {
        	return ApiResultI18n.failure(ResponseCodeI18n.PARAM_ERROR.getCode(), 
    				ResponseCodeI18n.PARAM_ERROR.getMessage(), lanType);
        }
        JSONArray commodityList = jsonObj.getJSONArray(RequestParamConstant.COMMODITY_LIST);
        if(commodityList == null || commodityList.size() == 0) {
        	return ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_ORDER_PROJECT_IS_EMPTY.getCode(), 
    				ResponseCodeI18n.MODULE_CRM_ORDER_PROJECT_IS_EMPTY.getMessage(), lanType);
        }
        
        orderService.operateCommodity(commodityList, orderId, adminId, lanType);
        
        return ApiResultI18n.success(lanType);
    }
    
    @AuthRuleAnnotation("admin/api/crm/order/updateData")
    @PostMapping("/admin/api/crm/order/updateData")
    public ApiResultI18n updateData(@RequestBody String params,
            HttpServletRequest request) {
        log.info(params);
        JSONObject jsonObj = JSONObject.fromObject(params);
        String lanType = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.LAN_TYPE))){
        	lanType = jsonObj.getString(RequestParamConstant.LAN_TYPE);
        }
        String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        
        JSONObject orderObj = JSONObject.fromObject(jsonObj.getString(RequestParamConstant.ORDER));
        
        JSONArray projectList = orderObj.getJSONArray(RequestParamConstant.PROJECT_LIST);
        if(projectList == null || projectList.size() == 0) {
        	return ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_ORDER_PROJECT_IS_EMPTY.getCode(), 
    				ResponseCodeI18n.MODULE_CRM_ORDER_PROJECT_IS_EMPTY.getMessage(), lanType);
        }
        
        orderService.updateData(orderObj, adminId, lanType);;
        return ApiResultI18n.success(lanType);
    }
    
    private Map<String, Object> initMapParam(Map<String, Object> paramsMap, JSONObject jsonObj, Long adminId, String lanType){
    	Integer pageNo = CommonConstant.DEFAULT_PAGE_NO;
        Integer pageSize = CommonConstant.DEFAULT_PAGE_SIZE;
        
        if (null != jsonObj.get(RequestParamConstant.PAGE_NO)){
            pageNo = jsonObj.getInt(RequestParamConstant.PAGE_NO);
        }
        if (null != jsonObj.get(RequestParamConstant.PAGE_SIZE)){
            pageSize = jsonObj.getInt(RequestParamConstant.PAGE_SIZE);
        }
        
        if (null != jsonObj.get(RequestParamConstant.ADMIN_ID)){
        	adminId = jsonObj.getLong(RequestParamConstant.ADMIN_ID);
        }
        User user = userService.findByPrimayKey(adminId);
    	
        paramsMap.put(RequestParamConstant.START_INDEX, (pageNo-1) * pageSize);
        paramsMap.put(RequestParamConstant.PAGE_SIZE, pageSize);
        paramsMap.put(RequestParamConstant.GROUP_ID, user.getGroupId());
        paramsMap.put(RequestParamConstant.COMPANY_ID, user.getCompanyId());
        paramsMap.put(RequestParamConstant.DEPARTMENT_ID, user.getDepartmentId());
        paramsMap.put(RequestParamConstant.ADMIN_ID, adminId);
        paramsMap.put(RequestParamConstant.LAN_TYPE, lanType);
        
        return paramsMap;
    }

}
