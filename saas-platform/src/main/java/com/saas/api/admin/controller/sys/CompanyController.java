package com.saas.api.admin.controller.sys;

import com.saas.api.admin.annotation.AuthRuleAnnotation;
import com.saas.api.admin.res.select.sys.CompanySelect;
import com.saas.api.admin.service.sys.CompanyService;
import com.saas.api.admin.service.sys.GroupService;
import com.saas.api.admin.service.sys.auth.UserService;
import com.saas.api.common.constant.CommonConstant;
import com.saas.api.common.constant.RequestParamConstant;
import com.saas.api.common.constant.ResponseCodeI18n;
import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.sys.Company;
import com.saas.api.common.entity.sys.Group;
import com.saas.api.common.entity.sys.auth.User;
import com.saas.api.common.util.ApiResultI18n;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Description: 公司相关接口
 * @Author: 徐未
 * @Date: 2019/11/12
 */
@RestController
@Slf4j
public class CompanyController {
	
	@Autowired
    private GroupService groupService;

    @Autowired
    private CompanyService companyService;
    
    @Resource
    private UserService userService;

    /**
     * * 查询列表
     * @return @AuthRuleAnnotation("admin/system/company/queryList")
     */
    @PostMapping(value = "/admin/system/company/queryList")
    public ApiResultI18n listData(@RequestBody String params,
            HttpServletRequest request) {
    	log.debug(params);
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	
    	Integer pageNo = 0;
        Integer pageSize = 20;
        
        if (null != jsonObj.get(RequestParamConstant.PAGE_NO)){
            pageNo = jsonObj.getInt(RequestParamConstant.PAGE_NO);
        }
        if (null != jsonObj.get(RequestParamConstant.PAGE_SIZE)){
            pageSize = jsonObj.getInt(RequestParamConstant.PAGE_SIZE);
        }
        
        String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        User user = userService.findByPrimayKey(adminId);
        
        Integer groupId = user.getGroupId();
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.GROUP_ID))){
        	groupId = jsonObj.getInt(RequestParamConstant.GROUP_ID);
        }
        
        Integer companyId = user.getCompanyId();
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.COMPANY_ID))){
        	companyId = jsonObj.getInt(RequestParamConstant.COMPANY_ID);
        }
        String lanType = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.LAN_TYPE))){
        	lanType = jsonObj.getString(RequestParamConstant.LAN_TYPE);
        }
    	
    	Page page = new Page(pageNo, pageSize);
    	Map<String, Object> result = companyService.getListData(groupId, companyId, lanType, page);
    	return ApiResultI18n.success(result, lanType);
    }

    /**
     * 查询下拉列表
     * @return @AuthRuleAnnotation("admin/system/company/querySelectList")
     */
    @PostMapping("/admin/system/company/querySelectList")
    public ApiResultI18n listSelectData(@RequestBody String params,
            HttpServletRequest request) {
    	log.debug(params);
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	
    	String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        User user = userService.findByPrimayKey(adminId);
    	Integer groupId = user.getGroupId();
    	if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.GROUP_ID))){
        	groupId = jsonObj.getInt(RequestParamConstant.GROUP_ID);
        }
    	
        Integer companyId = user.getCompanyId();
        
        String lanType = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.LAN_TYPE))){
        	lanType = jsonObj.getString(RequestParamConstant.LAN_TYPE);
        }
        
        Company record = new Company();
        record.setGroupId(groupId);
        record.setCompanyId(companyId);
        record.setLanType(lanType);
        
    	List<Company> serviceList = companyService.findByObject(record);
    	List<CompanySelect> resultList = new ArrayList<CompanySelect>();
    	for(Company forTemp : serviceList) {
    		CompanySelect selecttmp = new CompanySelect();
    		selecttmp.copyData(forTemp);
    		resultList.add(selecttmp);
    	}
    	
    	Map<String, Object> result = new HashMap<>();
    	result.put("dataList", resultList);
        return ApiResultI18n.success(result, lanType);
    }

    /**
     * * 添加数据
     * @return
     */
    @AuthRuleAnnotation("admin/system/company/addData")
    @PostMapping("/admin/system/company/addData")
    public ApiResultI18n insertData(@RequestBody Company company){
    	Company check = companyService.findByName(company.getCompanyName());
    	if(check != null) {
    		return ApiResultI18n.failure(ResponseCodeI18n.COMPANY_IS_EXIST.getCode(), 
    				ResponseCodeI18n.COMPANY_IS_EXIST.getMessage(), company.getLanType());
    	}
    	
    	Group checkGroup = groupService.findByPrimayKey(company.getGroupId());
    	if(checkGroup == null) {
    		return ApiResultI18n.failure(ResponseCodeI18n.GROUP_IS_NOT_EXIST.getCode(), 
    				ResponseCodeI18n.GROUP_IS_NOT_EXIST.getMessage(), company.getLanType());
    	}
    	
    	company.setGroupName(checkGroup.getGroupName());
    	company.setCreateTime(new Date());
    	company.setStatus(CommonConstant.INT_ONE);
    	companyService.insertData(company);
    	
    	return ApiResultI18n.success(company.getLanType());
    }

    /**
     * * 修改信息
     * @return
     */
    @AuthRuleAnnotation("admin/system/company/updateData")
    @PostMapping("/admin/system/company/updateData")
    public ApiResultI18n updateData(@RequestBody Company company) {
    	Company check = companyService.findByName(company.getCompanyName());
    	if(check != null) {
    		if(check.getCompanyId().intValue() != company.getCompanyId().intValue()) {
				return ApiResultI18n.failure(ResponseCodeI18n.COMPANY_IS_EXIST.getCode(), 
						ResponseCodeI18n.COMPANY_IS_EXIST.getMessage(), company.getLanType());
			}
    	}
    	
    	company.setUpdateTime(new Date());
    	companyService.updateData(company);

    	return ApiResultI18n.success(company.getLanType());
    }
    
    /**
     * * 删除信息
     * @return
     */
    @AuthRuleAnnotation("admin/system/company/deleteData")
    @PostMapping("/admin/system/company/deleteData")
    public ApiResultI18n deleteById(@RequestBody String params) {
    	log.debug(params);
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	
    	String lanType = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.LAN_TYPE))){
        	lanType = jsonObj.getString(RequestParamConstant.LAN_TYPE);
        }
        
    	Integer companyId = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.COMPANY_ID))){
        	companyId = jsonObj.getInt(RequestParamConstant.COMPANY_ID);
        }else {
        	return ApiResultI18n.failure(ResponseCodeI18n.PARAM_ERROR.getCode(), 
        			ResponseCodeI18n.PARAM_ERROR.getMessage(),lanType);
        }
        
    	companyService.deleteByPrimayKey(companyId);

    	return ApiResultI18n.success(lanType);
    }

}
