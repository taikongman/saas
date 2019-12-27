package com.saas.api.common.dao.crm;

import com.saas.api.admin.res.select.crm.InventoryRecordSelect;
import com.saas.api.common.entity.crm.InventoryRecord;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface InventoryRecordDao {
    int deleteByPrimaryKey(Long id);

    int insert(InventoryRecord record);

    int insertSelective(InventoryRecord record);

    InventoryRecord selectByPrimaryKey(Long id);
    
    List<InventoryRecord> selectByPurchaseId(Long purchaseId);
    
    List<InventoryRecord> selectByPurchaseCode(String purchaseCode);

    int updateByPrimaryKeySelective(InventoryRecord record);

    int updateByPrimaryKey(InventoryRecord record);
    
    int updateRemainQuantity(Integer remainQuantity, Long id);
    
    void updateWarehouseLocation(Long locationId, String locationName, Long id);

    List<InventoryRecord> listData(Map<?, ?> params);
    
    List<InventoryRecord> findByObject(InventoryRecord record);

    int countData(Map<?, ?> params);

    int countByCommodityId(Long commodityId);
    
    List<InventoryRecordSelect> findSelectObject(InventoryRecord record);
    
    List<InventoryRecord> defaultMaterialList(@Param(value = "groupId") Integer groupId,
    		@Param(value = "companyId") Integer companyId, @Param(value = "departmentId") Integer departmentId,
    		@Param(value = "adminId") Long adminId, @Param(value = "lanType") String lanType);

    List<InventoryRecord> commodityList4OrCond(@Param(value = "groupId") Integer groupId,
    		@Param(value = "companyId") Integer companyId, @Param(value = "departmentId") Integer departmentId,
    		@Param(value = "adminId") Long adminId, @Param(value = "lanType") String lanType, 
    		@Param(value = "searchCond") String searchCond, 
    		@Param(value = "startIndex") Integer startIndex, 
    		@Param(value = "pageSize") Integer pageSize);
    
    int countCommodity4OrCond(@Param(value = "groupId") Integer groupId,
    		@Param(value = "companyId") Integer companyId, @Param(value = "departmentId") Integer departmentId,
    		@Param(value = "adminId") Long adminId, @Param(value = "lanType") String lanType, 
    		@Param(value = "searchCond") String searchCond);
}