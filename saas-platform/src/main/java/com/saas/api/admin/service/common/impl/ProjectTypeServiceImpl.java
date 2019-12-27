package com.saas.api.admin.service.common.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.saas.api.admin.res.select.common.ProjectTypeSelect;
import com.saas.api.admin.service.common.ProjectTypeService;
import com.saas.api.common.dao.common.ProjectTypeDao;
import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.common.ProjectType;

@Service
public class ProjectTypeServiceImpl implements ProjectTypeService {

	@Resource
    private ProjectTypeDao projectTypeDao;
    
	@Override
	public ProjectType findByPrimayKey(Integer id) {
		return projectTypeDao.findById(id);
	}

	@Override
	public ProjectType findByName(String name) {
		return projectTypeDao.findByName(name);
	}

	@Override
	public int insertData(ProjectType record) {
		record.setCreateTime(new Date());
		return projectTypeDao.insertSelective(record);
	}

	@Override
	public int updateData(ProjectType record) {
		record.setUpdateTime(new Date());
		return projectTypeDao.updateData(record);
	}

	@Override
	public int insertDataByParams(String name, String remark, String lanType) {
		ProjectType record = new ProjectType();
		record.setName(name);
		record.setRemark(remark);
		record.setLanType(lanType);
		record.setCreateTime(new Date());
		
		return projectTypeDao.insertSelective(record);
	}

	@Override
	public int updateDataByParams(Integer id, String name, String remark, String lanType) {
		ProjectType record = new ProjectType();
		record.setId(id);
		record.setName(name);
		record.setRemark(remark);
		record.setLanType(lanType);
		record.setUpdateTime(new Date());
		
		return projectTypeDao.updateData(record);
	}

	@Override
	public int deleteByPrimayKey(Integer id) {
		return projectTypeDao.deleteById(id);
	}

	@Override
	public Map<String, Object> getListData(Integer id, String lanType, Page page) {
		List<ProjectType> resultList = projectTypeDao.listData(id, lanType, page.getStartIndex(), page.getPageSize());
		Integer total = projectTypeDao.countData(id, lanType);

		Map<String, Object> result = new HashMap<>();
		result.put("dataList", resultList);
        result.put("total", total);
        return result;
	}

	@Override
	public List<ProjectType> findByObject(ProjectType record) {
		return projectTypeDao.findByObject(record);
	}

	@Override
	public List<ProjectTypeSelect> findSelectObject(String lanType) {
		ProjectType record = new ProjectType();
		record.setLanType(lanType);
		return projectTypeDao.findSelectObject(record);
	}

}
