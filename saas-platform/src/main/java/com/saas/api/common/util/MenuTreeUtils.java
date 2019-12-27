package com.saas.api.common.util;

import org.springframework.beans.BeanUtils;

import com.saas.api.admin.res.sys.auth.MenuResponse;
import com.saas.api.admin.res.sys.auth.AuthPermissionRuleTreeResponse;
import com.saas.api.common.constant.CommonConstant;
import com.saas.api.common.entity.sys.auth.Menu;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限规则生成树形节点工具类
 */
public class MenuTreeUtils {
	
	public static String iconFirst = "│";
	
	public static String iconSecond = "├";
	
	public static String iconThird = "└";

    /**
     * 多维数组
     */
    public static List<MenuResponse> merge(List<Menu> authPermissionRuleList,
                                                              Integer pid) {

        List<MenuResponse> menuMergeResponseList = new ArrayList<MenuResponse>();
        for (Menu forTemp : authPermissionRuleList) {
            MenuResponse menuResponse = new MenuResponse();
            BeanUtils.copyProperties(forTemp, menuResponse);
            if (pid.equals(forTemp.getPid())) {
            	menuResponse.setChildren(merge(authPermissionRuleList, forTemp.getMenuId()));
            	menuMergeResponseList.add(menuResponse);
            }
        }

        return menuMergeResponseList;
    }
    
    /**
     * tree list
     */
    public static List<AuthPermissionRuleTreeResponse> tree(List<Menu> authPermissionRuleList,
                                                              Long pid) {
    	
    	List<AuthPermissionRuleTreeResponse> resultList = new ArrayList<AuthPermissionRuleTreeResponse>();
    	for(AuthPermissionRuleTreeResponse forvalue : resultList) {
    		AuthPermissionRuleTreeResponse authTreeRes = new AuthPermissionRuleTreeResponse();
    		BeanUtils.copyProperties(forvalue, authTreeRes);
    		if(authTreeRes.getLevel() == CommonConstant.INT_ONE) {
    			authTreeRes.setHtml("&nbsp;");
    		}else if(authTreeRes.getLevel() == CommonConstant.INT_TWO) {
    			authTreeRes.setHtml("&nbsp;&nbsp;├&nbsp;");
    		}else if(authTreeRes.getLevel() == CommonConstant.INT_THREE) {
    			authTreeRes.setHtml("&nbsp;&nbsp;│&nbsp;├&nbsp;");
    		}
    		
    		resultList.add(authTreeRes);
    	}
    	
    	return resultList;
    }
    

}
