package com.saas.api.admin.service.crm.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.saas.api.admin.service.crm.PurchaseRecordService;
import com.saas.api.common.constant.CommonConstant;
import com.saas.api.common.constant.DbConstant;
import com.saas.api.common.constant.RequestParamConstant;
import com.saas.api.common.constant.ResponseCodeI18n;
import com.saas.api.common.dao.common.InvoiceDao;
import com.saas.api.common.dao.common.UnitDao;
import com.saas.api.common.dao.crm.CommodityDao;
import com.saas.api.common.dao.crm.PurchaseRecordDao;
import com.saas.api.common.dao.crm.SupplierDao;
import com.saas.api.common.dao.sys.auth.UserDao;
import com.saas.api.common.entity.common.Invoice;
import com.saas.api.common.entity.common.Unit;
import com.saas.api.common.entity.crm.Commodity;
import com.saas.api.common.entity.crm.PurchaseRecord;
import com.saas.api.common.entity.crm.Supplier;
import com.saas.api.common.entity.sys.auth.User;
import com.saas.api.common.exception.SaasException;
import com.saas.api.common.util.ApiResultI18n;
import com.saas.api.common.util.StringDataUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class PurchaseRecordServiceImpl implements PurchaseRecordService {

	@Resource
    private PurchaseRecordDao purchaseRecordDao;
	
	@Resource
    private SupplierDao supplierDao;
	
	@Resource
    private CommodityDao commodityDao;
	
	@Resource
    private UserDao userDao;
	
	@Resource
    private UnitDao unitDao;
	
	@Resource
    private InvoiceDao invoiceDao;
	
	@Override
	public PurchaseRecord findByPrimayKey(Long id) {
		return purchaseRecordDao.selectByPrimaryKey(id);
	}

	@Override
	public void insertDataByJson(JSONObject params, Long adminId, String lanType) {
		User user = userDao.findById(adminId);
    	JSONArray commodityList = null;
        if(null != params.get(RequestParamConstant.COMMODITY_LIST)){
            commodityList = params.getJSONArray(RequestParamConstant.COMMODITY_LIST);
        }
        
        String purchaseCode = StringDataUtils.genPurchaseCode();
        
        if (null != commodityList) {
        	PurchaseRecord record = null;
            for (int i = 0; i < commodityList.size(); i++) {
            	record = (PurchaseRecord) JSONObject.toBean(commodityList.getJSONObject(i), PurchaseRecord.class);
            	record.setGroupId(user.getGroupId());
            	record.setCompanyId(user.getCompanyId());
            	record.setDepartmentId(user.getDepartmentId());
            	record.setAdminId(adminId);
            	record.setPurchaseCode(purchaseCode);
                Supplier supplier = supplierDao.selectByPrimaryKey(record.getSupplierId());
        		if(supplier != null) {
        			record.setSupplierCode(supplier.getSupplierCode());
        			record.setSupplierName(supplier.getSupplierName());
        		}else {
        			ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_SUPPLIER_IS_NOT_EXIST.getCode(), 
            				ResponseCodeI18n.MODULE_CRM_SUPPLIER_IS_NOT_EXIST.getMessage(), lanType);
                    throw new SaasException(exception18n.getCode(), exception18n.getMessage());
        		}
        		
        		Commodity commodity = commodityDao.selectByPrimaryKey(record.getCommodityId());
        		if(commodity != null) {
        			record.setCommodityCode(commodity.getCommodityCode());
        			record.setCommodityName(commodity.getCommodityName());
        			record.setSpecification(commodity.getSpecification());
        		}else {
        			ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_COMMODITY_IS_NOT_EXIST.getCode(), 
            				ResponseCodeI18n.MODULE_CRM_COMMODITY_IS_NOT_EXIST.getMessage(), lanType);
                    throw new SaasException(exception18n.getCode(), exception18n.getMessage());
        		}
        		
        		User operator = userDao.findById(record.getOperatorId());
        		if(operator != null) {
        			record.setOperatorName(operator.getRealName());
        		}else {
        			ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_OPERATOR_IS_NOT_EXIST.getCode(), 
            				ResponseCodeI18n.MODULE_CRM_OPERATOR_IS_NOT_EXIST.getMessage(), lanType);
                    throw new SaasException(exception18n.getCode(), exception18n.getMessage());
        		}
        		
        		Unit unit = unitDao.findById(record.getUnitId());
        		if(unit != null) {
        			record.setUnitName(unit.getUnitName());
        		}else {
        			ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_UNIT_IS_NOT_EXIST.getCode(), 
            				ResponseCodeI18n.MODULE_CRM_UNIT_IS_NOT_EXIST.getMessage(), lanType);
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
        		
        		if(record.getCostHasTax().equals(CommonConstant.INT_ONE)) {
                	if(record.getTaxRatio().intValue() > CommonConstant.INT_ZERO.intValue()) {
                		BigDecimal taxPrice = record.getCostPrice().divide(new BigDecimal(100 + record.getTaxRatio().intValue()),2);
                		record.setTaxPrice(taxPrice);
                		record.setTaxAmount(record.getCostPrice().subtract(taxPrice));
                	}else {
                		record.setTaxPrice(record.getCostPrice());
                		record.setTaxAmount(BigDecimal.ZERO);
                	}
                }
                if(record.getAdjustRatio().intValue() > CommonConstant.INT_ZERO.intValue()) {
                	record.setSalePrice(
                			record.getCostPrice().multiply(new BigDecimal(100 + record.getAdjustRatio().intValue()))
        							.divide(new BigDecimal(100), 2));
                }
                record.setAmount(record.getCostPrice().multiply(new BigDecimal(record.getQuantity())));
        		
        		record.setStatus(DbConstant.STATUS_ONE);
        		record.setCreateTime(new Date());
        		record.setVersion(DbConstant.INIT_VERSION);
        		record.setLanType(lanType);
        		purchaseRecordDao.insert(record);
            }
        }

	}
	
	@Override
	public void updateDataByJson(JSONObject params, Long adminId, String lanType) {
		JSONArray commodityList = null;
        if(null != params.get(RequestParamConstant.COMMODITY_LIST)){
            commodityList = params.getJSONArray(RequestParamConstant.COMMODITY_LIST);
        }
        if (null != commodityList) {
        	PurchaseRecord record = null;
            for (int i = 0; i < commodityList.size(); i++) {
            	record = (PurchaseRecord) JSONObject.toBean(commodityList.getJSONObject(i), PurchaseRecord.class);
                if(record.getId() == null) {
                	ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_PURCHASE_RECORD_IS_NOT_EXIST.getCode(), 
            				ResponseCodeI18n.MODULE_CRM_PURCHASE_RECORD_IS_NOT_EXIST.getMessage(), lanType);
                    throw new SaasException(exception18n.getCode(), exception18n.getMessage());
                }else {
                	PurchaseRecord check = purchaseRecordDao.selectByPrimaryKey(record.getId());
                	if(check == null) {
                		ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_PURCHASE_RECORD_IS_NOT_EXIST.getCode(), 
                				ResponseCodeI18n.MODULE_CRM_PURCHASE_RECORD_IS_NOT_EXIST.getMessage(), lanType);
                        throw new SaasException(exception18n.getCode(), exception18n.getMessage());
                	}
                }
                Supplier supplier = supplierDao.selectByPrimaryKey(record.getSupplierId());
        		if(supplier != null) {
        			record.setSupplierCode(supplier.getSupplierCode());
        			record.setSupplierName(supplier.getSupplierName());
        		}else {
        			ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_SUPPLIER_IS_NOT_EXIST.getCode(), 
            				ResponseCodeI18n.MODULE_CRM_SUPPLIER_IS_NOT_EXIST.getMessage(), lanType);
                    throw new SaasException(exception18n.getCode(), exception18n.getMessage());
        		}
        		
        		Commodity commodity = commodityDao.selectByPrimaryKey(record.getCommodityId());
        		if(commodity != null) {
        			record.setCommodityCode(commodity.getCommodityCode());
        			record.setCommodityName(commodity.getCommodityName());
        			record.setSpecification(commodity.getSpecification());
        		}else {
        			ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_COMMODITY_IS_NOT_EXIST.getCode(), 
            				ResponseCodeI18n.MODULE_CRM_COMMODITY_IS_NOT_EXIST.getMessage(), lanType);
                    throw new SaasException(exception18n.getCode(), exception18n.getMessage());
        		}
        		
        		User operator = userDao.findById(record.getOperatorId());
        		if(operator != null) {
        			record.setOperatorName(operator.getRealName());
        		}else {
        			ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_OPERATOR_IS_NOT_EXIST.getCode(), 
            				ResponseCodeI18n.MODULE_CRM_OPERATOR_IS_NOT_EXIST.getMessage(), lanType);
                    throw new SaasException(exception18n.getCode(), exception18n.getMessage());
        		}
        		
        		Unit unit = unitDao.findById(record.getUnitId());
        		if(unit != null) {
        			record.setUnitName(unit.getUnitName());
        		}else {
        			ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_UNIT_IS_NOT_EXIST.getCode(), 
            				ResponseCodeI18n.MODULE_CRM_UNIT_IS_NOT_EXIST.getMessage(), lanType);
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
        		
        		if(record.getCostHasTax().equals(CommonConstant.INT_ONE)) {
                	if(record.getTaxRatio().intValue() > CommonConstant.INT_ZERO.intValue()) {
                		BigDecimal taxPrice = record.getCostPrice().divide(new BigDecimal(100 + record.getTaxRatio().intValue()),2);
                		record.setTaxPrice(taxPrice);
                		record.setTaxAmount(record.getCostPrice().subtract(taxPrice));
                	}else {
                		record.setTaxPrice(record.getCostPrice());
                		record.setTaxAmount(BigDecimal.ZERO);
                	}
                }
                if(record.getAdjustRatio().intValue() > CommonConstant.INT_ZERO.intValue()) {
                	record.setSalePrice(
                			record.getCostPrice().multiply(new BigDecimal(100 + record.getAdjustRatio().intValue()))
        							.divide(new BigDecimal(100), 2));
                }
                record.setAmount(record.getCostPrice().multiply(new BigDecimal(record.getQuantity())));
                
        		record.setUpdateTime(new Date());
        		purchaseRecordDao.updateByPrimaryKeySelective(record);
            }
        }
	}

	@Override
	public int insertData(PurchaseRecord record) {
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
			record.setSpecification(commodity.getSpecification());
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
		
		if(record.getCostHasTax().equals(CommonConstant.INT_ONE)) {
        	if(record.getTaxRatio().intValue() > CommonConstant.INT_ZERO.intValue()) {
        		BigDecimal taxPrice = record.getCostPrice().divide(new BigDecimal(100 + record.getTaxRatio().intValue()),2);
        		record.setTaxPrice(taxPrice);
        		record.setTaxAmount(record.getCostPrice().subtract(taxPrice));
        	}else {
        		record.setTaxPrice(record.getCostPrice());
        		record.setTaxAmount(BigDecimal.ZERO);
        	}
        }
        if(record.getAdjustRatio().intValue() > CommonConstant.INT_ZERO.intValue()) {
        	record.setSalePrice(
        			record.getCostPrice().multiply(new BigDecimal(100 + record.getAdjustRatio().intValue()))
							.divide(new BigDecimal(100), 2));
        }
        record.setAmount(record.getCostPrice().multiply(new BigDecimal(record.getQuantity())));
        
		record.setStatus(DbConstant.STATUS_ONE);
		record.setCreateTime(new Date());
		record.setVersion(DbConstant.INIT_VERSION);
		return purchaseRecordDao.insert(record);
	}

	@Override
	public int updateData(PurchaseRecord record) {
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
			record.setSpecification(commodity.getSpecification());
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
		
		if(record.getCostHasTax().equals(CommonConstant.INT_ONE)) {
        	if(record.getTaxRatio().intValue() > CommonConstant.INT_ZERO.intValue()) {
        		BigDecimal taxPrice = record.getCostPrice().divide(new BigDecimal(100 + record.getTaxRatio().intValue()),2);
        		record.setTaxPrice(taxPrice);
        		record.setTaxAmount(record.getCostPrice().subtract(taxPrice));
        	}else {
        		record.setTaxPrice(record.getCostPrice());
        		record.setTaxAmount(BigDecimal.ZERO);
        	}
        }
        if(record.getAdjustRatio().intValue() > CommonConstant.INT_ZERO.intValue()) {
        	record.setSalePrice(
        			record.getCostPrice().multiply(new BigDecimal(100 + record.getAdjustRatio().intValue()))
							.divide(new BigDecimal(100), 2));
        }
        record.setAmount(record.getCostPrice().multiply(new BigDecimal(record.getQuantity())));
		
		record.setUpdateTime(new Date());
		return purchaseRecordDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int deleteByPrimayKey(Long id) {
		return purchaseRecordDao.deleteByPrimaryKey(id);
	}
	
	@Override
	public void deleteByStatus(Long id, String lanType) {
		PurchaseRecord check = purchaseRecordDao.selectByPrimaryKey(id);
    	if(check == null) {
    		ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_PURCHASE_RECORD_IS_NOT_EXIST.getCode(), 
    				ResponseCodeI18n.MODULE_CRM_PURCHASE_RECORD_IS_NOT_EXIST.getMessage(), lanType);
            throw new SaasException(exception18n.getCode(), exception18n.getMessage());
    	}
    	
    	PurchaseRecord record = new PurchaseRecord();
    	record.setId(id);
    	record.setStatus(DbConstant.STATUS_TWO);
    	record.setUpdateTime(new Date());
		purchaseRecordDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public Map<String, Object> getDataList(Map<String, Object> params, Long adminId) {
		User user = userDao.findById(adminId);
		params.put(RequestParamConstant.GROUP_ID, user.getGroupId());
		params.put(RequestParamConstant.COMPANY_ID, user.getCompanyId());
		
		List<PurchaseRecord> resultList = purchaseRecordDao.listData(params);
		Integer total = purchaseRecordDao.countData(params);
		
		Map<String, Object> result = new HashMap<>();
        result.put("dataList", resultList);
        result.put("total", total);
        return result;
	}

	@Override
	public List<PurchaseRecord> findByObject(PurchaseRecord record) {
		return purchaseRecordDao.findByObject(record);
	}

	@Override
	public List<PurchaseRecord> selectByPurchaseCode(String purchaseCode) {
		return purchaseRecordDao.selectByPurchaseCode(purchaseCode);
	}

	@Override
	public int countByCommodityId(Long commodityId) {
		return purchaseRecordDao.countByCommodityId(commodityId);
	}

}
