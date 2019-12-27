package com.saas.api.common.dao.common;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.saas.api.admin.res.select.common.UnitSelect;
import com.saas.api.common.entity.common.Unit;

import java.util.List;

@Mapper
public interface UnitDao {
    
    /**
     * * 根据id查询
     * @param id 传入的id
     * @return
     */
	Unit findById(Integer id);
    
    /**
     * * 根据name查询
     * @param name 传入的companyName
     * @return
     */
	Unit findByName(String unitName);

    
    /**
     * * 插入
     * @param 
     * @return
     */
    int insertData(Unit record);
    
    /**
     * * 插入根据条件插入数据
     * @param 
     * @return
     */
    int insertSelective(Unit record);
    
    /**
     * 更新
     * @param 
     * @return
     */
    int updateData(Unit record);
    
    /**
     * * 删除
     * @param id
     * @return
     */
    int deleteById(Integer id);
    
	/**
	 * * 查询列表
     * @return 列表
     */
    List<Unit> listData(@Param(value = "id") Integer id, @Param(value = "lanType") String lanType,
    		@Param(value = "startIndex") Integer startIndex, @Param(value = "pageSize") Integer pageSize);
    
    /**
     * * 统计数据
     * @param 
     * @return
     */
    int countData(@Param(value = "id") Integer id, @Param(value = "lanType") String lanType);
    
    /**
     * * 根据对象查询数据列表
     * @param group
     * @return
     */
    List<Unit> findByObject(Unit record);
    
    /**
     * * 查询下拉列表
     * @param group
     * @return
     */
    List<UnitSelect> findSelectObject(Unit record);
    
    
    
}
