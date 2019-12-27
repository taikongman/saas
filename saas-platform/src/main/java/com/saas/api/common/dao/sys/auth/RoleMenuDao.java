package com.saas.api.common.dao.sys.auth;

import org.apache.ibatis.annotations.Mapper;

import com.saas.api.common.entity.sys.auth.RoleMenu;

import java.util.List;

@Mapper
public interface RoleMenuDao {

    /**
     * 根据roleIds查询
     * @param roleIds 传入的id
     * @return
     */
    List<RoleMenu> listByRoleIdIn(List<Integer> roleIds);

    /**
     * 根据 roleId 查询
     * @param roleId 传入的id
     * @return
     */
    List<RoleMenu> listByRoleId(Integer roleId);

    /**
     * 根据 menuId 查询
     * @param menuId
     * @return
     */
    RoleMenu findByMenuId(Integer menuId);
    
    /**
     * 批量插入
     * @param authPermissionList
     * @return
     */
    int insertMenuAll(List<RoleMenu> roleMenuList);

    /**
     * 根据menuId删除
     * @param menuId
     * @return
     */
    int deleteByMenuId(Integer menuId);
    
    /**
     * 根据角色id删除
     * @param roleId
     * @return
     */
    int deleteByRoleId(Integer roleId);
    
}
