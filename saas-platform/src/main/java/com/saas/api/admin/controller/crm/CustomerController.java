package com.saas.api.admin.controller.crm;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.saas.api.admin.annotation.AuthRuleAnnotation;
import com.saas.api.admin.res.crm.CustomerAccountResponse;
import com.saas.api.admin.res.select.crm.CustomerSelect;
import com.saas.api.admin.service.crm.CustomerService;
import com.saas.api.common.constant.RequestParamConstant;
import com.saas.api.common.entity.crm.Car;
import com.saas.api.common.entity.crm.Customer;
import com.saas.api.common.util.ApiResultI18n;
import com.saas.api.common.util.ParamUtil;

import net.sf.json.JSONObject;

/**
 * @Description:  客户相关接口
 * @Author: 徐未
 * @Date: 2019/12/10
 */
@RestController
public class CustomerController {
	
	@Autowired
    private CustomerService customerService;
	
	@RequestMapping(value = "/module/crm/customerList", method = RequestMethod.POST)
    public ApiResultI18n customerList(@RequestBody String params,
            HttpServletRequest request) {
        JSONObject jsonObj = JSONObject.fromObject(params);
        String lanType = request.getHeader("X-LanType");
        
        String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        
        String name = null;
        String phone = null;
        if (null != jsonObj.get(RequestParamConstant.NAME)){
            name = jsonObj.getString(RequestParamConstant.NAME);
        }
        if (null != jsonObj.get(RequestParamConstant.PHONE)){
            phone = jsonObj.getString(RequestParamConstant.PHONE);
        }
        Map<String, Object> result = customerService.customerList(adminId, lanType, name, phone, ParamUtil.initPage(jsonObj));
        return ApiResultI18n.success(result, lanType);
    }

    @RequestMapping(value = "/module/crm/addCustomer", method = RequestMethod.POST)
    public ApiResultI18n addCustomer(@RequestBody String params,
            HttpServletRequest request) {
        JSONObject jsonObj = JSONObject.fromObject(params);
        String lanType = request.getHeader("X-LanType");
        
        String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        
        customerService.addCustomer(jsonObj, adminId, lanType);
        return ApiResultI18n.success(lanType);
    }
    
    @RequestMapping(value = "/module/crm/addCustomerAndCar", method = RequestMethod.POST)
    public ApiResultI18n addCustomerAndCar(@RequestBody String params,
            HttpServletRequest request) {
        JSONObject jsonObj = JSONObject.fromObject(params);
        String lanType = request.getHeader("X-LanType");
        
        String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        
        customerService.addCustomerAndCar(jsonObj, adminId, lanType);
        return ApiResultI18n.success(lanType);
    }
    
