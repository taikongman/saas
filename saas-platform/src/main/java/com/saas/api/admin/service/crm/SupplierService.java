package com.saas.api.admin.service.crm;

import java.util.List;
import java.util.Map;

import com.saas.api.admin.res.select.crm.SupplierSelect;
import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.crm.Supplier;

import net.sf.json.JSONArray;

public interface SupplierService {
	
	Supplier findByPrimayKey(Integer supplierId);
	
	Supplier findByName(Long adminId, String supplierName, String lanType);
	
	int insertData(Supplier record, JSONArray supplierBankList);
	
	int updateData(Supplier record, JSONArray supplierBankList);
	
	int deleteByPrimayKey(Integer supplierId);
	
	Map<String, Object> getListData(Integer supplierId, String supplierName, Long adminId, String lanType, Page page);
	
	List<Supplier> findByObject(Supplier record);
	
	List<SupplierSelect> findSelectObject(Long adminId, Supplier record);
	
}
