package com.saas.api.admin.service.crm;

import java.util.List;
import com.saas.api.common.entity.crm.CommodityAuto;


public interface CommodityAutoService {

	void addData(CommodityAuto record);

	void deleteByCommodityId(Long commodityId);
	
	void deleteById(Long id);
	
	List<CommodityAuto> findByCommodityId(Long commodityId);
	
}
