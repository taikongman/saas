package com.saas.api.common.dao.common;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.saas.api.admin.res.select.common.TransportModeSelect;
import com.saas.api.common.entity.common.TransportMode;

import java.util.List;

@Mapper
public interface TransportModeDao {
    
    /**
     * * 根据id查询
     * @param id 传入的id
     * @return
     */
	TransportMode findById(Integer id);
    
    /**
     * * 根据name查询
     * @param name 传入的companyName
     * @return
     */
	TransportMode findByName(String transportName);

    
    /**
     * * 插入
     * @param 
     * @return
     */
    int insertData(TransportMode record);
    
    /**
     * * 插入根据条件插入数据
     * @param 
     * @return
     */
    int insertSelective(TransportMode record);
    
    /**
     * 更新
     * @param 
     * @return
     */
    int updateData(TransportMode record);
    
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
    List<TransportMode> listData(@Param(value = "id") Integer id, @Param(value = "lanType") String lanType,
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
    List<TransportMode> findByObject(TransportMode record);
    
    /**
     * * 查询下拉列表
     * @param group
     * @return
     */
    List<TransportModeSelect> findSelectObject(TransportMode record);
    
    
    
}
