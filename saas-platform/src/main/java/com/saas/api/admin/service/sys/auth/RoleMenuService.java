package com.saas.api.admin.service.sys.auth;


import java.util.List;

import com.saas.api.common.entity.sys.auth.RoleMenu;

public interface RoleMenuService {

    List<RoleMenu> listByRoleIdIn(List<Integer> roleIds);

    List<RoleMenu> listByRoleId(Integer roleId);

    int insertRoleMenuAll(List<RoleMenu> roleMenuList);

    int deleteByRoleId(Integer roleId);
    
    int deleteByMenuId(Integer menuId);
}
