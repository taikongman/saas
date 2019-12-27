package com.saas.api.common.dao.crm;

import org.apache.ibatis.annotations.Mapper;

import com.saas.api.common.entity.crm.StatRevenue;

@Mapper
public interface StatRevenueDao {
    int deleteByPrimaryKey(Long id);

    int insert(StatRevenue record);

    int insertSelective(StatRevenue record);

    StatRevenue selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StatRevenue record);

    int updateByPrimaryKey(StatRevenue record);
}