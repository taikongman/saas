package com.saas.api.admin.controller.sys.auth;

import com.saas.api.admin.req.sys.auth.LoginRequest;
import com.saas.api.admin.res.sys.auth.MenuResponse;
import com.saas.api.admin.service.sys.auth.UserService;
import com.saas.api.admin.service.sys.auth.AuthLoginService;
import com.saas.api.common.constant.RequestParamConstant;
import com.saas.api.common.constant.ResponseCodeI18n;
import com.saas.api.common.entity.sys.auth.User;
import com.saas.api.common.util.ApiResultI18n;
import com.saas.api.common.util.IpUtils;
import com.saas.api.common.util.JwtUtils;
import com.saas.api.common.util.PasswordUtils;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 登录相关
 */
@RestController
@Slf4j
public class LoginController {

    @Autowired
    private AuthLoginService authLoginService;

    @Autowired
    private UserService userService;

    /**
     * * 用户登录
     * @return
     */
    @PostMapping(value = "/admin/system/auth/login/index")
    public ApiResultI18n index(@RequestBody LoginRequest loginRequest,
                              HttpServletRequest request) {
    	log.error(new StringBuffer(loginRequest.getUserName()).append("=").append(loginRequest.getPassword()).append("=").append(loginRequest.getLanType()).toString());
        if (loginRequest.getLanType() == null
        		|| loginRequest.getPassword() == null 
        		|| loginRequest.getUserName() == null) {
        	return ApiResultI18n.failure(ResponseCodeI18n.PARAM_ERROR.getCode(), 
        			ResponseCodeI18n.PARAM_ERROR.getMessage(),loginRequest.getLanType());
        }

        User authUser = userService.findByName(loginRequest.getUserName());
        if (authUser == null) {
        	return ApiResultI18n.failure(ResponseCodeI18n.USER_IS_NOT_EXIST.getCode(), 
        			ResponseCodeI18n.USER_IS_NOT_EXIST.getMessage(),loginRequest.getLanType());
        }

        if (!PasswordUtils.authAdminPwd(loginRequest.getPassword()).equals(authUser.getPassword())) {
        	return ApiResultI18n.failure(ResponseCodeI18n.USERNAME_OR_PASSWORD_ERROR.getCode(), 
        			ResponseCodeI18n.USERNAME_OR_PASSWORD_ERROR.getMessage(),loginRequest.getLanType());
        }
        if (authUser.getStatus() != 1){
        	return ApiResultI18n.failure(ResponseCodeI18n.USER_HAVE_NO_PRIVILEDGE.getCode(), 
        			ResponseCodeI18n.USER_HAVE_NO_PRIVILEDGE.getMessage(),loginRequest.getLanType());
        }

        // 更新登录状态
        User userUpdate = new User();
        userUpdate.setAdminId(authUser.getAdminId());
        userUpdate.setLastLoginTime(new Date());
        userUpdate.setLastLoginIp(IpUtils.getIpAddr(request));
        userService.updateData(userUpdate);

        Map<String, Object> claims = new HashMap<>();
        claims.put("admin_id", authUser.getAdminId());
        String token = JwtUtils.createToken(claims, 86400L); // 一天后过期

        authUser.setPassword(null);
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("adminUser", authUser);
        return ApiResultI18n.success(result, loginRequest.getLanType());
    }
    
