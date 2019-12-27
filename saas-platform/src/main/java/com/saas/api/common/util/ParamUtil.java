package com.saas.api.common.util;

import java.util.Map;

import com.saas.api.common.constant.CommonConstant;
import com.saas.api.common.constant.RequestParamConstant;
import com.saas.api.common.dto.Page;

import net.sf.json.JSONObject;

public class ParamUtil {
	
	public static Page initPage(JSONObject params) {
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
	
	public static Map<String, Object> initMapParam(Map<String, Object> paramsMap, JSONObject jsonObj, String lanType){
    	Integer pageNo = CommonConstant.DEFAULT_PAGE_NO;
        Integer pageSize = CommonConstant.DEFAULT_PAGE_SIZE;
        
        if (null != jsonObj.get(RequestParamConstant.PAGE_NO)){
            pageNo = jsonObj.getInt(RequestParamConstant.PAGE_NO);
        }
        if (null != jsonObj.get(RequestParamConstant.PAGE_SIZE)){
            pageSize = jsonObj.getInt(RequestParamConstant.PAGE_SIZE);
        }
    	
        paramsMap.put(RequestParamConstant.START_INDEX, (pageNo-1) * pageSize);
        paramsMap.put(RequestParamConstant.PAGE_SIZE, pageSize);
        paramsMap.put(RequestParamConstant.LAN_TYPE, lanType);
        
        return paramsMap;
    }

}
