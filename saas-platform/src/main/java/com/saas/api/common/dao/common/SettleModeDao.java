package com.saas.api.common.dao.common;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.saas.api.admin.res.select.common.SettleModeSelect;
import com.saas.api.common.entity.common.SettleMode;

import java.util.List;

@Mapper
public interface SettleModeDao {
    
    /**
     * * 根据id查询
     * @param id 传入的id
     * @return
     */
	SettleMode findById(Integer id);
    
    /**
     * * 根据name查询
     * @param name 传入的name
     * @return
     */
	SettleMode findByName(String name);

    
    /**
     * * 插入
     * @param 
     * @return
     */
    int insertData(SettleMode record);
    
    /**
     * * 插入根据条件插入数据
     * @param 
     * @return
     */
    int insertSelective(SettleMode record);
    
    /**
     * * 更新
     * @param 
     * @return
     */
    int updateData(SettleMode record);
    
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
    List<SettleMode> listData(@Param(value = "id") Integer id, @Param(value = "lanType") String lanType,
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
    List<SettleMode> findByObject(SettleMode record);
    
    /**
     * * 查询下拉列表
     * @param group
     * @return
     */
    List<SettleModeSelect> findSelectObject(SettleMode record);
    
    
    
}
