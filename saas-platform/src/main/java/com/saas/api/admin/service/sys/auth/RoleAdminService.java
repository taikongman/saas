package com.saas.api.admin.service.sys.auth;


import java.util.List;

import com.saas.api.common.entity.sys.auth.RoleAdmin;

public interface RoleAdminService {

    List<RoleAdmin> listByAdminIdIn(List<Integer> adminIds);

    List<RoleAdmin> listByRoleId(Integer roleId);
    
    List<RoleAdmin> findByRoleId(Integer roleId);
    
    RoleAdmin findByAdminId(Integer adminId);

    int insertData(RoleAdmin record);
    
    int updateData(RoleAdmin record);
    
    int updateDataByAdminId(RoleAdmin record);

    int deleteById(Integer Id);
    
    int deleteByAdminId(Integer adminId);

}
