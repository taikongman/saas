package com.saas.api.admin.controller.crm;


import com.alibaba.fastjson.JSON;
import com.saas.api.admin.annotation.AuthRuleAnnotation;
import com.saas.api.admin.service.crm.BusinessService;
import com.saas.api.common.constant.CommonConstant;
import com.saas.api.common.constant.RequestParamConstant;
import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.crm.SysData;
import com.saas.api.common.util.ApiResultI18n;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 门店相关
 */
@RestController
public class CrmController {

    @Autowired
    private BusinessService businessService;
    

    @RequestMapping(value = "/module/crm/inventoryWarn", method = RequestMethod.POST)
    public ApiResultI18n inventoryWarn(@RequestBody String params,
            HttpServletRequest request) {
    	JSONObject jsonObj = JSONObject.fromObject(params);
        String lanType = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.LAN_TYPE))){
        	lanType = jsonObj.getString(RequestParamConstant.LAN_TYPE);
        }
        
        String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        
        String code = null;
        String title = null;
        Integer warnType = jsonObj.getInt(RequestParamConstant.WARN_TYPE);
        if (null != jsonObj.get(RequestParamConstant.C_CODE)){
            code = jsonObj.getString(RequestParamConstant.C_CODE);
        }
        if (null != jsonObj.get(RequestParamConstant.C_TITLE)){
            title = jsonObj.getString(RequestParamConstant.C_TITLE);
        }
        Map<String, Object> result = businessService.inventoryWarn(adminId, lanType, title, code, warnType, initPage(jsonObj));
        return ApiResultI18n.success(result, lanType);
    }

    

    @AuthRuleAnnotation("admin/api/crm/order/settle")
    @PostMapping("/admin/api/crm/order/settle")
    public ApiResultI18n settle(@RequestBody String params,
            HttpServletRequest request) {
        JSONObject jsonObj = JSONObject.fromObject(params);
        String lanType = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.LAN_TYPE))){
        	lanType = jsonObj.getString(RequestParamConstant.LAN_TYPE);
        }
        
        String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        
        businessService.settle(jsonObj, adminId, lanType);
        return ApiResultI18n.success(lanType);
    }

    @AuthRuleAnnotation("admin/api/crm/order/batchSettle")
    @PostMapping("/admin/api/crm/order/batchSettle")
    public ApiResultI18n batchSettle(@RequestBody String params,
            HttpServletRequest request) {
        JSONObject jsonObj = JSONObject.fromObject(params);
        String lanType = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.LAN_TYPE))){
        	lanType = jsonObj.getString(RequestParamConstant.LAN_TYPE);
        }
        
        String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        
        businessService.batchSettle(jsonObj, adminId, lanType);
        return ApiResultI18n.success(lanType);
    }
    
    
    @AuthRuleAnnotation("admin/api/crm/order/suspend")
    @PostMapping("/admin/api/crm/order/suspend")
    public ApiResultI18n suspend(@RequestBody String params,
            HttpServletRequest request) {
        JSONObject jsonObj = JSONObject.fromObject(params);
        String lanType = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.LAN_TYPE))){
        	lanType = jsonObj.getString(RequestParamConstant.LAN_TYPE);
        }
        
        String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        
        Long orderId = jsonObj.getLong(RequestParamConstant.ORDER_ID);
        businessService.suspend(adminId, orderId);
        return ApiResultI18n.success(lanType);
    }
    
    @AuthRuleAnnotation("admin/api/crm/customer/recharge")
    @PostMapping("/admin/api/crm/customer/recharge")
    public ApiResultI18n recharge(@RequestBody String params,
            HttpServletRequest request) throws ParseException{
        JSONObject jsonObj = JSONObject.fromObject(params);
        String lanType = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.LAN_TYPE))){
        	lanType = jsonObj.getString(RequestParamConstant.LAN_TYPE);
        }
        
        String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        
        businessService.recharge(jsonObj, adminId, lanType);
        return ApiResultI18n.success(lanType);
    }
    
    @AuthRuleAnnotation("admin/api/crm/customer/recharge/deleteData")
    @PostMapping("/admin/api/crm/customer/recharge/deleteData")
    public ApiResultI18n deleteRechargeData(@RequestBody String params,
            HttpServletRequest request) throws ParseException{
        JSONObject jsonObj = JSONObject.fromObject(params);
        String lanType = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.LAN_TYPE))){
        	lanType = jsonObj.getString(RequestParamConstant.LAN_TYPE);
        }
        
        String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        Long accountId = null;
        if (null != jsonObj.get(RequestParamConstant.ACCOUNT_ID)){
        	accountId = jsonObj.getLong(RequestParamConstant.ACCOUNT_ID);
        }
        
        businessService.deleteRecharge(accountId, adminId, lanType);
        return ApiResultI18n.success(lanType);
    }

    @AuthRuleAnnotation("admin/api/crm/customer/account/card/delay")
    @PostMapping("/admin/api/crm/customer/account/card/delay")
    public ApiResultI18n delay(@RequestBody String params,
            HttpServletRequest request) throws ParseException{
        JSONObject jsonObj = JSONObject.fromObject(params);
        String lanType = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.LAN_TYPE))){
        	lanType = jsonObj.getString(RequestParamConstant.LAN_TYPE);
        }
        
        String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        
        businessService.delay(jsonObj, adminId, lanType);
        return ApiResultI18n.success(lanType);
    }

    @AuthRuleAnnotation("")
    @PostMapping("/admin/api/crm/customer/rechargeRecordList")
    public ApiResultI18n rechargeRecordList(@RequestBody String params,
            HttpServletRequest request) {
        JSONObject jsonObj = JSONObject.fromObject(params);
        String lanType = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.LAN_TYPE))){
        	lanType = jsonObj.getString(RequestParamConstant.LAN_TYPE);
        }
        
        String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        
        Long customerId = jsonObj.getLong(RequestParamConstant.CUSTOMER_ID);
        Integer accountType = null;
        if (null != jsonObj.get(RequestParamConstant.ACCOUNT_TYPE)){
            accountType = jsonObj.getInt(RequestParamConstant.ACCOUNT_TYPE);
        }
        Map<String, Object> result = businessService.rechargeRecordList(adminId, customerId, accountType, initPage(jsonObj));
        return ApiResultI18n.success(result, lanType);
    }

    @RequestMapping(value = "/module/crm/ocr", method = RequestMethod.POST)
    public ApiResultI18n ocr(@RequestBody String params,
            HttpServletRequest request) {
        JSONObject jsonObj = JSONObject.fromObject(params);
        String lanType = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.LAN_TYPE))){
        	lanType = jsonObj.getString(RequestParamConstant.LAN_TYPE);
        }
        
        //String xAdminId = request.getHeader("X-AdminId");
        //Long adminId = Long.valueOf(xAdminId);
        
        String result = businessService.ocr(jsonObj, lanType);
        return ApiResultI18n.success(result, lanType);
    }

    @RequestMapping(value = "/module/crm/sysData", method = RequestMethod.POST)
    public ApiResultI18n sysData(@RequestBody String params,
            HttpServletRequest request) {
        JSONObject jsonObj = JSONObject.fromObject(params);
        String lanType = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.LAN_TYPE))){
        	lanType = jsonObj.getString(RequestParamConstant.LAN_TYPE);
        }
        
        //String xAdminId = request.getHeader("X-AdminId");
        //Long adminId = Long.valueOf(xAdminId);
        
        Integer dataType = jsonObj.getInt(RequestParamConstant.DATA_TYPE);
        List<SysData> result = businessService.sysData(dataType);
        return ApiResultI18n.success(result, lanType);
    }

    @RequestMapping(value = "/module/crm/workData", method = RequestMethod.POST)
    public ApiResultI18n workData(@RequestBody String params,
            HttpServletRequest request) {
        JSONObject jsonObj = JSONObject.fromObject(params);
        String lanType = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.LAN_TYPE))){
        	lanType = jsonObj.getString(RequestParamConstant.LAN_TYPE);
        }
        
        //String xAdminId = request.getHeader("X-AdminId");
        //Long adminId = Long.valueOf(xAdminId);
        
        Map<String, Object> paramsMap = JSON.parseObject(params);
        
        Map<String, Object> result = businessService.workData(paramsMap);
        return ApiResultI18n.success(result, lanType);
    }

    @RequestMapping(value = "/module/crm/addFeedback", method = RequestMethod.POST)
    public ApiResultI18n addFeedback(@RequestBody String params,
            HttpServletRequest request) {
        JSONObject jsonObj = JSONObject.fromObject(params);
        String lanType = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.LAN_TYPE))){
        	lanType = jsonObj.getString(RequestParamConstant.LAN_TYPE);
        }
        
        String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        
        businessService.addFeedback(jsonObj, adminId, lanType);
        return ApiResultI18n.success(lanType);
    }
    


    private Page initPage(JSONObject params) {
        Integer pageNo = CommonConstant.DEFAULT_PAGE_NO;
        Integer pageSize = CommonConstant.DEFAULT_PAGE_SIZE;
        if (null != params.get(RequestParamConstant.PAGE_NO)) {
            pageNo = params.getInt(RequestParamConstant.PAGE_NO);
        }
        if (null != params.get(RequestParamConstant.PAGE_SIZE)) {
            pageSize = params.getInt(RequestParamConstant.PAGE_SIZE);
        }
        return new Page(pageNo, pageSize);
    }
}
