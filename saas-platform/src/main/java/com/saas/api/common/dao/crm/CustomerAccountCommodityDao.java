package com.saas.api.common.dao.crm;

import com.saas.api.common.entity.crm.CustomerAccountCommodity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomerAccountCommodityDao {
	
	CustomerAccountCommodity selectByPrimaryKey(Long id);
	
    int deleteByPrimaryKey(Long id);
    
    int deleteByAccountId(Long accountId);

    int insert(CustomerAccountCommodity record);

    int insertSelective(CustomerAccountCommodity record);

    int updateByPrimaryKeySelective(CustomerAccountCommodity record);

    int updateByPrimaryKey(CustomerAccountCommodity record);
    
    int countByAccountId(Long accountId);
    
    List<CustomerAccountCommodity> selectByAccountId(Long accountId);
    
    List<CustomerAccountCommodity> findByObject(CustomerAccountCommodity record);

}