package com.saas.api.common.dao.crm;

import com.saas.api.common.entity.crm.SysData;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysDataDao {
    int deleteByPrimaryKey(Integer id);

    int insert(SysData record);

    int insertSelective(SysData record);

    SysData selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysData record);

    int updateByPrimaryKey(SysData record);

    List<SysData> selectByDataType(Integer dataType);
}