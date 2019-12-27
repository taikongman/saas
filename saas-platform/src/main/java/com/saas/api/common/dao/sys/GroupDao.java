package com.saas.api.common.dao.sys;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.saas.api.common.entity.sys.Group;

import java.util.List;

@Mapper
public interface GroupDao {
    
    /**
     * 查询列表
     * @return 列表
     */
    List<Group> listData(@Param(value = "groupId") Integer groupId, @Param(value = "lanType") String lanType, @Param(value = "startIndex") Integer startIndex, @Param(value = "pageSize") Integer pageSize);
    
    /**
     * 统计数据
     * @param 
     * @return
     */
    int countData(@Param(value = "groupId") Integer groupId, @Param(value = "lanType") String lanType);
    
    /**
     * 根据对象查询数据列表
     * @param group
     * @return
     */
    List<Group> findByObject(Group record);
    
    /**
     * 根据id查询
     * @param id 传入的id
     * @return
     */
    Group findById(Integer groupId);
    
    /**
     * 根据name查询
     * @param name 传入的groupName
     * @return
     */
    Group findByName(String groupName);

    /**
     * 插入
     * @param 
     * @return
     */
    int insertData(Group record);
    
    /**
     * 插入根据条件插入数据
     * @param 
     * @return
     */
    int insertSelective(Group record);
    
    /**
     * 更新
     * @param 
     * @return
     */
    int updateData(Group record);
        
    /**
     * 删除
     * @param id
     * @return
     */
    int deleteById(Integer groupId);
    
}
