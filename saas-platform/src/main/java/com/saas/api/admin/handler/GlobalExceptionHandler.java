package com.saas.api.admin.handler;

import com.saas.api.common.constant.ResponseCodeI18n;
import com.saas.api.common.exception.JsonException;
import com.saas.api.common.exception.SaasException;
import com.saas.api.common.util.ApiResultI18n;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 错误回调
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    
    // 拦截API异常
    @ExceptionHandler(value = JsonException.class)
    public ApiResultI18n handlerJsonException(JsonException e) {
        // 返回对应的错误信息
        return ApiResultI18n.failure(e.getCode(), e.getMessage());
    }

    // 拦截API异常
    @ExceptionHandler(value = RuntimeException.class)
    public ApiResultI18n handlerRuntimeException(RuntimeException e) {
        log.error(e.getMessage());
        e.printStackTrace();
        // 返回对应的错误信息
        ApiResultI18n i18nReuslt = ApiResultI18n.failure(ResponseCodeI18n.NOT_NETWORK.getCode(), 
    			ResponseCodeI18n.NOT_NETWORK.getMessage(), "zh_cn");
        
        return i18nReuslt;
    }
    
    // 拦截API异常
    @ExceptionHandler(value = SaasException.class)
    public ApiResultI18n handlerRuntimeException(SaasException e) {
    	return ApiResultI18n.failure(e.getCode(), e.getMsg());
    }
    
}
