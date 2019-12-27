package com.saas.api.admin.service.crm;

import java.util.List;
import java.util.Map;

import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.crm.Commodity;

import net.sf.json.JSONArray;

public interface CommodityService {

	Map<String, Object> commodityList(String commodityCode, String commodityName, Integer categoryId, Long adminId, String lanType, Page page);

	void addCommodity(Commodity commodity, JSONArray commodityAutoList);

	void delCommodity(Long commodityId);

	void modifyCommodity(Commodity commodity, JSONArray commodityAutoList);
	
	Commodity selectByPrimaryKey(Long commodityId);
	
	Commodity findByCommodityName(Long adminId, String commodityName);
	
	List<Commodity> findBySelectObject(Long adminId, String lanType);
	
	List<Commodity> findByObject(Commodity record);
	
	Integer countByCode(Integer groupId, Integer companyId, Integer departmentId, Long adminId, 
			String lanType, 
    		String commodityCode);
	
}
