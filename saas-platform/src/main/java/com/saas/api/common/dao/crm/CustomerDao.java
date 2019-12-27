package com.saas.api.common.dao.crm;

import com.saas.api.admin.res.select.crm.CustomerSelect;
import com.saas.api.common.entity.crm.Customer;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CustomerDao {
    int deleteByPrimaryKey(Long id);

    int insert(Customer record);

    int insertSelective(Customer record);

    Customer selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Customer record);

    int updateByPrimaryKey(Customer record);
    
    List<CustomerSelect> findSelectObject(@Param(value = "groupId") Integer groupId,
    		@Param(value = "companyId") Integer companyId, 
    		@Param(value = "departmentId") Integer departmentId,
    		@Param(value = "adminId") Integer adminId, 
    		@Param(value = "status") Integer status, 
    		@Param(value = "lanType") String lanType);

    Customer selectByPhone(@Param(value = "groupId") Integer groupId,
    		@Param(value = "companyId") Integer companyId, @Param(value = "departmentId") Integer departmentId,
    		@Param(value = "adminId") Integer adminId, @Param(value = "lanType") String lanType, 
    		@Param(value = "phone") String phone);

    List<Customer> customerList(@Param(value = "groupId") Integer groupId,
    		@Param(value = "companyId") Integer companyId, @Param(value = "departmentId") Integer departmentId,
    		@Param(value = "adminId") Integer adminId, @Param(value = "lanType") String lanType, 
    		@Param(value = "name") String name, @Param(value = "phone") String phone, 
    		@Param(value = "status") Integer status, 
    		@Param(value = "startIndex") Integer startIndex, @Param(value = "pageSize") Integer pageSize);

    int countCustomer(@Param(value = "groupId") Integer groupId,
    		@Param(value = "companyId") Integer companyId, @Param(value = "departmentId") Integer departmentId,
    		@Param(value = "adminId") Integer adminId, @Param(value = "lanType") String lanType, 
    		@Param(value = "name") String name, @Param(value = "phone") String phone, @Param(value = "status") Integer status);

    int countByPhone(@Param(value = "groupId") Integer groupId,
    		@Param(value = "companyId") Integer companyId, @Param(value = "departmentId") Integer departmentId,
    		@Param(value = "adminId") Integer adminId, @Param(value = "lanType") String lanType, 
    		@Param(value = "phone") String phone);
    
    
}