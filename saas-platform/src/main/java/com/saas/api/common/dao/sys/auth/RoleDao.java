package com.saas.api.common.dao.sys.auth;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.saas.api.common.entity.sys.auth.Role;

import java.util.List;

@Mapper
public interface RoleDao {

	/**
     * 后台业务查询列表
     * @return 列表
     */
    List<Role> listData(@Param(value = "groupId") Integer groupId, @Param(value = "companyId") Integer companyId,
    		@Param(value = "roleId") Long roleId, 
    		@Param(value = "lanType") String lanType, 
    		@Param(value = "startIndex") Integer startIndex, @Param(value = "pageSize") Integer pageSize);
    
    /**
     * 统计数据
     * @param 
     * @return
     */
    int countData(@Param(value = "groupId") Integer groupId, @Param(value = "companyId") Integer companyId,
    		@Param(value = "roleId") Long roleId, 
    		@Param(value = "lanType") String lanType);
    
    
    /**
     * 根据对象模糊查询
     * @param record
     * @return
     */
    List<Role> findByObject(Role record);
    
    /**
     * 根据id查询
     * @param id 传入的id
     * @return
     */
    Role findById(Long roleId);

    /**
     * 根据Name
     * @param roleName 名
     * @return
     */
    Role findByName(String roleName);
    
    /**
     * 插入
     * @param User
     * @return
     */
    int insertData(Role record);
    
    /**
     * 插入
     * @param User
     * @return
     */
    int insertSelective(Role record);
    
    
    /**
     * 更新
     * @param User
     * @return
     */
    int updateData(Role record);
    
    /**
     * 删除
     * @param id
     * @return
     */
    int deleteById(Long roleId);
    
}
