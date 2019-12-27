package com.saas.api.common.dao.crm;

import com.saas.api.admin.res.select.crm.CommodityCategorySelect;
import com.saas.api.common.entity.crm.CommodityCategory;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommodityCategoryDao {
	
    int deleteByPrimaryKey(Integer categoryId);

    int insert(CommodityCategory record);

    int insertSelective(CommodityCategory record);

    CommodityCategory selectByPrimaryKey(Integer categoryId);
    
    CommodityCategory selectByName(@Param(value = "groupId") Integer groupId,
    		@Param(value = "companyId") Integer companyId,
    		@Param(value = "categoryName") String categoryName, 
    		@Param(value = "lanType") String lanType);

    int updateByPrimaryKeySelective(CommodityCategory record);

    int updateByPrimaryKey(CommodityCategory record);

    List<CommodityCategory> listData(@Param(value = "groupId") Integer groupId,
    		@Param(value = "companyId") Integer companyId, @Param(value = "departmentId") Integer departmentId,
    		@Param(value = "adminId") Integer adminId, 
    		@Param(value = "categoryId") Integer categoryId,
    		@Param(value = "lanType") String lanType, 
    		@Param(value = "startIndex") Integer startIndex, @Param(value = "pageSize") Integer pageSize);

    int countData(@Param(value = "groupId") Integer groupId,
    		@Param(value = "companyId") Integer companyId, @Param(value = "departmentId") Integer departmentId,
    		@Param(value = "adminId") Integer adminId, 
    		@Param(value = "categoryId") Integer categoryId,
    		@Param(value = "lanType") String lanType);

    
    List<CommodityCategory> findByObject(CommodityCategory record);
   
    List<CommodityCategorySelect> findSelectObject(CommodityCategory record);

}