    @RequestMapping(value = "/module/crm/deleteCustomerCar", method = RequestMethod.POST)
    public ApiResultI18n deleteCustomerCar(@RequestBody String params,
            HttpServletRequest request) {
        JSONObject jsonObj = JSONObject.fromObject(params);
        String lanType = request.getHeader("X-LanType");
        
        String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        
        Long customerId = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.CUSTOMER_ID))){
        	customerId = jsonObj.getLong(RequestParamConstant.CUSTOMER_ID);
        }
        
        Long carId = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.CAR_ID))){
        	carId = jsonObj.getLong(RequestParamConstant.CAR_ID);
        }
        
        customerService.delCustomerCar(adminId, lanType, customerId, carId);
        return ApiResultI18n.success(lanType);
    }

    @RequestMapping(value = "/module/crm/delCustomer", method = RequestMethod.POST)
    public ApiResultI18n delCustomer(@RequestBody String params,
            HttpServletRequest request) {
        JSONObject jsonObj = JSONObject.fromObject(params);
        String lanType = request.getHeader("X-LanType");
        
        String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        
        Long customerId = jsonObj.getLong(RequestParamConstant.CUSTOMER_ID);
        customerService.delCustomer(adminId, customerId);
        return ApiResultI18n.success(lanType);
    }

    @RequestMapping(value = "/module/crm/modifyCustomer", method = RequestMethod.POST)
    public ApiResultI18n modifyCustomer(@RequestBody String params,
            HttpServletRequest request) {
        JSONObject jsonObj = JSONObject.fromObject(params);
        String lanType = request.getHeader("X-LanType");
        
        String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        customerService.modifyCustomer(jsonObj, adminId, lanType);
        return ApiResultI18n.success(lanType);
    }
	
    @AuthRuleAnnotation("")
    @PostMapping("/admin/api/crm/customer/carList4Customer")
    public ApiResultI18n carList4Customer(@RequestBody String params,
            HttpServletRequest request) {
        JSONObject jsonObj = JSONObject.fromObject(params);
        String lanType = request.getHeader("X-LanType");
        
        String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        
        Long customerId = jsonObj.getLong(RequestParamConstant.CUSTOMER_ID);
        List<Car> result = customerService.carList4Customer(adminId, customerId);
        return ApiResultI18n.success(result, lanType);
    }
    
    
    @AuthRuleAnnotation("")
    @PostMapping("/admin/api/crm/customer/accountList4Customer")
    public ApiResultI18n accountList4Customer(@RequestBody String params,
            HttpServletRequest request) {
        JSONObject jsonObj = JSONObject.fromObject(params);
        String lanType = request.getHeader("X-LanType");
        
        String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        
        Long customerId = null;
        if (null != jsonObj.get(RequestParamConstant.CUSTOMER_ID)){
        	customerId = jsonObj.getLong(RequestParamConstant.CUSTOMER_ID);
        }
        Integer accountType = null;
        if (null != jsonObj.get(RequestParamConstant.ACCOUNT_TYPE)){
            accountType = jsonObj.getInt(RequestParamConstant.ACCOUNT_TYPE);
        }
        
        List<CustomerAccountResponse> result = customerService.accountList4Customer(adminId, customerId, accountType);
        return ApiResultI18n.success(result, lanType);
    }
    
    @AuthRuleAnnotation("")
    @PostMapping("/admin/api/crm/customer/account/queryList")
    public ApiResultI18n queryAccountList(@RequestBody String params,
            HttpServletRequest request) {
        JSONObject jsonObj = JSONObject.fromObject(params);
        String lanType = request.getHeader("X-LanType");
        
        String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        
        Long customerId = null;
        if (null != jsonObj.get(RequestParamConstant.CUSTOMER_ID)){
        	customerId = jsonObj.getLong(RequestParamConstant.CUSTOMER_ID);
        }
        Integer accountType = null;
        if (null != jsonObj.get(RequestParamConstant.ACCOUNT_TYPE)){
            accountType = jsonObj.getInt(RequestParamConstant.ACCOUNT_TYPE);
        }
        
        Map<String, Object> result = customerService.queryAccountList(adminId, customerId, accountType, ParamUtil.initPage(jsonObj));
        return ApiResultI18n.success(result, lanType);
    }
    
    @AuthRuleAnnotation("")
    @PostMapping("/admin/api/crm/customer/querySelectList")
    public ApiResultI18n querySelectList(@RequestBody String params,
            HttpServletRequest request) {
        //JSONObject jsonObj = JSONObject.fromObject(params);
        String lanType = request.getHeader("X-LanType");
        
        String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        
        List<CustomerSelect> result = customerService.findSelectObject(adminId, lanType);
        return ApiResultI18n.success(result, lanType);
    }
    
    @AuthRuleAnnotation("")
    @PostMapping("/admin/api/crm/customer/detailData")
    public ApiResultI18n detailData(@RequestBody String params,
            HttpServletRequest request) {
        JSONObject jsonObj = JSONObject.fromObject(params);
        String lanType = request.getHeader("X-LanType");
        
        
        Long id = jsonObj.getLong(RequestParamConstant.ID);
        Customer customer = customerService.selectByPrimaryKey(id);
        return ApiResultI18n.success(customer, lanType);
    }
}
