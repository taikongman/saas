package com.saas.api.admin.aspect;

import com.saas.api.admin.annotation.AuthRuleAnnotation;
import com.saas.api.admin.service.sys.auth.AuthLoginService;
import com.saas.api.common.constant.ResponseCodeI18n;
import com.saas.api.common.exception.JsonException;
import com.saas.api.common.util.ApiResultI18n;
import com.saas.api.common.util.JwtUtils;

import io.jsonwebtoken.Claims;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 登录验证 AOP
 */
@Aspect
@Component
public class AuthorizeAspect {

    @Resource
    private AuthLoginService authLoginService;

    @Pointcut("@annotation(com.saas.api.admin.annotation.AuthRuleAnnotation)")
    public void adminLoginVerify() {
    	
    }

    /**
     * * 登录验证
     *
     * @param joinPoint
     */
    @Before("adminLoginVerify()")
    public void doAdminAuthVerify(JoinPoint joinPoint) {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            ApiResultI18n i18nReuslt = ApiResultI18n.failure(ResponseCodeI18n.NOT_NETWORK.getCode(), 
        			ResponseCodeI18n.NOT_NETWORK.getMessage(), "zh_cn");
            throw new JsonException(i18nReuslt.getCode(),i18nReuslt.getMessage());
        }
        HttpServletRequest request = attributes.getRequest();
        
        String lanType = request.getHeader("X-LanType");

        String id = request.getHeader("X-AdminId");
        Long adminId = null;
        try {
            adminId = Long.valueOf(id);
        }catch (Exception e) {
        	ApiResultI18n i18nReuslt = ApiResultI18n.failure(ResponseCodeI18n.LOGIN_VERIFY_FALL.getCode(), 
        			ResponseCodeI18n.LOGIN_VERIFY_FALL.getMessage(), lanType);
            throw new JsonException(i18nReuslt.getCode(),i18nReuslt.getMessage());
        }

        String token = request.getHeader("X-Token");
        if (token == null) {
        	ApiResultI18n i18nReuslt = ApiResultI18n.failure(ResponseCodeI18n.LOGIN_VERIFY_FALL.getCode(), 
        			ResponseCodeI18n.LOGIN_VERIFY_FALL.getMessage(), lanType);
            throw new JsonException(i18nReuslt.getCode(),i18nReuslt.getMessage());
        }
        
        // 判断是否进行权限验证
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //从切面中获取当前方法
        Method method = signature.getMethod();
        //得到了方,提取出他的注解
        AuthRuleAnnotation action = method.getAnnotation(AuthRuleAnnotation.class);

        if(action.value() != null && action.value().length() > 0) {
        	// 验证 token
            Claims claims = JwtUtils.parse(token);
            if (claims == null) {
            	ApiResultI18n i18nReuslt = ApiResultI18n.failure(ResponseCodeI18n.LOGIN_VERIFY_FALL.getCode(), 
            			ResponseCodeI18n.LOGIN_VERIFY_FALL.getMessage(), lanType);
                throw new JsonException(i18nReuslt.getCode(),i18nReuslt.getMessage());
            }
            Long jwtAdminId = Long.valueOf(claims.get("admin_id").toString());
            if (adminId.compareTo(jwtAdminId) != 0) {
            	ApiResultI18n i18nReuslt = ApiResultI18n.failure(ResponseCodeI18n.LOGIN_VERIFY_FALL.getCode(), 
            			ResponseCodeI18n.LOGIN_VERIFY_FALL.getMessage(), lanType);
                throw new JsonException(i18nReuslt.getCode(),i18nReuslt.getMessage());
            }

            
            // 进行权限验证
            authRuleVerify(action.value(), adminId, lanType);
        }
        
    }

    /**
     * * 权限验证
     * @param authRule
     */
    private void authRuleVerify(String authRule, Long adminId, String lanType) {
    	System.out.println("authRuleVerify=" + authRule + "=" + adminId + "=" + lanType);
    	//log.debug("authRuleVerify=" + authRule + "=" + adminId + "=" + lanType);
        if (authRule != null && authRule.length() > 0) {

            List<String> authRules = authLoginService.listMenuUrlByAdminId(adminId);
            // admin 为最高权限
            for (String item : authRules) {
                if (item.equals("admin") || item.equals(authRule)) {
                    return;
                }
            }
            ApiResultI18n i18nReuslt = ApiResultI18n.failure(ResponseCodeI18n.AUTH_FAILED.getCode(), 
        			ResponseCodeI18n.AUTH_FAILED.getMessage(), lanType);
            throw new JsonException(i18nReuslt.getCode(),i18nReuslt.getMessage());
        }

    }

}
