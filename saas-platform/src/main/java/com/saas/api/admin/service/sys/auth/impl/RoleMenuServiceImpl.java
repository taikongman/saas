package com.saas.api.admin.service.sys.auth.impl;

import org.springframework.stereotype.Service;

import com.saas.api.admin.service.sys.auth.RoleMenuService;
import com.saas.api.common.dao.sys.auth.RoleMenuDao;
import com.saas.api.common.entity.sys.auth.RoleMenu;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service
public class RoleMenuServiceImpl implements RoleMenuService {

    @Resource
    private RoleMenuDao roleMenuDao;

    /**
     * 根据 多个角色id 查询
     * @param roleIds
     * @return
     */
    @Override
    public List<RoleMenu> listByRoleIdIn(List<Integer> roleIds) {
        if (roleIds.isEmpty()) {
            return Collections.emptyList();
        }
        return roleMenuDao.listByRoleIdIn(roleIds);
    }

    /**
     * 根据某个角色id 查询
     * @param roleId
     * @return
     */
    @Override
    public List<RoleMenu> listByRoleId(Integer roleId) {
        return roleMenuDao.listByRoleId(roleId);
    }

    /**
     * 批量插入
     * @param authPermissionList
     * @return
     */
    @Override
    public int insertRoleMenuAll(List<RoleMenu> roleMenuList) {
        return roleMenuDao.insertMenuAll(roleMenuList);
    }

    /**
     * 根据角色id删除
     * @param roleId
     * @return
     */
    @Override
    public int deleteByRoleId(Integer roleId) {
        return roleMenuDao.deleteByRoleId(roleId);
    }
    
    /**
     * 根据角色id删除
     * @param roleId
     * @return
     */
    @Override
    public int deleteByMenuId(Integer menuId) {
        return roleMenuDao.deleteByMenuId(menuId);
    }
}
