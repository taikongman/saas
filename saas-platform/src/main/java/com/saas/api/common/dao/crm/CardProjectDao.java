package com.saas.api.common.dao.crm;

import com.saas.api.common.entity.crm.CardProject;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CardProjectDao {
	
	CardProject selectByPrimaryKey(Long id);
	
    int deleteByPrimaryKey(Long id);
    
    int deleteByByCardId(Long cardId);

    int insert(CardProject record);

    int insertSelective(CardProject record);
    
    int updateByPrimaryKeySelective(CardProject record);

    int updateByPrimaryKey(CardProject record);
    
    int countByCardId(Long cardId);
    
    List<CardProject> selectByCardId(Long cardId);
    
    List<CardProject> findByObject(CardProject record);

}