    /**
     * * 用户登录
     * @return
     */
    @PostMapping(value = "/wechat/small/program/auth/login")
    public ApiResultI18n wechatLogin(@RequestBody LoginRequest loginRequest,
                              HttpServletRequest request) {
    	log.error(new StringBuffer("wechatLogin=").append(loginRequest.getUserName()).append("=").append(loginRequest.getPassword()).append("=").append(loginRequest.getLanType()).toString());
        if (loginRequest.getLanType() == null
        		|| loginRequest.getPassword() == null 
        		|| loginRequest.getUserName() == null) {
        	return ApiResultI18n.failure(ResponseCodeI18n.PARAM_ERROR.getCode(), 
        			ResponseCodeI18n.PARAM_ERROR.getMessage(),loginRequest.getLanType());
        }

        User authUser = userService.findByName(loginRequest.getUserName());
        if (authUser == null) {
        	return ApiResultI18n.failure(ResponseCodeI18n.USER_IS_NOT_EXIST.getCode(), 
        			ResponseCodeI18n.USER_IS_NOT_EXIST.getMessage(),loginRequest.getLanType());
        }

        if (!PasswordUtils.authAdminPwd(loginRequest.getPassword()).equals(authUser.getPassword())) {
        	return ApiResultI18n.failure(ResponseCodeI18n.USERNAME_OR_PASSWORD_ERROR.getCode(), 
        			ResponseCodeI18n.USERNAME_OR_PASSWORD_ERROR.getMessage(),loginRequest.getLanType());
        }
        if (authUser.getStatus() != 1){
        	return ApiResultI18n.failure(ResponseCodeI18n.USER_HAVE_NO_PRIVILEDGE.getCode(), 
        			ResponseCodeI18n.USER_HAVE_NO_PRIVILEDGE.getMessage(),loginRequest.getLanType());
        }

        // 更新登录状态
        User userUpdate = new User();
        userUpdate.setAdminId(authUser.getAdminId());
        userUpdate.setLastLoginTime(new Date());
        userUpdate.setLastLoginIp(IpUtils.getIpAddr(request));
        userService.updateData(userUpdate);

        Map<String, Object> claims = new HashMap<>();
        claims.put("admin_id", authUser.getAdminId());
        String token = JwtUtils.createToken(claims, 2592000L); // 一天后过期

        Long ttl = 2592000L;
        Long nowMillis = System.currentTimeMillis(); //生成JWT的时间
        Long expMillis = nowMillis + ttl * 1000;
        Date exp = new Date(expMillis);
        
        authUser.setPassword(null);
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("expiryDate", exp);
        result.put("adminUser", authUser);
        return ApiResultI18n.success(result, loginRequest.getLanType());
    }

    /**
     * * 获取登录用户信息
     * @return  @AuthRuleAnnotation("admin/system/auth/login/userInfo")
     */
    @PostMapping("/admin/system/auth/login/userInfo")
    public ApiResultI18n userInfo(@RequestBody String params, 
    		HttpServletRequest request) {
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	String lanType = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.LAN_TYPE))){
        	lanType = jsonObj.getString(RequestParamConstant.LAN_TYPE);
        }
        
        String xAdminId = request.getHeader("X-AdminId");
        Long adminId = Long.valueOf(xAdminId);

        User authUser = userService.findByPrimayKey(adminId);
        if (authUser == null) {
        	return ApiResultI18n.failure(ResponseCodeI18n.USER_IS_NOT_EXIST.getCode(), 
        			ResponseCodeI18n.USER_IS_NOT_EXIST.getMessage(), lanType);
        }

        List<MenuResponse> menuList = authLoginService.listRuleByAdminId(authUser.getAdminId());
        
        Map<String, Object> result = new HashMap<>();
        result.put("adminUser", authUser);
        result.put("dataList", menuList);
        return ApiResultI18n.success(result, lanType);
    }

    /**
     * 登出
     * @return
     */
    @PostMapping("/admin/system/auth/login/out")
    public ApiResultI18n logout(@RequestBody String params){
    	log.debug(params);
    	JSONObject jsonObj = JSONObject.fromObject(params);
    	
    	String lanType = null;
        if (!StringUtils.isEmpty(jsonObj.get(RequestParamConstant.LAN_TYPE))){
        	lanType = jsonObj.getString(RequestParamConstant.LAN_TYPE);
        }
        
        return ApiResultI18n.success(lanType);
    }


}
