package com.saas.api.common.dao.crm;

import com.saas.api.admin.res.select.crm.ProjectSelect;
import com.saas.api.common.entity.crm.Project;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProjectDao {
    int deleteByPrimaryKey(Long id);

    int insert(Project record);

    int insertSelective(Project record);

    Project selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Project record);

    int updateByPrimaryKey(Project record);
    
    List<ProjectSelect> findSelectObject(Project record);

    List<Project> projectList(@Param(value = "groupId") Integer groupId,
    		@Param(value = "companyId") Integer companyId, @Param(value = "departmentId") Integer departmentId,
    		@Param(value = "adminId") Integer adminId, @Param(value = "lanType") String lanType, 
    		@Param(value = "projectName") String projectName, @Param(value = "projectCode") String projectCode, 
    		@Param(value = "projectTypeId") Integer projectTypeId, @Param(value = "status") Integer status, 
    		@Param(value = "startIndex") Integer startIndex, @Param(value = "pageSize") Integer pageSize);

    int countProject(@Param(value = "groupId") Integer groupId,
    		@Param(value = "companyId") Integer companyId, @Param(value = "departmentId") Integer departmentId,
    		@Param(value = "adminId") Integer adminId, @Param(value = "lanType") String lanType, 
    		@Param(value = "projectName") String projectName, @Param(value = "projectCode") String projectCode, 
    		@Param(value = "projectTypeId") Integer projectTypeId, @Param(value = "status") Integer status);

    int countByCode(@Param(value = "groupId") Integer groupId,
    		@Param(value = "companyId") Integer companyId, @Param(value = "departmentId") Integer departmentId,
    		@Param(value = "adminId") Integer adminId, @Param(value = "lanType") String lanType, 
    		@Param(value = "projectCode") String projectCode);

    Project selectByCode(@Param(value = "groupId") Integer groupId,
    		@Param(value = "companyId") Integer companyId, @Param(value = "departmentId") Integer departmentId,
    		@Param(value = "adminId") Integer adminId, @Param(value = "lanType") String lanType, 
    		@Param(value = "projectCode") String projectCode);

    List<Project> defaultProjectList(@Param(value = "groupId") Integer groupId,
    		@Param(value = "companyId") Integer companyId, @Param(value = "departmentId") Integer departmentId,
    		@Param(value = "adminId") Integer adminId, @Param(value = "lanType") String lanType, 
    		@Param(value = "projectTypeId") Integer projectTypeId);

    List<Project> projectList4OrCond(@Param(value = "groupId") Integer groupId,
    		@Param(value = "companyId") Integer companyId, @Param(value = "departmentId") Integer departmentId,
    		@Param(value = "adminId") Integer adminId, @Param(value = "lanType") String lanType, 
    		@Param(value = "searchCond") String searchCond, @Param(value = "projectTypeId") Integer projectTypeId, 
    		@Param(value = "startIndex") Integer startIndex, @Param(value = "pageSize") Integer pageSize);

    int countProject4OrCond(@Param(value = "groupId") Integer groupId,
    		@Param(value = "companyId") Integer companyId, @Param(value = "departmentId") Integer departmentId,
    		@Param(value = "adminId") Integer adminId, @Param(value = "lanType") String lanType, 
    		@Param(value = "searchCond") String searchCond, @Param(value = "projectTypeId") Integer projectTypeId);

}