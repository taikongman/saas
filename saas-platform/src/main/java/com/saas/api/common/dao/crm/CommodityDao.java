package com.saas.api.common.dao.crm;

import com.saas.api.common.entity.crm.Commodity;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommodityDao {
    int deleteByPrimaryKey(Long commodityId);

    int insert(Commodity record);

    int insertSelective(Commodity record);

    Commodity selectByPrimaryKey(Long commodityId);

    int updateByPrimaryKeySelective(Commodity record);

    int updateByPrimaryKey(Commodity record);
    
    List<Commodity> findByObject(Commodity record);
    
    Commodity findByCommodityName(@Param(value = "groupId") Integer groupId,
    		@Param(value = "companyId") Integer companyId, @Param(value = "departmentId") Integer departmentId,
    		@Param(value = "adminId") Long adminId, 
    		@Param(value = "commodityName") String commodityName);

    List<Commodity> selectByCond(@Param(value = "groupId") Integer groupId,
    		@Param(value = "companyId") Integer companyId, @Param(value = "departmentId") Integer departmentId,
    		@Param(value = "adminId") Long adminId, 
    		@Param(value = "categoryId") Integer categoryId, 
    		@Param(value = "commodityCode") String commodityCode, @Param(value = "commodityName") String commodityName, 
    		@Param(value = "pinyinCode") String pinyinCode, 
    		@Param(value = "status") Integer status, 
    		@Param(value = "lanType") String lanType, 
    		@Param(value = "startIndex") Integer startIndex, @Param(value = "pageSize") Integer pageSize);

    int countByCond(@Param(value = "groupId") Integer groupId,
    		@Param(value = "companyId") Integer companyId, @Param(value = "departmentId") Integer departmentId,
    		@Param(value = "adminId") Long adminId, 
    		@Param(value = "categoryId") Integer categoryId, 
    		@Param(value = "commodityCode") String commodityCode, @Param(value = "commodityName") String commodityName, 
    		@Param(value = "pinyinCode") String pinyinCode, 
    		@Param(value = "status") Integer status, 
    		@Param(value = "lanType") String lanType);

    List<Commodity> warnDownList(@Param(value = "groupId") Integer groupId,
    		@Param(value = "companyId") Integer companyId, @Param(value = "departmentId") Integer departmentId,
    		@Param(value = "adminId") Long adminId, @Param(value = "lanType") String lanType, 
    		@Param(value = "commodityCode") String commodityCode, @Param(value = "commodityName") String commodityName, 
    		@Param(value = "status") Integer status, 
    		@Param(value = "startIndex") Integer startIndex, @Param(value = "pageSize") Integer pageSize);

    int countWarnDown(@Param(value = "groupId") Integer groupId,
    		@Param(value = "companyId") Integer companyId, @Param(value = "departmentId") Integer departmentId,
    		@Param(value = "adminId") Long adminId, @Param(value = "lanType") String lanType, 
    		@Param(value = "commodityCode") String commodityCode, @Param(value = "commodityName") String commodityName,
    		@Param(value = "status") Integer status);

    List<Commodity> warnUpList(@Param(value = "groupId") Integer groupId,
    		@Param(value = "companyId") Integer companyId, @Param(value = "departmentId") Integer departmentId,
    		@Param(value = "adminId") Long adminId, @Param(value = "lanType") String lanType, 
    		@Param(value = "commodityCode") String commodityCode, @Param(value = "commodityName") String commodityName, 
    		@Param(value = "status") Integer status, 
    		@Param(value = "startIndex") Integer startIndex, @Param(value = "pageSize") Integer pageSize);

    int countWarnUp(@Param(value = "groupId") Integer groupId,
    		@Param(value = "companyId") Integer companyId, @Param(value = "departmentId") Integer departmentId,
    		@Param(value = "adminId") Long adminId, @Param(value = "lanType") String lanType, 
    		@Param(value = "commodityCode") String commodityCode, @Param(value = "commodityName") String commodityName, 
    		@Param(value = "status") Integer status);

    int countByCode(@Param(value = "groupId") Integer groupId,
    		@Param(value = "companyId") Integer companyId, @Param(value = "departmentId") Integer departmentId,
    		@Param(value = "adminId") Long adminId, @Param(value = "lanType") String lanType, 
    		@Param(value = "commodityCode") String commodityCode);

    Commodity selectByCode(@Param(value = "groupId") Integer groupId,
    		@Param(value = "companyId") Integer companyId, @Param(value = "departmentId") Integer departmentId,
    		@Param(value = "adminId") Long adminId, @Param(value = "lanType") String lanType, 
    		@Param(value = "commodityCode") String commodityCode);
    

}