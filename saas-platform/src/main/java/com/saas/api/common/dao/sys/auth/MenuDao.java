package com.saas.api.common.dao.sys.auth;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.saas.api.common.entity.sys.auth.Menu;

import java.util.List;

@Mapper
public interface MenuDao {

	/**
     * 后台业务查询列表
     * @return 列表
     */
    List<Menu> listData(@Param(value = "menuId") Integer menuId, @Param(value = "pid") Integer pid,
    		@Param(value = "lanType") String lanType, 
    		@Param(value = "startIndex") Integer startIndex, @Param(value = "pageSize") Integer pageSize);
    
    /**
     * 统计数据
     * @param 
     * @return
     */
    int countData(@Param(value = "menuId") Integer menuId, @Param(value = "pid") Integer pid,
    		@Param(value = "lanType") String lanType);
    
    
    /**
     * 根据对象模糊查询
     * @param record
     * @return
     */
    List<Menu> findByObject(Menu record);
    
    /**
     * 根据menuId list查询
     * @param record
     * @return
     */
    List<Menu> listDataByIdIn(List<Integer> menuIdList);
    
    /**
     * 根据id查询
     * @param id 传入的id
     * @return
     */
    Menu findById(Integer menuId);
    
    /**
     * 根据parent id查询
     * @param pid 传入的pid
     * @return
     */
    Menu findByPid(Integer pid);

    /**
     * 根据Name
     * @param menuTitle 名
     * @return
     */
    Menu findByName(String menuTitle);
    
    /**
     * 插入
     * @param Menu
     * @return
     */
    int insertData(Menu record);
    
    /**
     * 插入
     * @param Menu
     * @return
     */
    int insertSelective(Menu record);
    
    
    /**
     * 更新
     * @param Menu
     * @return
     */
    int updateData(Menu record);
    
    /**
     * 删除
     * @param id
     * @return
     */
    int deleteById(Integer menuId);
    
}
