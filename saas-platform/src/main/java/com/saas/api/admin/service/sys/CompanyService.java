package com.saas.api.admin.service.sys;


import java.util.List;
import java.util.Map;

import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.sys.Company;

public interface CompanyService {

	Map<String, Object> getListData(Integer groupId, Integer companyId, String lanType, Page page);
	
	List<Company> findByObject(Company company);

	Company findByPrimayKey(Integer companyId);
	
	Company findByName(String companyName);
	
	int insertData(Company company);
	
	int updateData(Company company);
	
	int deleteByPrimayKey(Integer companyId);
	
	
}
