package com.saas.api.admin.service.sys.auth.impl;

import com.saas.api.admin.service.sys.auth.RoleAdminService;
import com.saas.api.common.constant.CacheConstant;
import com.saas.api.common.dao.sys.auth.RoleAdminDao;
import com.saas.api.common.entity.sys.auth.RoleAdmin;
import com.saas.api.common.util.CacheUtils;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service
public class RoleAdminServiceImpl implements RoleAdminService {

    @Resource
    private RoleAdminDao roleAdminDao;

    /**
     * 根据多个 adminId 查询角色列表
     * @param adminIds
     * @return
     */
    @Override
    public List<RoleAdmin> listByAdminIdIn(List<Integer> adminIds) {
        if (adminIds.isEmpty()) {
            return Collections.emptyList();
        }
        return roleAdminDao.listByAdminIdIn(adminIds);
    }

    /**
     * 根据 roleId 获取 管理员id
     * @param roleId
     * @return
     */
    @Override
    public List<RoleAdmin> listByRoleId(Integer roleId) {
        return roleAdminDao.listByRoleId(roleId);
    }
    
    /**
     * 根据 roleId 获取 管理员id
     * @param roleId
     * @return
     */
    @Override
    public List<RoleAdmin> findByRoleId(Integer roleId) {
        return roleAdminDao.findByRoleId(roleId);
    }
    
    /**
     * 根据 adminId 获取 管理员
     * @param adminId
     * @return
     */
    @Override
    public RoleAdmin findByAdminId(Integer adminId) {
    	return roleAdminDao.findByAdminId(adminId);
    }

    @Override
    public int insertData(RoleAdmin record) {
    	return roleAdminDao.insertData(record);
    }
    
    @Override
    public int updateData(RoleAdmin record) {
    	return roleAdminDao.updateData(record);
    }
    
    @Override
    public int updateDataByAdminId(RoleAdmin record) {
    	return roleAdminDao.updateDataByAdminId(record);
    }

    @Override
    public int deleteById(Integer id) {
    	return roleAdminDao.deleteById(id);
    }
    
    /**
     * 根据 adminId 删除对应的权限
     * @param adminId
     * @return
     */
    @Override
    public int deleteByAdminId(Integer adminId) {
    	// 删除之前缓存权限规则
        String aarKey = String.format(CacheConstant.ADMIN_AUTH_RULES, adminId);
        CacheUtils.delete(aarKey);
    	return roleAdminDao.deleteByAdminId(adminId);
    }

}
