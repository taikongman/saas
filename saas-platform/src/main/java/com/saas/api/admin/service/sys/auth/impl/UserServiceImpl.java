package com.saas.api.admin.service.sys.auth.impl;

import com.saas.api.admin.res.sys.auth.UserResponse;
import com.saas.api.admin.service.sys.auth.UserService;
import com.saas.api.common.dao.sys.auth.RoleAdminDao;
import com.saas.api.common.dao.sys.auth.UserDao;
import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.sys.auth.RoleAdmin;
import com.saas.api.common.entity.sys.auth.User;
import com.saas.api.common.util.PublicFileUtils;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;
    
    @Resource
    private RoleAdminDao roleAdminDao;

    @Override
    public Map<String, Object> getListData(Integer groupId, Integer companyId, Integer departmentId, Integer adminId, String lanType, Page page) {
    	Map<String, Object> result = new HashMap<>();
    	if (groupId == null && companyId == null  && departmentId == null && adminId == null && lanType == null) {
    		return result;
        }
    	
    	List<User> serviceList = userDao.listData(groupId, companyId, departmentId, adminId, lanType, page.getStartIndex(), page.getPageSize());
    	for(User forTemp : serviceList) {
			forTemp.setAvatarUrl(PublicFileUtils.createUploadUrl(forTemp.getAvatar()));
		}
    	Integer total = userDao.countData(groupId, companyId, departmentId, adminId, lanType);
    	
    	result.put("list", serviceList);
        result.put("total", total);
        return result;
    }
    
    @Override
	public Map<String, Object> queryListData(Integer groupId, Integer companyId, Integer departmentId, Integer adminId,
			String lanType, Page page) {
    	Map<String, Object> result = new HashMap<>();
    	if (groupId == null && companyId == null  && departmentId == null && adminId == null && lanType == null) {
    		return result;
        }
    	
    	List<User> serviceList = userDao.listData(groupId, companyId, departmentId, adminId, lanType, page.getStartIndex(), page.getPageSize());
    	List<UserResponse> userResponseList = new ArrayList<UserResponse>();
    	for(User forTemp : serviceList) {
			forTemp.setAvatarUrl(PublicFileUtils.createUploadUrl(forTemp.getAvatar()));
			UserResponse userResponse = new UserResponse();
            BeanUtils.copyProperties(forTemp, userResponse);
            RoleAdmin roleAdmin = roleAdminDao.findByAdminId(forTemp.getAdminId().intValue());
            if(roleAdmin != null) {
            	userResponse.setRoleId(roleAdmin.getRoleId());
            }
			userResponseList.add(userResponse);
		}
    	Integer total = userDao.countData(groupId, companyId, departmentId, adminId, lanType);
    	
    	result.put("list", userResponseList);
        result.put("total", total);
        return result;
	}
    
	@Override
    public List<User> findByObject(User record){
    	List<User> resultList = userDao.findByObject(record);
		return resultList;
    }
    
    @Override
    public User findByPrimayKey(Long adminId) {
    	return userDao.findById(adminId);
    }
    
	@Override
	public User findByName(String userName) {
		return userDao.findByName(userName);
	}

    /**
     * 新增
     * @param authAdmin
     * @return
     */
    @Override
    public int insertData(User record) {
    	record.setCreateTime(new Date());
    	userDao.insertData(record);
        return record.getAdminId().intValue();
    }

    @Override
	public int resetPassword(User record) {
    	record.setUpdateTime(new Date());
        return userDao.resetPassword(record);
	}

	/**
     * 更新
     * @param UserSelect
     * @return
     */
    @Override
    public int updateData(User record) {
    	record.setUpdateTime(new Date());
        return userDao.updateData(record);
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @Override
    public int deleteByPrimayKey(Long adminId) {
        return userDao.deleteById(adminId);
    }


}
