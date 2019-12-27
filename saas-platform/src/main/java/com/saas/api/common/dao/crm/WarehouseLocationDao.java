package com.saas.api.common.dao.crm;

import com.saas.api.common.entity.crm.WarehouseLocation;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WarehouseLocationDao {
	
    int deleteByPrimaryKey(Long id);
    
    int deleteByWarehouseId(Integer warehouseId);

    int insert(WarehouseLocation record);

    int insertSelective(WarehouseLocation record);

    WarehouseLocation selectByPrimaryKey(Long id);
    
    WarehouseLocation findByName(Integer warehouseId, String locationName);
    
    List<WarehouseLocation> findByWarehouseId(Integer warehouseId);

    int updateByPrimaryKeySelective(WarehouseLocation record);

    int updateByPrimaryKey(WarehouseLocation record);

    List<WarehouseLocation> listData(@Param(value = "warehouseId") Integer warehouseId,
    		@Param(value = "locationCode") String locationCode, @Param(value = "locationName") String locationName, 
    		@Param(value = "status") Integer status, 
    		@Param(value = "startIndex") Integer startIndex, @Param(value = "pageSize") Integer pageSize);

    int countData(@Param(value = "warehouseId") Integer warehouseId,
    		@Param(value = "locationCode") String locationCode, @Param(value = "locationName") String locationName, 
    		@Param(value = "status") Integer status);
    
    List<WarehouseLocation> findByObject(WarehouseLocation record);
    
    
}