package com.saas.api.common.dao.common;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.saas.api.admin.res.select.common.AutoModelSelect;
import com.saas.api.common.entity.common.AutoModel;

import java.util.List;

@Mapper
public interface AutoModelDao {
    
    /**
     * * 根据id查询
     * @param id 传入的id
     * @return
     */
	AutoModel findById(Integer modelId);
    
    /**
     * * 根据name查询
     * @param name 传入的companyName
     * @return
     */
	AutoModel findByName(String modelName);

    
    /**
     * * 插入
     * @param 
     * @return
     */
    int insertData(AutoModel record);
    
    /**
     * * 插入根据条件插入数据
     * @param 
     * @return
     */
    int insertSelective(AutoModel record);
    
    /**
     * 更新
     * @param 
     * @return
     */
    int updateData(AutoModel record);
    
    /**
     * * 删除
     * @param id
     * @return
     */
    int deleteById(Integer modelId);
    
	/**
	 * * 查询列表
     * @return 列表
     */
    List<AutoModel> listData(@Param(value = "brandId") Integer brandId, @Param(value = "seriesId") Integer seriesId,
    		@Param(value = "modelId") Integer modelId, 
    		@Param(value = "lanType") String lanType,
    		@Param(value = "startIndex") Integer startIndex, @Param(value = "pageSize") Integer pageSize);
    
    /**
     * * 统计数据
     * @param 
     * @return
     */
    int countData(@Param(value = "brandId") Integer brandId, @Param(value = "seriesId") Integer seriesId,
    		@Param(value = "modelId") Integer modelId, 
    		@Param(value = "lanType") String lanType);
    
    /**
     * * 根据对象查询数据列表
     * @param group
     * @return
     */
    List<AutoModel> findByObject(AutoModel record);
    
    /**
     * * 查询下拉列表
     * @param group
     * @return
     */
    List<AutoModelSelect> findSelectObject(AutoModel record);
    
    /**
     * * 查询下拉年份列表
     * @param group
     * @return
     */
    List<Integer> findAutoYears(AutoModel record);
    
}
