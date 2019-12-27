package com.saas.api.common.dao.crm;

import com.saas.api.admin.res.select.crm.CardCategorySelect;
import com.saas.api.common.entity.crm.CardCategory;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CardCategoryDao {
	
	CardCategory selectByPrimaryKey(Integer categoryId);
	
    int deleteByPrimaryKey(Integer categoryId);

    int insert(CardCategory record);

    int insertSelective(CardCategory record);
    
    int updateByPrimaryKeySelective(CardCategory record);

    int updateByPrimaryKey(CardCategory record);
    
    CardCategory selectByName(@Param(value = "groupId") Integer groupId,
    		@Param(value = "companyId") Integer companyId,@Param(value = "departmentId") Integer departmentId,
    		@Param(value = "adminId") Integer adminId, 
    		@Param(value = "categoryName") String categoryName, 
    		@Param(value = "lanType") String lanType);

    List<CardCategory> listData(@Param(value = "groupId") Integer groupId,
    		@Param(value = "companyId") Integer companyId, @Param(value = "departmentId") Integer departmentId,
    		@Param(value = "adminId") Integer adminId, 
    		@Param(value = "cardType") Integer cardType, 
    		@Param(value = "categoryId") Integer categoryId,
    		@Param(value = "lanType") String lanType, 
    		@Param(value = "startIndex") Integer startIndex, @Param(value = "pageSize") Integer pageSize);

    int countData(@Param(value = "groupId") Integer groupId,
    		@Param(value = "companyId") Integer companyId, @Param(value = "departmentId") Integer departmentId,
    		@Param(value = "adminId") Integer adminId, 
    		@Param(value = "cardType") Integer cardType, 
    		@Param(value = "categoryId") Integer categoryId,
    		@Param(value = "lanType") String lanType);

    
    List<CardCategory> findByObject(CardCategory record);
   
    List<CardCategorySelect> findSelectObject(CardCategory record);

}