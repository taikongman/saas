package com.saas.api.common.dao.crm;

import com.saas.api.common.entity.crm.OrderProject;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderProjectDao {
	
    int deleteByPrimaryKey(Long id);
    
    int deleteByOrderId(Long orderId);

    int insert(OrderProject record);

    int insertSelective(OrderProject record);

    OrderProject selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderProject record);

    int updateByPrimaryKey(OrderProject record);

    List<OrderProject> selectByOrderId(Long orderId);
}