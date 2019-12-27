package com.saas.api.common.dao.sys;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.saas.api.common.entity.sys.Company;

import java.util.List;

@Mapper
public interface CompanyDao {
    
	/**
     * 查询列表
     * @return 列表
     */
    List<Company> listData(@Param(value = "groupId") Integer groupId, @Param(value = "companyId") Integer companyId, @Param(value = "lanType") String lanType, @Param(value = "startIndex") Integer startIndex, @Param(value = "pageSize") Integer pageSize);
    
    /**
     * 统计数据
     * @param 
     * @return
     */
    int countData(@Param(value = "groupId") Integer groupId, @Param(value = "companyId") Integer companyId, @Param(value = "lanType") String lanType);
    
    /**
     * 根据对象查询数据列表
     * @param group
     * @return
     */
    List<Company> findByObject(Company record);
    
    /**
     * 根据id查询
     * @param id 传入的id
     * @return
     */
    Company findById(Integer companyId);
    
    /**
     * 根据name查询
     * @param name 传入的companyName
     * @return
     */
    Company findByName(String companyName);

    
    /**
     * 插入
     * @param 
     * @return
     */
    int insertData(Company record);
    
    /**
     * 插入根据条件插入数据
     * @param 
     * @return
     */
    int insertSelective(Company record);
    
    /**
     * 更新
     * @param 
     * @return
     */
    int updateData(Company record);
    
    /**
     * 删除
     * @param id
     * @return
     */
    int deleteById(Integer companyId);
    
    
}
