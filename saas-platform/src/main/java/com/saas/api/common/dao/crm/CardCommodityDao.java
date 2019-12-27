package com.saas.api.common.dao.crm;

import com.saas.api.common.entity.crm.CardCommodity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CardCommodityDao {
	
	CardCommodity selectByPrimaryKey(Long id);
	
    int deleteByPrimaryKey(Long id);
    
    int deleteByCardId(Long cardId);

    int insert(CardCommodity record);

    int insertSelective(CardCommodity record);

    int updateByPrimaryKeySelective(CardCommodity record);

    int updateByPrimaryKey(CardCommodity record);
    
    int countByCardId(Long cardId);
    
    List<CardCommodity> selectByCardId(Long cardId);
    
    List<CardCommodity> findByObject(CardCommodity record);

}