package com.saas.api.common.dao.crm;

import com.saas.api.common.entity.crm.SupplierBank;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SupplierBankDao {
	
    int deleteByPrimaryKey(Integer id);
    
    int deleteBySupplierId(Integer supplierId);

    int insert(SupplierBank record);

    int insertSelective(SupplierBank record);

    SupplierBank selectByPrimaryKey(Integer id);
    
    SupplierBank findByBankNo(Integer supplierId, String bankNo);

    int updateByPrimaryKeySelective(SupplierBank record);

    int updateByPrimaryKey(SupplierBank record);

    List<SupplierBank> listData(@Param(value = "supplierId") Integer supplierId,
    		@Param(value = "accountName") String accountName, @Param(value = "taxNo") String taxNo, 
    		@Param(value = "bankNo") String bankNo, @Param(value = "phone") String phone, 
    		@Param(value = "status") Integer status, 
    		@Param(value = "startIndex") Integer startIndex, @Param(value = "pageSize") Integer pageSize);

    int countData(@Param(value = "supplierId") Integer supplierId,
    		@Param(value = "accountName") String accountName, @Param(value = "taxNo") String taxNo, 
    		@Param(value = "bankNo") String bankNo, @Param(value = "phone") String phone, 
    		@Param(value = "status") Integer status);
    
    List<SupplierBank> findByObject(SupplierBank record);
    
    
}