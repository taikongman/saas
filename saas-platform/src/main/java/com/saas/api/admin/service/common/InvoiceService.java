package com.saas.api.admin.service.common;

import java.util.List;
import java.util.Map;

import com.saas.api.admin.res.select.common.InvoiceSelect;
import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.common.Invoice;

public interface InvoiceService {
	
	Invoice findByPrimayKey(Integer id);
	
	Invoice findByName(String invoiceName);
	
	int insertData(Invoice record);
	
	int updateData(Invoice record);
	
	int deleteByPrimayKey(Integer id);
	
	Map<String, Object> getListData(Integer id, String lanType, Page page);
	
	List<Invoice> findByObject(Invoice record);
	
	List<InvoiceSelect> findSelectObject(Invoice record);
	
}
