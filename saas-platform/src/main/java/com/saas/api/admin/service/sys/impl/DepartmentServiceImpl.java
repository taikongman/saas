package com.saas.api.admin.service.sys.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.saas.api.admin.service.sys.DepartmentService;
import com.saas.api.common.dao.sys.DepartmentDao;
import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.sys.Department;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {

	@Resource
    private DepartmentDao departmentDao;
	
	@Override
	public Map<String, Object> getListData(Integer groupId, Integer companyId, Integer departmentId, String lanType, Page page) {
		log.debug("DepartmentServiceImpl");
		Map<String, Object> result = new HashMap<>();
		List<Department> departmentList = departmentDao.listData(groupId, companyId, departmentId, lanType, page.getStartIndex(), page.getPageSize());
		Integer total = departmentDao.countData(groupId, companyId, departmentId, lanType);

		result.put("list", departmentList);
        result.put("total", total);
        return result;
	}

	@Override
	public Department findByPrimayKey(Integer departmentId) {
		Department department = departmentDao.findById(departmentId);
		return department;
	}

	@Override
	public Department findByName(String departmentName) {
		Department department = departmentDao.findByName(departmentName);
		return department;
	}

	@Override
	public int insertData(Department record) {
		record.setCreateTime(new Date());
		departmentDao.insertData(record);
		return record.getDepartmentId();
	}

	@Override
	public int updateData(Department record) {
		record.setUpdateTime(new Date());
		int result = departmentDao.updateData(record);
		return result;
	}

	@Override
	public int deleteByPrimayKey(Integer departmentId) {
		int result = departmentDao.deleteById(departmentId);
		return result;
	}

	@Override
	public List<Department> findByObject(Department record) {
		List<Department> resultList = departmentDao.findByObject(record);
		return resultList;
	}

	
}
