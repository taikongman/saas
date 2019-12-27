package com.saas.api.admin.service.crm.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.saas.api.admin.res.select.crm.ProjectCategorySelect;
import com.saas.api.admin.service.crm.ProjectCategoryService;
import com.saas.api.common.constant.CommonConstant;
import com.saas.api.common.constant.DbConstant;
import com.saas.api.common.dao.crm.ProjectCategoryDao;
import com.saas.api.common.dao.sys.auth.UserDao;
import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.crm.ProjectCategory;
import com.saas.api.common.entity.sys.auth.User;

@Service
public class ProjectCategoryServiceImpl implements ProjectCategoryService {

	@Resource
    private ProjectCategoryDao projectCategoryDao;
    
	@Resource
    private UserDao userDao;
    
	@Override
	public ProjectCategory findByPrimayKey(Integer categoryId) {		
		return projectCategoryDao.selectByPrimaryKey(categoryId);
	}

	@Override
	public ProjectCategory findByName(Long adminId, String categoryName, String lanType) {
		User user = userDao.findById(adminId);
		return projectCategoryDao.selectByName(user.getGroupId(), user.getCompanyId(), categoryName, lanType);
	}

	@Override
	public int insertData(ProjectCategory record) {
		record.setStatus(DbConstant.STATUS_ONE);
		record.setCreateTime(new Date());
		return projectCategoryDao.insert(record);
	}

	@Override
	public int updateData(ProjectCategory record) {
		record.setUpdateTime(new Date());
		return projectCategoryDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int deleteByPrimayKey(Integer categoryId) {
		return projectCategoryDao.deleteByPrimaryKey(categoryId);
	}

	@Override
	public Map<String, Object> getListData(Integer categoryId, Long adminId, String lanType, Page page) {
		User user = userDao.findById(adminId);
		List<ProjectCategory> resultList = projectCategoryDao.listData(user.getGroupId(), user.getCompanyId(), 
				null, null, 
				categoryId, lanType, page.getStartIndex(), page.getPageSize());
		Integer total = projectCategoryDao.countData(user.getGroupId(), user.getCompanyId(), 
				null, null, 
				categoryId, lanType);
		
		Map<String, Object> result = new HashMap<>();
        result.put("dataList", resultList);
        result.put("total", total);
        return result;
	}

	@Override
	public List<ProjectCategory> findByObject(ProjectCategory record) {
		return projectCategoryDao.findByObject(record);
	}

	@Override
	public List<ProjectCategorySelect> findSelectObject(ProjectCategory record) {
		User user = userDao.findById(record.getAdminId());
		record.setGroupId(user.getGroupId());
		record.setCompanyId(user.getCompanyId());
		record.setParentId(CommonConstant.INDEX_ZERO);
		List<ProjectCategorySelect> resultList = projectCategoryDao.findSelectObject(record);
		for(ProjectCategorySelect forTemp : resultList) {
			ProjectCategory queryFist = new ProjectCategory();
			queryFist.setParentId(forTemp.getCategoryId());
			
			List<ProjectCategorySelect> childList = projectCategoryDao.findSelectObject(queryFist);
			for(ProjectCategorySelect forTwoTemp : childList) {
				ProjectCategory queryTwoFist = new ProjectCategory();
				queryTwoFist.setParentId(forTwoTemp.getCategoryId());
				forTwoTemp.setChildList(projectCategoryDao.findSelectObject(queryTwoFist));
			}
			
			forTemp.setChildList(childList);
		}
		
		return resultList;
	}

}
