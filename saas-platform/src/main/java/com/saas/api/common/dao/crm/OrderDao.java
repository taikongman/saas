package com.saas.api.common.dao.crm;

import com.saas.api.common.entity.crm.Order;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderDao {
    int deleteByPrimaryKey(Long id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    List<Order> orderList(Map<String, Object> params);

    int countOrder(Map<String, Object> params);

    List<Order> orderList4OrCond(Map<String, Object> params);

    int countOrder4OrCond(Map<String, Object> params);
}