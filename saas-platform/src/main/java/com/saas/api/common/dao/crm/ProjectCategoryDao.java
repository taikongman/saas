package com.saas.api.common.dao.crm;

import com.saas.api.admin.res.select.crm.ProjectCategorySelect;
import com.saas.api.common.entity.crm.ProjectCategory;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProjectCategoryDao {
	
    int deleteByPrimaryKey(Integer categoryId);

    int insert(ProjectCategory record);

    int insertSelective(ProjectCategory record);

    ProjectCategory selectByPrimaryKey(Integer categoryId);
    
    ProjectCategory selectByName(@Param(value = "groupId") Integer groupId,
    		@Param(value = "companyId") Integer companyId,
    		@Param(value = "categoryName") String categoryName, 
    		@Param(value = "lanType") String lanType);

    int updateByPrimaryKeySelective(ProjectCategory record);

    int updateByPrimaryKey(ProjectCategory record);

    List<ProjectCategory> listData(@Param(value = "groupId") Integer groupId,
    		@Param(value = "companyId") Integer companyId, @Param(value = "departmentId") Integer departmentId,
    		@Param(value = "adminId") Integer adminId, 
    		@Param(value = "categoryId") Integer categoryId,
    		@Param(value = "lanType") String lanType, 
    		@Param(value = "startIndex") Integer startIndex, @Param(value = "pageSize") Integer pageSize);

    int countData(@Param(value = "groupId") Integer groupId,
    		@Param(value = "companyId") Integer companyId, @Param(value = "departmentId") Integer departmentId,
    		@Param(value = "adminId") Integer adminId, 
    		@Param(value = "categoryId") Integer categoryId,
    		@Param(value = "lanType") String lanType);

    
    List<ProjectCategory> findByObject(ProjectCategory record);
   
    List<ProjectCategorySelect> findSelectObject(ProjectCategory record);

}