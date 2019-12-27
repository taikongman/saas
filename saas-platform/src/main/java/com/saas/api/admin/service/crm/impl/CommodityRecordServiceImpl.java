package com.saas.api.admin.service.crm.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.saas.api.admin.service.crm.CommodityRecordService;
import com.saas.api.common.constant.DbConstant;
import com.saas.api.common.constant.ResponseCodeI18n;
import com.saas.api.common.dao.common.InvoiceDao;
import com.saas.api.common.dao.common.UnitDao;
import com.saas.api.common.dao.crm.CommodityDao;
import com.saas.api.common.dao.crm.OrderCommodityDao;
import com.saas.api.common.dao.crm.SupplierDao;
import com.saas.api.common.dao.sys.auth.UserDao;
import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.common.Invoice;
import com.saas.api.common.entity.common.Unit;
import com.saas.api.common.entity.crm.Commodity;
import com.saas.api.common.entity.crm.OrderCommodity;
import com.saas.api.common.entity.crm.Supplier;
import com.saas.api.common.entity.sys.auth.User;
import com.saas.api.common.exception.SaasException;
import com.saas.api.common.util.ApiResultI18n;

import net.sf.json.JSONObject;

@Service
public class CommodityRecordServiceImpl implements CommodityRecordService {

	@Resource
    private SupplierDao supplierDao;
	
	@Resource
    private CommodityDao commodityDao;
	
	@Resource
    private OrderCommodityDao commodityRecordDao;
	
	@Resource
    private UserDao userDao;
	
	@Resource
    private UnitDao unitDao;
	
	@Resource
    private InvoiceDao invoiceDao;
	
	
	
	@Override
	public OrderCommodity findByPrimayKey(Long id) {
		return commodityRecordDao.selectByPrimaryKey(id);
	}

	@Override
	public int insertData(OrderCommodity record) {
		User user = userDao.findById(record.getAdminId());
		record.setGroupId(user.getGroupId());
		record.setCompanyId(user.getCompanyId());
		record.setDepartmentId(user.getDepartmentId());
		
		Supplier supplier = supplierDao.selectByPrimaryKey(record.getSupplierId());
		if(supplier != null) {
			record.setSupplierCode(supplier.getSupplierCode());
			record.setSupplierName(supplier.getSupplierName());
		}else {
			ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_SUPPLIER_IS_NOT_EXIST.getCode(), 
    				ResponseCodeI18n.MODULE_CRM_SUPPLIER_IS_NOT_EXIST.getMessage(), record.getLanType());
            throw new SaasException(exception18n.getCode(), exception18n.getMessage());
		}
		
