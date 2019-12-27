package com.saas.api.admin.service.crm.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.saas.api.admin.res.select.crm.InventoryRecordSelect;
import com.saas.api.admin.service.crm.InventoryRecordService;
import com.saas.api.common.constant.CommonConstant;
import com.saas.api.common.constant.DbConstant;
import com.saas.api.common.constant.RequestParamConstant;
import com.saas.api.common.constant.ResponseCodeI18n;
import com.saas.api.common.dao.common.UnitDao;
import com.saas.api.common.dao.crm.CommodityDao;
import com.saas.api.common.dao.crm.InventoryRecordDao;
import com.saas.api.common.dao.crm.PurchaseRecordDao;
import com.saas.api.common.dao.crm.SupplierDao;
import com.saas.api.common.dao.crm.WarehouseDao;
import com.saas.api.common.dao.crm.WarehouseLocationDao;
import com.saas.api.common.dao.sys.auth.UserDao;
import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.common.Unit;
import com.saas.api.common.entity.crm.Commodity;
import com.saas.api.common.entity.crm.InventoryRecord;
import com.saas.api.common.entity.crm.PurchaseRecord;
import com.saas.api.common.entity.crm.Supplier;
import com.saas.api.common.entity.crm.Warehouse;
import com.saas.api.common.entity.crm.WarehouseLocation;
import com.saas.api.common.entity.sys.auth.User;
import com.saas.api.common.exception.SaasException;
import com.saas.api.common.util.ApiResultI18n;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class InventoryRecordServiceImpl implements InventoryRecordService {

	@Resource
    private InventoryRecordDao inventoryRecordDao;
	
	@Resource
    private PurchaseRecordDao purchaseRecordDao;
	
	@Resource
    private SupplierDao supplierDao;
	
	@Resource
    private CommodityDao commodityDao;
	
	@Resource
    private WarehouseDao warehouseDao;
	
	@Resource
    private WarehouseLocationDao locationDao;
	
	@Resource
    private UserDao userDao;
	
	@Resource
    private UnitDao unitDao;
	

	@Override
	public InventoryRecord findByPrimayKey(Long id) {
		return inventoryRecordDao.selectByPrimaryKey(id);
	}

	@Override
	public void insertDataByJson(JSONObject params, Long adminId, String lanType) {
		User user = userDao.findById(adminId);
    	JSONArray commodityList = null;
        if(null != params.get(RequestParamConstant.COMMODITY_LIST)){
            commodityList = params.getJSONArray(RequestParamConstant.COMMODITY_LIST);
        }
        if (null != commodityList) {
        	InventoryRecord inventoryRecord = null;
            for (int i = 0; i < commodityList.size(); i++) {
            	inventoryRecord = (InventoryRecord) JSONObject.toBean(commodityList.getJSONObject(i), InventoryRecord.class);
            	inventoryRecord.setGroupId(user.getGroupId());
                inventoryRecord.setCompanyId(user.getCompanyId());
                inventoryRecord.setDepartmentId(user.getDepartmentId());
                inventoryRecord.setAdminId(adminId);
                
                Supplier supplier = supplierDao.selectByPrimaryKey(inventoryRecord.getSupplierId());
        		if(supplier != null) {
        			inventoryRecord.setSupplierCode(supplier.getSupplierCode());
        			inventoryRecord.setSupplierName(supplier.getSupplierName());
        		}else {
        			ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_SUPPLIER_IS_NOT_EXIST.getCode(), 
            				ResponseCodeI18n.MODULE_CRM_SUPPLIER_IS_NOT_EXIST.getMessage(), lanType);
                    throw new SaasException(exception18n.getCode(), exception18n.getMessage());
        		}
        		
        		Commodity commodity = commodityDao.selectByPrimaryKey(inventoryRecord.getCommodityId());
        		if(commodity != null) {
        			inventoryRecord.setCommodityCode(commodity.getCommodityCode());
        			inventoryRecord.setCommodityName(commodity.getCommodityName());
        			inventoryRecord.setSpecification(commodity.getSpecification());
        		}else {
        			ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_COMMODITY_IS_NOT_EXIST.getCode(), 
            				ResponseCodeI18n.MODULE_CRM_COMMODITY_IS_NOT_EXIST.getMessage(), lanType);
                    throw new SaasException(exception18n.getCode(), exception18n.getMessage());
        		}
        		
        		User operator = userDao.findById(inventoryRecord.getOperatorId());
        		if(operator != null) {
        			inventoryRecord.setOperatorName(operator.getRealName());
        		}else {
        			ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_OPERATOR_IS_NOT_EXIST.getCode(), 
            				ResponseCodeI18n.MODULE_CRM_OPERATOR_IS_NOT_EXIST.getMessage(), lanType);
                    throw new SaasException(exception18n.getCode(), exception18n.getMessage());
        		}
        		
        		Unit unit = unitDao.findById(inventoryRecord.getUnitId());
        		if(unit != null) {
        			inventoryRecord.setUnitName(unit.getUnitName());
        		}else {
        			ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_UNIT_IS_NOT_EXIST.getCode(), 
            				ResponseCodeI18n.MODULE_CRM_UNIT_IS_NOT_EXIST.getMessage(), lanType);
                    throw new SaasException(exception18n.getCode(), exception18n.getMessage());
        		}
        		
        		if(inventoryRecord.getWarehouseId() != null) {
        			Warehouse warehouse = warehouseDao.selectByPrimaryKey(inventoryRecord.getWarehouseId());
            		if(warehouse != null) {
            			inventoryRecord.setWarehouseName(warehouse.getWarehouseName());
            		}else {
            			ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_WAREHOUSE_IS_NOT_EXIST.getCode(), 
                				ResponseCodeI18n.MODULE_CRM_WAREHOUSE_IS_NOT_EXIST.getMessage(), lanType);
                        throw new SaasException(exception18n.getCode(), exception18n.getMessage());
            		}
        		}
        		if(inventoryRecord.getLocationId() != null) {
        			WarehouseLocation location = locationDao.selectByPrimaryKey(inventoryRecord.getLocationId());
            		if(location != null) {
            			inventoryRecord.setLocationName(location.getLocationName());
            		}else {
            			ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_WAREHOUSE_LOCATION_IS_NOT_EXIST.getCode(), 
                				ResponseCodeI18n.MODULE_CRM_WAREHOUSE_LOCATION_IS_NOT_EXIST.getMessage(), lanType);
                        throw new SaasException(exception18n.getCode(), exception18n.getMessage());
            		}
        		}
                
        		if(inventoryRecord.getRemainQuantity() == null) {
        			inventoryRecord.setRemainQuantity(inventoryRecord.getQuantity());
        		}
        		
        		if(inventoryRecord.getCostHasTax().equals(CommonConstant.INT_ONE)) {
                	if(inventoryRecord.getTaxRatio().intValue() > CommonConstant.INT_ZERO.intValue()) {
                		BigDecimal taxPrice = inventoryRecord.getCostPrice().divide(new BigDecimal(100 + inventoryRecord.getTaxRatio().intValue()),2);
                		inventoryRecord.setTaxPrice(taxPrice);
                		inventoryRecord.setTaxAmount(inventoryRecord.getCostPrice().subtract(taxPrice));
                	}else {
                		inventoryRecord.setTaxPrice(inventoryRecord.getCostPrice());
                		inventoryRecord.setTaxAmount(BigDecimal.ZERO);
                	}
                }
                if(inventoryRecord.getAdjustRatio().intValue() > CommonConstant.INT_ZERO.intValue()) {
                	inventoryRecord.setSalePrice(
                			inventoryRecord.getCostPrice().multiply(new BigDecimal(100 + inventoryRecord.getAdjustRatio().intValue()))
        							.divide(new BigDecimal(100), 2));
                }
                inventoryRecord.setAmount(inventoryRecord.getCostPrice().multiply(new BigDecimal(inventoryRecord.getQuantity())));
                
        		inventoryRecord.setStatus(DbConstant.STATUS_ONE);
                inventoryRecord.setCreateTime(new Date());
                inventoryRecord.setVersion(DbConstant.INIT_VERSION);
                inventoryRecord.setLanType(lanType);
                inventoryRecordDao.insert(inventoryRecord);
                
                PurchaseRecord updatePurchase = new PurchaseRecord();
                updatePurchase.setId(inventoryRecord.getPurchaseId());
                updatePurchase.setIsToInventory(DbConstant.STATUS_ONE);
                purchaseRecordDao.updateIsToInventory(updatePurchase);
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
        	InventoryRecord inventoryRecord = null;
            for (int i = 0; i < commodityList.size(); i++) {
            	inventoryRecord = (InventoryRecord) JSONObject.toBean(commodityList.getJSONObject(i), InventoryRecord.class);
            	if(inventoryRecord.getId() == null) {
                	ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_INVENTORY_RECORD_IS_NOT_EXIST.getCode(), 
            				ResponseCodeI18n.MODULE_CRM_INVENTORY_RECORD_IS_NOT_EXIST.getMessage(), lanType);
                    throw new SaasException(exception18n.getCode(), exception18n.getMessage());
                }else {
                	InventoryRecord check = inventoryRecordDao.selectByPrimaryKey(inventoryRecord.getId());
                	if(check == null) {
                		ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_INVENTORY_RECORD_IS_NOT_EXIST.getCode(), 
                				ResponseCodeI18n.MODULE_CRM_INVENTORY_RECORD_IS_NOT_EXIST.getMessage(), lanType);
                        throw new SaasException(exception18n.getCode(), exception18n.getMessage());
                	}
                }
            	
                Supplier supplier = supplierDao.selectByPrimaryKey(inventoryRecord.getSupplierId());
        		if(supplier != null) {
        			inventoryRecord.setSupplierCode(supplier.getSupplierCode());
        			inventoryRecord.setSupplierName(supplier.getSupplierName());
        		}else {
        			ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_SUPPLIER_IS_NOT_EXIST.getCode(), 
            				ResponseCodeI18n.MODULE_CRM_SUPPLIER_IS_NOT_EXIST.getMessage(), lanType);
                    throw new SaasException(exception18n.getCode(), exception18n.getMessage());
        		}
        		
        		Commodity commodity = commodityDao.selectByPrimaryKey(inventoryRecord.getCommodityId());
        		if(commodity != null) {
        			inventoryRecord.setCommodityCode(commodity.getCommodityCode());
        			inventoryRecord.setCommodityName(commodity.getCommodityName());
        			inventoryRecord.setSpecification(commodity.getSpecification());
        		}else {
        			ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_COMMODITY_IS_NOT_EXIST.getCode(), 
            				ResponseCodeI18n.MODULE_CRM_COMMODITY_IS_NOT_EXIST.getMessage(), lanType);
                    throw new SaasException(exception18n.getCode(), exception18n.getMessage());
        		}
        		
        		User operator = userDao.findById(inventoryRecord.getOperatorId());
        		if(operator != null) {
        			inventoryRecord.setOperatorName(operator.getRealName());
        		}else {
        			ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_OPERATOR_IS_NOT_EXIST.getCode(), 
            				ResponseCodeI18n.MODULE_CRM_OPERATOR_IS_NOT_EXIST.getMessage(), lanType);
                    throw new SaasException(exception18n.getCode(), exception18n.getMessage());
        		}
        		
        		Unit unit = unitDao.findById(inventoryRecord.getUnitId());
        		if(unit != null) {
        			inventoryRecord.setUnitName(unit.getUnitName());
        		}else {
        			ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_UNIT_IS_NOT_EXIST.getCode(), 
            				ResponseCodeI18n.MODULE_CRM_UNIT_IS_NOT_EXIST.getMessage(), lanType);
                    throw new SaasException(exception18n.getCode(), exception18n.getMessage());
        		}
        		
        		if(inventoryRecord.getWarehouseId() != null) {
        			Warehouse warehouse = warehouseDao.selectByPrimaryKey(inventoryRecord.getWarehouseId());
            		if(warehouse != null) {
            			inventoryRecord.setWarehouseName(warehouse.getWarehouseName());
            		}else {
            			ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_WAREHOUSE_IS_NOT_EXIST.getCode(), 
                				ResponseCodeI18n.MODULE_CRM_WAREHOUSE_IS_NOT_EXIST.getMessage(), lanType);
                        throw new SaasException(exception18n.getCode(), exception18n.getMessage());
            		}
        		}
        		if(inventoryRecord.getLocationId() != null) {
        			WarehouseLocation location = locationDao.selectByPrimaryKey(inventoryRecord.getLocationId());
            		if(location != null) {
            			inventoryRecord.setLocationName(location.getLocationName());
            		}else {
            			ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_WAREHOUSE_LOCATION_IS_NOT_EXIST.getCode(), 
                				ResponseCodeI18n.MODULE_CRM_WAREHOUSE_LOCATION_IS_NOT_EXIST.getMessage(), lanType);
                        throw new SaasException(exception18n.getCode(), exception18n.getMessage());
            		}
        		}
        		
        		if(inventoryRecord.getRemainQuantity() == null) {
        			inventoryRecord.setRemainQuantity(inventoryRecord.getQuantity());
        		}
        		
        		if(inventoryRecord.getCostHasTax().equals(CommonConstant.INT_ONE)) {
                	if(inventoryRecord.getTaxRatio().intValue() > CommonConstant.INT_ZERO.intValue()) {
                		BigDecimal taxPrice = inventoryRecord.getCostPrice().divide(new BigDecimal(100 + inventoryRecord.getTaxRatio().intValue()),2);
                		inventoryRecord.setTaxPrice(taxPrice);
                		inventoryRecord.setTaxAmount(inventoryRecord.getCostPrice().subtract(taxPrice));
                	}else {
                		inventoryRecord.setTaxPrice(inventoryRecord.getCostPrice());
                		inventoryRecord.setTaxAmount(BigDecimal.ZERO);
                	}
                }
                if(inventoryRecord.getAdjustRatio().intValue() > CommonConstant.INT_ZERO.intValue()) {
                	inventoryRecord.setSalePrice(
                			inventoryRecord.getCostPrice().multiply(new BigDecimal(100 + inventoryRecord.getAdjustRatio().intValue()))
        							.divide(new BigDecimal(100), 2));
                }
                inventoryRecord.setAmount(inventoryRecord.getCostPrice().multiply(new BigDecimal(inventoryRecord.getQuantity())));
                
        		inventoryRecord.setUpdateTime(new Date());
        		inventoryRecordDao.updateByPrimaryKeySelective(inventoryRecord);
            }
        }
	}

	@Override
	public void deleteByStatus(Long id, String lanType) {
		InventoryRecord check = inventoryRecordDao.selectByPrimaryKey(id);
    	if(check == null) {
    		ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_INVENTORY_RECORD_IS_NOT_EXIST.getCode(), 
    				ResponseCodeI18n.MODULE_CRM_INVENTORY_RECORD_IS_NOT_EXIST.getMessage(), lanType);
            throw new SaasException(exception18n.getCode(), exception18n.getMessage());
    	}
		
    	InventoryRecord record = new InventoryRecord();
    	record.setId(id);
    	record.setStatus(DbConstant.STATUS_TWO);
    	record.setUpdateTime(new Date());
    	inventoryRecordDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int insertData(InventoryRecord record) {
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
		
		if(record.getWarehouseId() != null) {
			Warehouse warehouse = warehouseDao.selectByPrimaryKey(record.getWarehouseId());
			if(warehouse != null) {
				record.setWarehouseName(warehouse.getWarehouseName());
			}else {
				ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_WAREHOUSE_IS_NOT_EXIST.getCode(), 
	    				ResponseCodeI18n.MODULE_CRM_WAREHOUSE_IS_NOT_EXIST.getMessage(), record.getLanType());
	            throw new SaasException(exception18n.getCode(), exception18n.getMessage());
			}
		}
		if(record.getLocationId() != null) {
			WarehouseLocation location = locationDao.selectByPrimaryKey(record.getLocationId());
			if(location != null) {
				record.setLocationName(location.getLocationName());
			}else {
				ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_WAREHOUSE_LOCATION_IS_NOT_EXIST.getCode(), 
	    				ResponseCodeI18n.MODULE_CRM_WAREHOUSE_LOCATION_IS_NOT_EXIST.getMessage(), record.getLanType());
	            throw new SaasException(exception18n.getCode(), exception18n.getMessage());
			}
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
		return inventoryRecordDao.insert(record);
	}

	@Override
	public int updateData(InventoryRecord record) {
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
		
		if(record.getWarehouseId() != null) {
			Warehouse warehouse = warehouseDao.selectByPrimaryKey(record.getWarehouseId());
			if(warehouse != null) {
				record.setWarehouseName(warehouse.getWarehouseName());
			}else {
				ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_WAREHOUSE_IS_NOT_EXIST.getCode(), 
	    				ResponseCodeI18n.MODULE_CRM_WAREHOUSE_IS_NOT_EXIST.getMessage(), record.getLanType());
	            throw new SaasException(exception18n.getCode(), exception18n.getMessage());
			}
		}
		if(record.getLocationId() != null) {
			WarehouseLocation location = locationDao.selectByPrimaryKey(record.getLocationId());
			if(location != null) {
				record.setLocationName(location.getLocationName());
			}else {
				ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_WAREHOUSE_LOCATION_IS_NOT_EXIST.getCode(), 
	    				ResponseCodeI18n.MODULE_CRM_WAREHOUSE_LOCATION_IS_NOT_EXIST.getMessage(), record.getLanType());
	            throw new SaasException(exception18n.getCode(), exception18n.getMessage());
			}
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
		return inventoryRecordDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateQuantity(Integer remainQuantity, Long id) {
		
		return inventoryRecordDao.updateRemainQuantity(remainQuantity, id);
	}

	@Override
	public void updateLocation(Long id, Long locationId, String lanType) {
		WarehouseLocation location = locationDao.selectByPrimaryKey(locationId);
		if(location == null) {
			ApiResultI18n exception18n = ApiResultI18n.failure(ResponseCodeI18n.MODULE_CRM_WAREHOUSE_LOCATION_IS_NOT_EXIST.getCode(), 
    				ResponseCodeI18n.MODULE_CRM_WAREHOUSE_LOCATION_IS_NOT_EXIST.getMessage(), lanType);
            throw new SaasException(exception18n.getCode(), exception18n.getMessage());
		}
		
		inventoryRecordDao.updateWarehouseLocation(locationId, location.getLocationName(), id);
	}

	@Override
	public int deleteByPrimayKey(Long id) {
		return inventoryRecordDao.deleteByPrimaryKey(id);
	}

	@Override
	public Map<String, Object> getDataList(Map<String, Object> params, Long adminId) {
		User user = userDao.findById(adminId);
		params.put(RequestParamConstant.GROUP_ID, user.getGroupId());
		params.put(RequestParamConstant.COMPANY_ID, user.getCompanyId());
		
		List<InventoryRecord> resultList = inventoryRecordDao.listData(params);
		Integer total = inventoryRecordDao.countData(params);
		
		Map<String, Object> result = new HashMap<>();
        result.put("dataList", resultList);
        result.put("total", total);
        return result;
	}
	
	@Override
	public List<InventoryRecordSelect> findSelectObject(Long adminId, String lanType) {
		User user = userDao.findById(adminId);
		InventoryRecord record = new InventoryRecord();
		record.setGroupId(user.getGroupId());
		record.setCompanyId(user.getCompanyId());
		record.setStatus(DbConstant.STATUS_ONE);
		record.setLanType(lanType);
		return inventoryRecordDao.findSelectObject(record);
	}

	@Override
	public List<InventoryRecord> findByObject(InventoryRecord record){
		return inventoryRecordDao.findByObject(record);
	}
	
	@Override
	public List<InventoryRecord> selectByPurchaseId(Long purchaseId) {
		return inventoryRecordDao.selectByPurchaseId(purchaseId);
	}

	@Override
	public List<InventoryRecord> selectByPurchaseCode(String purchaseCode) {
		return inventoryRecordDao.selectByPurchaseCode(purchaseCode);
	}

	@Override
	public int countByCommodityId(Long commodityId) {
		int inventoryRecordCount = inventoryRecordDao.countByCommodityId(commodityId);
		return inventoryRecordCount;
	}
	
	@Override
    public List<InventoryRecord> defaultMaterialList(Long adminId, String lanType) {
    	User user = userDao.findById(adminId);
    	List<InventoryRecord> commodityList = inventoryRecordDao.defaultMaterialList(user.getGroupId(), user.getCompanyId(), 
    			null, null, 
    			lanType);
        return commodityList;
    }
    
    
	@Override
    public Map<String, Object> commodityList4OrCond(Long adminId, String lanType, String searchCond, Page page) {
    	User user = userDao.findById(adminId);
    	List<InventoryRecord> commodityList = inventoryRecordDao.commodityList4OrCond(user.getGroupId(), user.getCompanyId(), 
    			null, null,
    			lanType, searchCond, page.getStartIndex(), page.getPageSize());
    	
    	int total = inventoryRecordDao.countCommodity4OrCond(user.getGroupId(), user.getCompanyId(), 
    			null, null,
    			lanType, searchCond);
        
        Map<String, Object> result = new HashMap<>();
        result.put("dataList", commodityList);
        result.put("total", total);
        return result;
    }

}
