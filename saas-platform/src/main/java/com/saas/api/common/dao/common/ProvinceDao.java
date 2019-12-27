package com.saas.api.common.dao.common;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.saas.api.admin.res.select.common.ProvinceSelect;
import com.saas.api.common.entity.common.Province;

import java.util.List;

@Mapper
public interface ProvinceDao {
    
    /**
     * * 根据id查询
     * @param id 传入的id
     * @return
     */
    Province findById(String provinceId);
    
    /**
     * * 根据name查询
     * @param name 传入的companyName
     * @return
     */
    Province findByName(String provinceName);

    
    /**
     * * 插入
     * @param 
     * @return
     */
    int insertData(Province record);
    
    /**
     * * 插入根据条件插入数据
     * @param 
     * @return
     */
    int insertSelective(Province record);
    
    /**
     * 更新
     * @param 
     * @return
     */
    int updateData(Province record);
    
    /**
     * * 删除
     * @param id
     * @return
     */
    int deleteById(String provinceId);
    
	/**
	 * * 查询列表
     * @return 列表
     */
    List<Province> listData(@Param(value = "provinceId") String provinceId, @Param(value = "startIndex") Integer startIndex, @Param(value = "pageSize") Integer pageSize);
    
    /**
     * * 统计数据
     * @param 
     * @return
     */
    int countData(@Param(value = "provinceId") String provinceId);
    
    /**
     * * 根据对象查询数据列表
     * @param group
     * @return
     */
    List<Province> findByObject(Province record);
    
    
    /**
     * * 查询下拉列表
     * @param group
     * @return
     */
    List<ProvinceSelect> findSelectObject(Province record);
    
    
}
