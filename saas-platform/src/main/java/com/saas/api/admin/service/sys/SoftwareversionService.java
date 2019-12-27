package com.saas.api.admin.service.sys;


import java.util.Map;
import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.sys.Softwareversion;

public interface SoftwareversionService {

	Map<String, Object> getListData(String appname, String osname, String version, Page page);

	Softwareversion findById(Integer id);
	
	Softwareversion findCheckVersion(String appname, String osname, String version);
	
	int insertData(Softwareversion config);
	
	int updateData(Softwareversion config);
	
	int deleteById(Integer id);
	

}
