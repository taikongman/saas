package com.saas.api.admin.service.common.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.saas.api.admin.res.select.common.InvoiceSelect;
import com.saas.api.admin.service.common.InvoiceService;
import com.saas.api.common.dao.common.InvoiceDao;
import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.common.Invoice;

@Service
public class InvoiceServiceImpl implements InvoiceService {

	@Resource
    private InvoiceDao invoiceDao;

	@Override
	public Invoice findByPrimayKey(Integer id) {
		return invoiceDao.findById(id);
	}

	@Override
	public Invoice findByName(String invoiceName) {
		return invoiceDao.findByName(invoiceName);
	}

	@Override
	public int insertData(Invoice record) {
		record.setCreateTime(new Date());
		return invoiceDao.insertData(record);
	}

	@Override
	public int updateData(Invoice record) {
		record.setUpdateTime(new Date());
		return invoiceDao.updateData(record);
	}

	@Override
	public int deleteByPrimayKey(Integer id) {
		return invoiceDao.deleteById(id);
	}

	@Override
	public Map<String, Object> getListData(Integer id, String lanType, Page page) {
		
		List<Invoice> resultList = invoiceDao.listData(id, lanType, page.getStartIndex(), page.getPageSize());
		Integer total = invoiceDao.countData(id, lanType);

		Map<String, Object> result = new HashMap<>();
		result.put("dataList", resultList);
        result.put("total", total);
        return result;
	}

	@Override
	public List<Invoice> findByObject(Invoice record) {
		return invoiceDao.findByObject(record);
	}

	@Override
	public List<InvoiceSelect> findSelectObject(Invoice record) {
		return invoiceDao.findSelectObject(record);
	}
	
	
	
	
	

}
