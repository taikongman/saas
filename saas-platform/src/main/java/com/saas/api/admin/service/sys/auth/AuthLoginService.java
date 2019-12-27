package com.saas.api.admin.service.sys.auth;

import java.util.List;

import com.saas.api.admin.res.sys.auth.MenuResponse;

public interface AuthLoginService {

	List<MenuResponse> listRuleByAdminId(Long adminId);
	
	List<String> listMenuUrlByAdminId(Long adminId);

}
