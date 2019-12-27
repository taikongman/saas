package com.saas.api.common.dao.crm;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.saas.api.common.entity.crm.CustomerPay;

@Mapper
public interface CustomerPayDao {
	
    int deleteByPrimaryKey(Long id);

    int insert(CustomerPay record);

    int insertSelective(CustomerPay record);

    CustomerPay selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerPay record);

    int updateByPrimaryKey(CustomerPay record);
    
    List<CustomerPay> selectByCustomerId(@Param(value = "customerId") Long customerId, 
    		@Param(value = "status") Integer status);

    
}