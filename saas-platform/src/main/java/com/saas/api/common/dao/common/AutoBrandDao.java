package com.saas.api.common.dao.common;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.saas.api.admin.res.select.common.AutoBrandSelect;
import com.saas.api.common.entity.common.AutoBrand;

import java.util.List;

@Mapper
public interface AutoBrandDao {
    
    /**
     * * 根据id查询
     * @param id 传入的id
     * @return
     */
	AutoBrand findById(Integer brandId);
    
    /**
     * * 根据name查询
     * @param name 传入的companyName
     * @return
     */
	AutoBrand findByName(String brandName);

    
    /**
     * * 插入
     * @param 
     * @return
     */
    int insertData(AutoBrand record);
    
    /**
     * * 插入根据条件插入数据
     * @param 
     * @return
     */
    int insertSelective(AutoBrand record);
    
    /**
     * 更新
     * @param 
     * @return
     */
    int updateData(AutoBrand record);
    
    /**
     * * 删除
     * @param id
     * @return
     */
    int deleteById(Integer brandId);
    
	/**
	 * * 查询列表
     * @return 列表
     */
    List<AutoBrand> listData(@Param(value = "brandId") Integer brandId,
    		@Param(value = "lanType") String lanType,
    		@Param(value = "startIndex") Integer startIndex, @Param(value = "pageSize") Integer pageSize);
    
    /**
     * * 统计数据
     * @param 
     * @return
     */
    int countData(@Param(value = "brandId") Integer brandId,
    		@Param(value = "lanType") String lanType);
    
    /**
     * * 根据对象查询数据列表
     * @param group
     * @return
     */
    List<AutoBrand> findByObject(AutoBrand record);
    
    /**
     * * 查询下拉列表
     * @param group
     * @return
     */
    List<AutoBrandSelect> findSelectObject(AutoBrand record);
    
    
}
