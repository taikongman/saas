package com.saas.api.common.dao.crm;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.saas.api.common.entity.crm.CustomerConsumeRecord;

@Mapper
public interface CustomerConsumeRecordDao {
	
    int deleteByPrimaryKey(Long id);
    
    CustomerConsumeRecord selectByPrimaryKey(Long id);

    int insert(CustomerConsumeRecord record);

    int insertSelective(CustomerConsumeRecord record);

    int updateByPrimaryKeySelective(CustomerConsumeRecord record);

    int updateByPrimaryKey(CustomerConsumeRecord record);
    
    List<CustomerConsumeRecord> selectByCustomerId(@Param(value = "customerId") Long customerId, 
    		@Param(value = "consumeType") Integer consumeType,
    		@Param(value = "status") Integer status);
    
    List<CustomerConsumeRecord> selectByCustomerIdAndCardId(@Param(value = "customerId") Long customerId, 
    		@Param(value = "cardId") Integer cardId,
    		@Param(value = "status") Integer status);
    
    List<CustomerConsumeRecord> findByObject(CustomerConsumeRecord record);

    List<CustomerConsumeRecord> selectByOrderIId(@Param(value = "orderId") Long orderId, 
    		@Param(value = "status") Integer status);
    
    
}