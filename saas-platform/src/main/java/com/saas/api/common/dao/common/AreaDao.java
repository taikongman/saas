package com.saas.api.common.dao.common;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.saas.api.admin.res.select.common.AreaSelect;
import com.saas.api.common.entity.common.Area;

import java.util.List;

@Mapper
public interface AreaDao {
    
    /**
     * * 根据id查询
     * @param id 传入的id
     * @return
     */
	Area findById(String areaId);
    
    /**
     * * 根据name查询
     * @param name 传入的companyName
     * @return
     */
	Area findByName(String areaName);

    
    /**
     * * 插入
     * @param 
     * @return
     */
    int insertData(Area record);
    
    /**
     * * 插入根据条件插入数据
     * @param 
     * @return
     */
    int insertSelective(Area record);
    
    /**
     * 更新
     * @param 
     * @return
     */
    int updateData(Area record);
    
    /**
     * * 删除
     * @param id
     * @return
     */
    int deleteById(String areaId);
    
	/**
	 * * 查询列表
     * @return 列表
     */
    List<Area> listData(@Param(value = "cityId") String cityId, 
    		@Param(value = "areaId") String areaId, 
    		@Param(value = "startIndex") Integer startIndex, @Param(value = "pageSize") Integer pageSize);
    
    /**
     * * 统计数据
     * @param 
     * @return
     */
    int countData(@Param(value = "cityId") String cityId, 
    		@Param(value = "areaId") String areaId);
    
    /**
     * * 根据对象查询数据列表
     * @param group
     * @return
     */
    List<Area> findByObject(Area record);
    
    /**
     * * 查询下拉列表
     * @param group
     * @return
     */
    List<AreaSelect> findSelectObject(Area record);
}
