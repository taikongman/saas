package com.saas.api.common.dao.common;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.saas.api.admin.res.select.common.CitySelect;
import com.saas.api.common.entity.common.City;

import java.util.List;

@Mapper
public interface CityDao {
    
    /**
     * * 根据id查询
     * @param id 传入的id
     * @return
     */
	City findById(String cityId);
    
    /**
     * * 根据name查询
     * @param name 传入的companyName
     * @return
     */
    City findByName(String cityName);

    
    /**
     * * 插入
     * @param 
     * @return
     */
    int insertData(City record);
    
    /**
     * * 插入根据条件插入数据
     * @param 
     * @return
     */
    int insertSelective(City record);
    
    /**
     * 更新
     * @param 
     * @return
     */
    int updateData(City record);
    
    /**
     * * 删除
     * @param id
     * @return
     */
    int deleteById(String cityId);
    
	/**
	 * * 查询列表
     * @return 列表
     */
    List<City> listData(@Param(value = "provinceId") String provinceId, @Param(value = "cityId") String cityId,
    		@Param(value = "startIndex") Integer startIndex, @Param(value = "pageSize") Integer pageSize);
    
    /**
     * * 统计数据
     * @param 
     * @return
     */
    int countData(@Param(value = "provinceId") String provinceId, @Param(value = "cityId") String cityId);
    
    /**
     * * 根据对象查询数据列表
     * @param group
     * @return
     */
    List<City> findByObject(City record);
    
    /**
     * * 查询下拉列表
     * @param group
     * @return
     */
    List<CitySelect> findSelectObject(City record);
    
    
    /**
     * *返回所有省份车牌前两位
     * @return
     */
    List<String> findAllAutoCode();
    
    
}
