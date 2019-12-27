package com.saas.api.common.dao.crm;

import com.saas.api.common.entity.crm.CustomerAccountProject;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomerAccountProjectDao {
	
	CustomerAccountProject selectByPrimaryKey(Long id);
	
    int deleteByPrimaryKey(Long id);
    
    int deleteByAccountId(Long accountId);

    int insert(CustomerAccountProject record);

    int insertSelective(CustomerAccountProject record);
    
    int updateByPrimaryKeySelective(CustomerAccountProject record);

    int updateByPrimaryKey(CustomerAccountProject record);
    
    int countByAccountId(Long accountId);
    
    List<CustomerAccountProject> selectByAccountId(Long accountId);
    
    List<CustomerAccountProject> findByObject(CustomerAccountProject record);

}