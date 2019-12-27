package com.saas.api.common.dao.crm;

import com.saas.api.admin.res.select.crm.SupplierSelect;
import com.saas.api.common.entity.crm.Supplier;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SupplierDao {
	
    int deleteByPrimaryKey(Integer supplierId);

    int insert(Supplier record);

    int insertSelective(Supplier record);

    Supplier selectByPrimaryKey(Integer supplierId);

    int updateByPrimaryKeySelective(Supplier record);

    int updateByPrimaryKey(Supplier record);

    List<Supplier> listData(@Param(value = "groupId") Integer groupId,
    		@Param(value = "companyId") Integer companyId, @Param(value = "departmentId") Integer departmentId,
    		@Param(value = "adminId") Integer adminId, @Param(value = "lanType") String lanType, 
    		@Param(value = "supplierName") String supplierName, @Param(value = "status") Integer status, 
    		@Param(value = "startIndex") Integer startIndex, @Param(value = "pageSize") Integer pageSize);

    int countData(@Param(value = "groupId") Integer groupId,
    		@Param(value = "companyId") Integer companyId, @Param(value = "departmentId") Integer departmentId,
    		@Param(value = "adminId") Integer adminId, @Param(value = "lanType") String lanType, 
    		@Param(value = "supplierName") String supplierName, @Param(value = "status") Integer status);
    
    Supplier selectBySupplierName(@Param(value = "groupId") Integer groupId,
    		@Param(value = "companyId") Integer companyId, @Param(value = "departmentId") Integer departmentId,
    		@Param(value = "adminId") Integer adminId, @Param(value = "lanType") String lanType, 
    		@Param(value = "supplierName") String supplierName);

    int countBySupplierName(@Param(value = "groupId") Integer groupId,
    		@Param(value = "companyId") Integer companyId, @Param(value = "departmentId") Integer departmentId,
    		@Param(value = "adminId") Integer adminId, @Param(value = "lanType") String lanType, 
    		@Param(value = "supplierName") String supplierName);
    
    
    List<Supplier> findByObject(Supplier record);
    
    List<SupplierSelect> findSelectObject(Supplier record);
    
    
}