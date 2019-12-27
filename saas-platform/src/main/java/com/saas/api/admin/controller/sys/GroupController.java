package com.saas.api.admin.controller.sys;


import com.saas.api.admin.annotation.AuthRuleAnnotation;
import com.saas.api.admin.res.select.sys.GroupSelect;
import com.saas.api.admin.service.sys.GroupService;
import com.saas.api.admin.service.sys.auth.UserService;
import com.saas.api.common.constant.CommonConstant;
import com.saas.api.common.constant.RequestParamConstant;
import com.saas.api.common.constant.ResponseCodeI18n;
import com.saas.api.common.dto.Page;
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
 * @Description: 组织相关接口
 * @Author: 徐未
 * @Date: 2019/11/12
 */
@RestController
@Slf4j
public class GroupController {

    @Autowired
    private GroupService groupService;
    
    @Resource
    private UserService userService;

    /**
     * 查询列表
     * @return @AuthRuleAnnotation("admin/system/group/queryList")
     */
    @PostMapping(value = "/admin/system/group/queryList")
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
        
        String lanType = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.LAN_TYPE))){
        	lanType = jsonObj.getString(RequestParamConstant.LAN_TYPE);
        }
    	
    	Page page = new Page(pageNo, pageSize);
    	Map<String, Object> result = groupService.getListData(groupId, lanType, page);
    	
        return ApiResultI18n.success(result, lanType);
    }

    /**
     * 查询下拉列表
     * @return @AuthRuleAnnotation("admin/system/group/querySelectList")
     */
    @PostMapping("/admin/system/group/querySelectList")
    public ApiResultI18n listSelectData(@RequestBody String params,
    		HttpServletRequest request) {
    	log.debug(params);
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	
    	String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);
        User user = userService.findByPrimayKey(adminId);
        
        Integer groupId = user.getGroupId();
        
        String lanType = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.LAN_TYPE))){
        	lanType = jsonObj.getString(RequestParamConstant.LAN_TYPE);
        }
        Group record =  new Group();
        record.setGroupId(groupId);
        record.setLanType(lanType);
    	List<Group> serviceList = groupService.findByObject(record);
    	List<GroupSelect> resultList = new ArrayList<GroupSelect>();
    	for(Group forTemp : serviceList) {
    		GroupSelect selecttmp = new GroupSelect();
    		selecttmp.copyData(forTemp);
    		resultList.add(selecttmp);
    	}
    	
    	Map<String, Object> result = new HashMap<>();
    	result.put("dataList", resultList);
    	
        return ApiResultI18n.success(result, lanType);
    }

    /**
     * 添加数据
     * @return
     */
    @AuthRuleAnnotation("admin/system/group/addData")
    @PostMapping("/admin/system/group/addData")
    public ApiResultI18n insertData(@RequestBody Group group){
    	Group check =  groupService.findByName(group.getGroupName());
    	if(check != null) {
    		return ApiResultI18n.failure(ResponseCodeI18n.GROUP_IS_EXIST.getCode(), 
    				ResponseCodeI18n.GROUP_IS_EXIST.getMessage(), group.getLanType());
    	}

    	group.setCreateTime(new Date());
    	group.setStatus(CommonConstant.INT_ONE);
    	groupService.insertData(group);
    	
        return ApiResultI18n.success(group.getLanType());
    }

    /**
     * 修改信息
     * @return
     */
    @AuthRuleAnnotation("admin/system/group/updateData")
    @PostMapping("/admin/system/group/updateData")
    public ApiResultI18n updateData(@RequestBody Group group) {
    	Group check =  groupService.findByName(group.getGroupName());
    	if(check != null) {
    		if(check.getGroupId().intValue() != group.getGroupId().intValue()) {
				return ApiResultI18n.failure(ResponseCodeI18n.GROUP_IS_EXIST.getCode(), 
						ResponseCodeI18n.GROUP_IS_EXIST.getMessage(), group.getLanType());
			}
    	}
    	
    	group.setUpdateTime(new Date());
    	groupService.updateData(group);

    	return ApiResultI18n.success(group.getLanType());
    }
    
    /**
     * 删除信息
     * @return
     */
    @AuthRuleAnnotation("admin/system/group/deleteData")
    @PostMapping("/admin/system/group/deleteData")
    public ApiResultI18n deleteById(@RequestBody String params) {
    	log.debug(params);
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	
    	String lanType = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.LAN_TYPE))){
        	lanType = jsonObj.getString(RequestParamConstant.LAN_TYPE);
        }
        
    	Integer groupId = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.GROUP_ID))){
        	groupId = jsonObj.getInt(RequestParamConstant.GROUP_ID);
        }else {
        	return ApiResultI18n.failure(ResponseCodeI18n.PARAM_ERROR.getCode(), ResponseCodeI18n.PARAM_ERROR.getMessage(),lanType);
        }
        
        groupService.deleteByPrimayKey(groupId);

    	return ApiResultI18n.success(lanType);
    }

}
