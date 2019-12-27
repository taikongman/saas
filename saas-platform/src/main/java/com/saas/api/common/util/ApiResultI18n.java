package com.saas.api.common.util;

import java.io.IOException;
import java.io.Serializable;

import com.saas.api.common.constant.ResponseCodeI18n;
import com.saas.api.common.i18n.I18nMessageUtil;
import com.saas.api.common.i18n.LanguageEnum;

import lombok.Data;

/**
 * @Description: 多语言, 国际化接口返回结果封装
 * @Author: 九尾
 * @Date: 2019/11/15
 */
@Data
public class ApiResultI18n implements Serializable {
	
	private static final long serialVersionUID = 4518290031778225230L;
	
    /**
     * 返回码，1000 正常
     */
    private int code = 1000;
    
    /**
     * 返回信息
     */
    private String message = "成功";
    
    /**
     * 返回数据
     */
    private Object data;
    
    /**
     * api 返回结果
     */
    protected ApiResultI18n() {
    	
    }

    /**
     * api 返回结果,区分多语言
     * @param language 语言类型,eg: en_us 表示美式英文
     */
    public ApiResultI18n(String language){
        this.code = ResponseCodeI18n.SUCCESS.getCode();
        language = LanguageEnum.getLanguageType(language);
        try {
            this.message = I18nMessageUtil.getMessage(language,ResponseCodeI18n.SUCCESS.getMessage(),"SUCCESS");
        } catch (IOException e) {
            this.message = "SUCCESS";
        }
    }

    /**
     * 获取成功状态结果,区分多语言(默认简体中文)
     *
     * @param language 语言类型,eg: en_us 表示美式英文
     * @return
     */
    public static ApiResultI18n success(String language) {
        return success(null, language);
    }

    /**
     * 获取成功状态结果,区分多语言(默认简体中文)
     * @param data 返回数据
     * @param language 语言类型,eg: en_us 表示美式英文
     * @return
     */
    public static ApiResultI18n success(Object data, String language) {
        ApiResultI18n result = new ApiResultI18n(language);
        result.setData(data);
        return result;
    }

    /**
     * 获取失败状态结果,区分多语言(默认简体中文)
     * @param language 语言类型,eg: en_us 表示美式英文
     * @return
     */
    public static ApiResultI18n failure(String language) {
        return failure(ResponseCodeI18n.FAIL.getCode(), ResponseCodeI18n.FAIL.getMessage(), null, language);
    }

    /**
     * 获取失败结果,区分多语言(默认中文)
     * @param responseCodeI18n 返回码
     * @param language 语言类型
     * @return
     */
    public static ApiResultI18n failure(ResponseCodeI18n responseCodeI18n, String language) {
        return failure(responseCodeI18n.getCode(), responseCodeI18n.getMessage(), null, language);
    }

    /**
     * 获取失败状态结果,区分多语言(默认中文)
     * @param code 返回状态码
     * @param msg 错误信息
     * @param language 语言类型,eg: en 表示英文
     * @return
     */
    public static ApiResultI18n failure(int code, String msg, String language) {
        return failure(code ,msg, null, language);
    }

    /**
     * * 获取失败返回结果,区分多语言(默认中文)
     * @param code 错误码
     * @param msg 错误信息
     * @param data 返回结果
     * @param language 语言类型,eg: en 表示英文
     * @return
     */
    public static ApiResultI18n failure(int code, String message, Object data, String language) {
        ApiResultI18n result = new ApiResultI18n(language);
        language = LanguageEnum.getLanguageType(language);
        try {
        	message = I18nMessageUtil.getMessage(language, message, message);
        } catch (IOException e) {
        	message = "Error";
        }
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        if (data instanceof String) {
            String m = (String) data;
            if (!m.matches("^.*error$")) {
                m += "error";
            }
        }
        return result;
    }
    
    public static ApiResultI18n failure(int code, String message) {
    	ApiResultI18n result = new ApiResultI18n();
    	
    	result.setCode(code);
        result.setMessage(message);
        return result;
    }

}
