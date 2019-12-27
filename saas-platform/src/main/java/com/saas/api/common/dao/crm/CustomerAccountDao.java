package com.saas.api.common.dao.crm;

import com.saas.api.common.entity.crm.CustomerAccount;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CustomerAccountDao {
	
    int deleteByPrimaryKey(Long id);

    int insert(CustomerAccount record);

    int insertSelective(CustomerAccount record);

    CustomerAccount selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerAccount record);

    int updateByPrimaryKey(CustomerAccount record);

    List<CustomerAccount> selectByCustomerId(@Param(value = "customerId") Long customerId, 
    		@Param(value = "accountType") Integer accountType,
    		@Param(value = "status") Integer status);

    CustomerAccount selectByCustomerIdAndCardId(@Param(value = "customerId") Long customerId, 
    		@Param(value = "cardId") Long cardId,
    		@Param(value = "status") Integer status);
    
    List<CustomerAccount> listData(@Param(value = "groupId") Integer groupId,
    		@Param(value = "companyId") Integer companyId, 
    		@Param(value = "departmentId") Integer departmentId,
    		@Param(value = "adminId") Integer adminId, 
    		@Param(value = "accountType") Integer accountType,
    		@Param(value = "status") Integer status,
    		@Param(value = "startIndex") Integer startIndex, @Param(value = "pageSize") Integer pageSize);
    
    int countData(@Param(value = "groupId") Integer groupId,
    		@Param(value = "companyId") Integer companyId, 
    		@Param(value = "departmentId") Integer departmentId,
    		@Param(value = "adminId") Integer adminId, 
    		@Param(value = "accountType") Integer accountType,
    		@Param(value = "status") Integer status);
    
}