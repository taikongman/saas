package com.saas.api.admin.service.sys;


import java.util.Map;
import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.sys.Config;

public interface ConfigService {

	Map<String, Object> getListData(String keyName, Page page);

	Config findById(Integer id);
	
	Config findByKeyName(String keyName);
	
	int insertData(Config config);
	
	int updateData(Config config);
	
	int deleteById(Integer id);
	

}
