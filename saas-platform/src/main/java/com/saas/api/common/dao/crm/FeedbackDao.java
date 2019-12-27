package com.saas.api.common.dao.crm;

import org.apache.ibatis.annotations.Mapper;

import com.saas.api.common.entity.crm.Feedback;

@Mapper
public interface FeedbackDao {
	
    int deleteByPrimaryKey(Integer id);

    int insert(Feedback record);

    int insertSelective(Feedback record);

    Feedback selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Feedback record);

    int updateByPrimaryKey(Feedback record);
}