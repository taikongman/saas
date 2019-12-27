package com.saas.api.common.dao.crm;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.saas.api.admin.res.select.crm.CardSelect;
import com.saas.api.common.entity.crm.Card;

@Mapper
public interface CardDao {
	
	Card selectByPrimaryKey(Long id);
	
    int deleteByPrimaryKey(Long id);

    int insert(Card record);

    int insertSelective(Card record);

    int updateByPrimaryKeySelective(Card record);

    int updateByPrimaryKey(Card record);
    
    Card selectByName(@Param(value = "groupId") Integer groupId,
    		@Param(value = "companyId") Integer companyId, 
    		@Param(value = "departmentId") Integer departmentId, @Param(value = "adminId") Long adminId, 
    		@Param(value = "cardName") String cardName, 
    		@Param(value = "lanType") String lanType);
    
    List<Card> listData(Map<String, Object> params);

    int countData(Map<String, Object> params);
    
    List<Card> findByObject(Card record);
    
    List<CardSelect> findSelectObject(Card record);
}