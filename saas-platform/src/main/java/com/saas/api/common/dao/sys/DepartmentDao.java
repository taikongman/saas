package com.saas.api.common.dao.sys;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.saas.api.common.entity.sys.Department;

import java.util.List;

/**
 * @Description: 公司部门DAO
 * @Author: 徐未
 * @Date: 2019/11/18
 */
@Mapper
public interface DepartmentDao {
    
	/**
     * 查询列表
     * @return 列表
     */
    List<Department> listData(@Param(value = "groupId") Integer groupId, @Param(value = "companyId") Integer companyId, 
    		@Param(value = "departmentId") Integer departmentId, @Param(value = "lanType") String lanType, 
    		@Param(value = "startIndex") Integer startIndex, @Param(value = "pageSize") Integer pageSize);
    
    /**
     * 统计数据
     * @param 
     * @return
     */
    int countData(@Param(value = "groupId") Integer groupId, @Param(value = "companyId") Integer companyId, 
    		@Param(value = "departmentId") Integer departmentId, @Param(value = "lanType") String lanType);
    
    /**
     * 根据对象查询数据列表
     * @param group
     * @return
     */
    List<Department> findByObject(Department record);
    
    
    /**
     * 根据id查询
     * @param id 传入的id
     * @return
     */
    Department findById(Integer departmentId);
    
    /**
     * 根据name查询
     * @param name 传入的groupName
     * @return
     */
    Department findByName(String departmentName);
    
    /**
     * 插入
     * @param 
     * @return
     */
    int insertData(Department record);
    
    /**
     * 插入根据条件插入数据
     * @param 
     * @return
     */
    int insertSelective(Department record);
    
    /**
     * 更新
     * @param
     * @return
     */
    int updateData(Department record);
    
    /**
     * 删除
     * @param id
     * @return
     */
    int deleteById(Integer departmentId);
    
    
}
