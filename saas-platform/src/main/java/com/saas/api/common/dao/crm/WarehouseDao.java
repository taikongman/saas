package com.saas.api.common.dao.crm;

import com.saas.api.admin.res.select.crm.WarehouseSelect;
import com.saas.api.common.entity.crm.Warehouse;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WarehouseDao {
	
    int deleteByPrimaryKey(Integer warehouseId);

    int insert(Warehouse record);

    int insertSelective(Warehouse record);

    Warehouse selectByPrimaryKey(Integer warehouseId);

    int updateByPrimaryKeySelective(Warehouse record);

    int updateByPrimaryKey(Warehouse record);

    List<Warehouse> listData(@Param(value = "groupId") Integer groupId,
    		@Param(value = "companyId") Integer companyId, @Param(value = "departmentId") Integer departmentId,
    		@Param(value = "adminId") Integer adminId, @Param(value = "lanType") String lanType, 
    		@Param(value = "warehouseCode") String warehouseCode, 
    		@Param(value = "warehouseName") String warehouseName, 
    		@Param(value = "status") Integer status, 
    		@Param(value = "startIndex") Integer startIndex, @Param(value = "pageSize") Integer pageSize);

    int countData(@Param(value = "groupId") Integer groupId,
    		@Param(value = "companyId") Integer companyId, @Param(value = "departmentId") Integer departmentId,
    		@Param(value = "adminId") Integer adminId, @Param(value = "lanType") String lanType, 
    		@Param(value = "warehouseCode") String warehouseCode, 
    		@Param(value = "warehouseName") String warehouseName, 
    		@Param(value = "status") Integer status);
    
    Warehouse selectByWarehouseName(@Param(value = "groupId") Integer groupId,
    		@Param(value = "companyId") Integer companyId, @Param(value = "departmentId") Integer departmentId,
    		@Param(value = "adminId") Integer adminId, @Param(value = "lanType") String lanType, 
    		@Param(value = "warehouseName") String warehouseName);

    int countByWarehouseName(@Param(value = "groupId") Integer groupId,
    		@Param(value = "companyId") Integer companyId, @Param(value = "departmentId") Integer departmentId,
    		@Param(value = "adminId") Integer adminId, @Param(value = "lanType") String lanType, 
    		@Param(value = "warehouseName") String warehouseName);
    
    
    List<Warehouse> findByObject(Warehouse record);
    
    List<WarehouseSelect> findSelectObject(Warehouse record);
    
    
}