		Commodity commodity = commodityDao.selectByPrimaryKey(record.getCommodityId());
		if(commodity != null) {
			record.setCommodityCode(commodity.getCommodityCode());
			record.setCommodityName(commodity.getCommodityName());
		}else {
			ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_COMMODITY_IS_NOT_EXIST.getCode(), 
    				ResponseCodeI18n.MODULE_CRM_COMMODITY_IS_NOT_EXIST.getMessage(), record.getLanType());
            throw new SaasException(exception18n.getCode(), exception18n.getMessage());
		}
		
		User operator = userDao.findById(record.getOperatorId());
		if(operator != null) {
			record.setOperatorName(operator.getRealName());
		}else {
			ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_OPERATOR_IS_NOT_EXIST.getCode(), 
    				ResponseCodeI18n.MODULE_CRM_OPERATOR_IS_NOT_EXIST.getMessage(), record.getLanType());
            throw new SaasException(exception18n.getCode(), exception18n.getMessage());
		}
		
		Unit unit = unitDao.findById(record.getUnitId());
		if(unit != null) {
			record.setUnitName(unit.getUnitName());
		}else {
			ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_UNIT_IS_NOT_EXIST.getCode(), 
    				ResponseCodeI18n.MODULE_CRM_UNIT_IS_NOT_EXIST.getMessage(), record.getLanType());
            throw new SaasException(exception18n.getCode(), exception18n.getMessage());
		}
		Invoice invoice = invoiceDao.findById(record.getInvoiceId());
		if(invoice != null) {
			record.setInvoiceName(invoice.getInvoiceName());
		}else {
			ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_INVOICE_IS_NOT_EXIST.getCode(), 
    				ResponseCodeI18n.MODULE_CRM_INVOICE_IS_NOT_EXIST.getMessage(), record.getLanType());
            throw new SaasException(exception18n.getCode(), exception18n.getMessage());
		}
		
		record.setStatus(DbConstant.STATUS_ONE);
		record.setCreateTime(new Date());
		return commodityRecordDao.insert(record);
	}

	@Override
	public int updateData(OrderCommodity record) {
		Supplier supplier = supplierDao.selectByPrimaryKey(record.getSupplierId());
		if(supplier != null) {
			record.setSupplierCode(supplier.getSupplierCode());
			record.setSupplierName(supplier.getSupplierName());
		}else {
			ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_SUPPLIER_IS_NOT_EXIST.getCode(), 
    				ResponseCodeI18n.MODULE_CRM_SUPPLIER_IS_NOT_EXIST.getMessage(), record.getLanType());
            throw new SaasException(exception18n.getCode(), exception18n.getMessage());
		}
		
		Commodity commodity = commodityDao.selectByPrimaryKey(record.getCommodityId());
		if(commodity != null) {
			record.setCommodityCode(commodity.getCommodityCode());
			record.setCommodityName(commodity.getCommodityName());
		}else {
			ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_COMMODITY_IS_NOT_EXIST.getCode(), 
    				ResponseCodeI18n.MODULE_CRM_COMMODITY_IS_NOT_EXIST.getMessage(), record.getLanType());
            throw new SaasException(exception18n.getCode(), exception18n.getMessage());
		}
		
		User operator = userDao.findById(record.getOperatorId());
		if(operator != null) {
			record.setOperatorName(operator.getRealName());
		}else {
			ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_OPERATOR_IS_NOT_EXIST.getCode(), 
    				ResponseCodeI18n.MODULE_CRM_OPERATOR_IS_NOT_EXIST.getMessage(), record.getLanType());
            throw new SaasException(exception18n.getCode(), exception18n.getMessage());
		}
		
		Unit unit = unitDao.findById(record.getUnitId());
		if(unit != null) {
			record.setUnitName(unit.getUnitName());
		}else {
			ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_UNIT_IS_NOT_EXIST.getCode(), 
    				ResponseCodeI18n.MODULE_CRM_UNIT_IS_NOT_EXIST.getMessage(), record.getLanType());
            throw new SaasException(exception18n.getCode(), exception18n.getMessage());
		}
		Invoice invoice = invoiceDao.findById(record.getInvoiceId());
		if(invoice != null) {
			record.setInvoiceName(invoice.getInvoiceName());
		}else {
			ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_INVOICE_IS_NOT_EXIST.getCode(), 
    				ResponseCodeI18n.MODULE_CRM_INVOICE_IS_NOT_EXIST.getMessage(), record.getLanType());
            throw new SaasException(exception18n.getCode(), exception18n.getMessage());
		}
		
		record.setUpdateTime(new Date());
		return commodityRecordDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int deleteByPrimayKey(Long id) {
		return commodityRecordDao.deleteByPrimaryKey(id);
	}

	@Override
	public Map<String, Object> getListData(Integer commodityId, 
			Integer supplierId, Long operatorId, String carNo, 
			Integer operateType, Long adminId, String lanType,
			Page page) {
		User user = userDao.findById(adminId);
		Map<String, Object> params = new HashMap<>();
		params.put("groupId", user.getGroupId());
		params.put("companyId", user.getCompanyId());
		if(commodityId != null) {
			params.put("commodityId", commodityId);
		}
		if(supplierId != null) {
			params.put("supplierId", supplierId);
		}
		if(operatorId != null) {
			params.put("operatorId", operatorId);
		}
		if(!StringUtils.isEmpty(carNo)) {
			params.put("carNo", carNo);
		}
		if(operateType != null) {
			params.put("operateType", operatorId);
		}
		if(lanType != null) {
			params.put("lanType", operatorId);
		}
		
		params.put("startIndex", page.getStartIndex());
		params.put("pageSize", page.getPageSize());
		
		List<OrderCommodity> resultList = commodityRecordDao.listData(params);
		
		params.remove("startIndex");
		params.remove("pageSize");
		Integer total = commodityRecordDao.countData(params);
		
		Map<String, Object> result = new HashMap<>();
        result.put("dataList", resultList);
        result.put("total", total);
        return result;
	}

	@Override
	public List<OrderCommodity> findByObject(OrderCommodity record) {
		//
		return commodityRecordDao.findByObject(record);
	}

	@Override
	public void addDataByJson(JSONObject params, Long adminId, String lanType) {
		
	}

	@Override
	public Map<String, Object> getListDataByMap(Map<?, ?> params) {
		List<OrderCommodity> resultList = commodityRecordDao.listData(params);
		
		params.remove("startIndex");
		params.remove("pageSize");
		
		Integer total = commodityRecordDao.countData(params);
		
		Map<String, Object> result = new HashMap<>();
        result.put("dataList", resultList);
        result.put("total", total);
        return result;
	}

}
