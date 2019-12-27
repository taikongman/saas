package com.saas.api.common.dao.common;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.saas.api.admin.res.select.common.AutoSeriesSelect;
import com.saas.api.common.entity.common.AutoSeries;

import java.util.List;

@Mapper
public interface AutoSeriesDao {
    
    /**
     * * 根据id查询
     * @param id 传入的id
     * @return
     */
	AutoSeries findById(Integer seriesId);
    
    /**
     * * 根据name查询
     * @param name 传入的companyName
     * @return
     */
	AutoSeries findByName(String seriesName);

    
    /**
     * * 插入
     * @param 
     * @return
     */
    int insertData(AutoSeries record);
    
    /**
     * * 插入根据条件插入数据
     * @param 
     * @return
     */
    int insertSelective(AutoSeries record);
    
    /**
     * 更新
     * @param 
     * @return
     */
    int updateData(AutoSeries record);
    
    /**
     * * 删除
     * @param id
     * @return
     */
    int deleteById(Integer seriesId);
    
	/**
	 * * 查询列表
     * @return 列表
     */
    List<AutoSeries> listData(@Param(value = "brandId") Integer brandId, @Param(value = "seriesId") Integer seriesId,
    		@Param(value = "lanType") String lanType,
    		@Param(value = "startIndex") Integer startIndex, @Param(value = "pageSize") Integer pageSize);
    
    /**
     * * 统计数据
     * @param 
     * @return
     */
    int countData(@Param(value = "brandId") Integer brandId, @Param(value = "seriesId") Integer seriesId, 
    		@Param(value = "lanType") String lanType);
    
    /**
     * * 根据对象查询数据列表
     * @param group
     * @return
     */
    List<AutoSeries> findByObject(AutoSeries record);
    
    /**
     * * 查询下拉列表
     * @param group
     * @return
     */
    List<AutoSeriesSelect> findSelectObject(AutoSeries record);
    
}
