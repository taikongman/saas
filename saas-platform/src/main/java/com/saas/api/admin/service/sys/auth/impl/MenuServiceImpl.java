package com.saas.api.admin.service.sys.auth.impl;

import com.saas.api.admin.service.sys.auth.MenuService;
import com.saas.api.common.dao.sys.auth.MenuDao;
import com.saas.api.common.dto.Page;
import com.saas.api.common.entity.sys.auth.Menu;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Service
@Slf4j
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuDao menuDao;

	@Override
	public Map<String, Object> getListData(Integer menuId, Integer pid, String lanType, Page page) {
		log.debug("MenuServiceImpl");
		Map<String, Object> result = new HashMap<>();
		List<Menu> resultList = menuDao.listData(menuId, pid, lanType, page.getStartIndex(), page.getPageSize());
		Integer total = menuDao.countData(menuId, pid, lanType);

		result.put("dataList", resultList);
        result.put("total", total);
        return result;
	}

	@Override
	public List<Menu> findByObject(Menu record) {
		return menuDao.findByObject(record);
	}

	@Override
	public List<Menu> listDataByIdIn(List<Integer> menuIdList) {
		if (menuIdList.isEmpty()) {
            return Collections.emptyList();
        }
		return menuDao.listDataByIdIn(menuIdList);
	}

	@Override
	public Menu findByPrimayKey(Integer menuId) {
		return menuDao.findById(menuId);
	}

	@Override
	public Menu findByName(String menuTitle) {
		return menuDao.findByName(menuTitle);
	}

	@Override
	public int insertData(Menu record) {
		record.setCreateTime(new Date());
		if (record.getListorder() == null) {
			record.setListorder(999);
		}
		return menuDao.insertData(record);
	}

	@Override
	public int updateData(Menu record) {
		record.setUpdateTime(new Date());
		return menuDao.updateData(record);
	}

	@Override
	public int deleteByPrimayKey(Integer menuId) {
		return menuDao.deleteById(menuId);
	}

    
}
