package com.saas.api.admin.service.sys.auth.impl;

import com.saas.api.admin.service.sys.auth.RoleService;
import com.saas.api.common.dao.sys.auth.RoleDao;
import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.sys.auth.Role;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleDao roleDao;

    /**
     * 查询列表
     * @return
     */
    
    @Override
    public Map<String, Object> getListData(Integer groupId, Integer companyId, Long roleId, String lanType, Page page) {
    	log.debug("RoleServiceImpl");
		Map<String, Object> result = new HashMap<>();
		
		List<Role> resultList = roleDao.listData(groupId, companyId, roleId, lanType, page.getStartIndex(), page.getPageSize());
		Integer total = roleDao.countData(groupId, companyId, roleId, lanType);

		result.put("dataList", resultList);
        result.put("total", total);
        
        return result;
    }

    /**
     * 查询管理员页面的列表
     * @param page
     * @param limit
     * @param status
     * @return
     */
    @Override
    public List<Role> findByObject(Role record) {
        List<Role> resultList = roleDao.findByObject(record);
        return resultList;
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    public Role findByPrimayKey(Long roleId) {
    	return roleDao.findById(roleId);
    }
    
    /**
     * 根据名称查询
     * @param name
     * @return
     */
    @Override
    public Role findByName(String roleName) {
        return roleDao.findByName(roleName);
    }

    /**
     * 插入
     * @param authRole
     * @return
     */
    @Override
    public int insertData(Role record) {
    	record.setCreateTime(new Date());

        return roleDao.insertData(record);
    }

    /**
     * 修改
     * @param authRole
     * @return
     */
    @Override
    public int updateData(Role record) {
    	record.setUpdateTime(new Date());
        return roleDao.updateData(record);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @Override
    public int deleteByPrimayKey(Long roleId) {
        return roleDao.deleteById(roleId);
    }
}
