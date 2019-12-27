package com.saas.api.admin.service.crm;

import java.util.List;
import java.util.Map;

import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.crm.SupplierBank;

public interface SupplierBankService {
	
	SupplierBank findByPrimayKey(Integer id);
	
	SupplierBank findByBankNo(Integer supplierId, String bankNo);
	
	int insertData(SupplierBank record);
	
	int updateData(SupplierBank record);
	
	int deleteByPrimayKey(Integer id);
	
	int deleteBySupplierId(Integer supplierId);
	
	Map<String, Object> getListData(Integer supplierId, String accountName, 
			String taxNo, String bankNo, String phone, Page page);
	
	List<SupplierBank> findByObject(SupplierBank record);
	
	
}
