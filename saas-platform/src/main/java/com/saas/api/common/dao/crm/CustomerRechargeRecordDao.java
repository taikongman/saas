package com.saas.api.common.dao.crm;

import com.saas.api.common.entity.crm.CustomerRechargeRecord;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CustomerRechargeRecordDao {
	
    int deleteByPrimaryKey(Long id);

    int insert(CustomerRechargeRecord record);

    int insertSelective(CustomerRechargeRecord record);

    CustomerRechargeRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerRechargeRecord record);
    
    int updateByPrimaryKey(CustomerRechargeRecord record);
    
    int updateByStatusAccountId(CustomerRechargeRecord record);

    List<CustomerRechargeRecord> selectByCustomerId(@Param(value = "customerId") Long customerId, 
    		@Param(value = "accountType") Integer accountType,
    		@Param(value = "status") Integer status,
    		@Param(value = "startIndex") Integer startIndex, @Param(value = "pageSize") Integer pageSize);

    int countByCustomerId(@Param(value = "customerId") Long customerId, 
    		@Param(value = "accountType") Integer accountType,
    		@Param(value = "status") Integer status);

}