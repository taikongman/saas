package com.saas.api.common.dao.crm;

import com.saas.api.common.entity.crm.CommodityAuto;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommodityAutoDao {
	
	CommodityAuto selectByPrimaryKey(Long id);
	
    int deleteByPrimaryKey(Long id);
    
    int deleteByCommodityId(Long commodityId);

    int insert(CommodityAuto record);

    int insertSelective(CommodityAuto record);

    int updateByPrimaryKeySelective(CommodityAuto record);

    int updateByPrimaryKey(CommodityAuto record);

    List<CommodityAuto> findByObject(CommodityAuto record);
    
    List<CommodityAuto> findByCommodityId(Long commodityId);

}