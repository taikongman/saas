package com.saas.api.common.dao.sys.auth;

import org.apache.ibatis.annotations.Mapper;

import com.saas.api.common.entity.sys.auth.RoleAdmin;

import java.util.List;


@Mapper
public interface RoleAdminDao {
	
	/**
     * 根据 多个 adminId 查询
     * @param adminIds 传入的 adminIds
     * @return
     */
    List<RoleAdmin> listByAdminIdIn(List<Integer> adminIds);

    /**
     * 根据 role_id 查询 admin_id
     * @param roleId 传入的 roleId
     * @return
     */
    List<RoleAdmin> listByRoleId(Integer roleId);
    
    /**
     * 根据id查询
     * @param id 传入的id
     * @return
     */
    List<RoleAdmin> findByRoleId(Integer roleId);
    
    /**
     * 根据id查询
     * @param id 传入的id
     * @return
     */
    RoleAdmin findByAdminId(Integer adminId);
    
    
    /**
     * 插入
     * @param User
     * @return
     */
    int insertData(RoleAdmin record);
    
    
    /**
     * 更新
     * @param User
     * @return
     */
    int updateData(RoleAdmin record);
    
    int updateDataByAdminId(RoleAdmin record);
    
    /**
     * 删除
     * @param id
     * @return
     */
    int deleteById(Integer id);
    
    int deleteByAdminId(Integer adminId);
    
}
