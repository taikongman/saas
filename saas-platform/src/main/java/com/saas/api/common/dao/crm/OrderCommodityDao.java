package com.saas.api.common.dao.crm;

import com.saas.api.common.entity.crm.OrderCommodity;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderCommodityDao {
	
    int deleteByPrimaryKey(Long id);
    
    int deleteByOrderId(Long orderId);

    int insert(OrderCommodity record);

    int insertSelective(OrderCommodity record);

    OrderCommodity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderCommodity record);

    int updateByPrimaryKey(OrderCommodity record);

    List<OrderCommodity> listData(Map<?, ?> params);

    int countData(Map<?, ?> params);
    
    List<OrderCommodity> findByObject(OrderCommodity record);

    int countByCommodityId(Long commodityId);
    
    List<OrderCommodity> selectByOrderId(Long orderId);
    
}