package com.saas.api.common.dao.crm;

import org.apache.ibatis.annotations.Mapper;

import com.saas.api.common.entity.crm.StatUser;

@Mapper
public interface StatUserDao {
    int deleteByPrimaryKey(Long id);

    int insert(StatUser record);

    int insertSelective(StatUser record);

    StatUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StatUser record);

    int updateByPrimaryKey(StatUser record);
}