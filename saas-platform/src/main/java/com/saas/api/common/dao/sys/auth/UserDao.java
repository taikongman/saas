package com.saas.api.common.dao.sys.auth;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.saas.api.common.entity.sys.auth.User;

import java.util.List;

@Mapper
public interface UserDao {
    
    /**
     * 后台业务查询列表
     * @return 列表
     */
    List<User> listData(@Param(value = "groupId") Integer groupId, @Param(value = "companyId") Integer companyId, 
    		@Param(value = "departmentId") Integer departmentId, @Param(value = "adminId") Integer adminId,
    		@Param(value = "lanType") String lanType, 
    		@Param(value = "startIndex") Integer startIndex, @Param(value = "pageSize") Integer pageSize);
    
    /**
     * 统计数据
     * @param 
     * @return
     */
    int countData(@Param(value = "groupId") Integer groupId, @Param(value = "companyId") Integer companyId, 
    		@Param(value = "departmentId") Integer departmentId, @Param(value = "adminId") Integer adminId,
    		@Param(value = "lanType") String lanType);
    
    
    /**
     * 根据对象模糊查询
     * @param record
     * @return
     */
    List<User> findByObject(User record);
    
    /**
     * 根据id查询
     * @param id 传入的id
     * @return
     */
    User findById(Long adminId);

    /**
     * 根据Name
     * @param userName 用户名
     * @return
     */
    User findByName(String userName);
    
    /**
     * 插入
     * @param UserSelect
     * @return
     */
    int insertData(User record);
    
    /**
     * 插入
     * @param UserSelect
     * @return
     */
    int insertSelective(User record);
    
    /**
     * 更新
     * @param UserSelect
     * @return
     */
    int resetPassword(User record);
    
    /**
     * 更新
     * @param UserSelect
     * @return
     */
    int updateData(User record);
    
    /**
     * 删除
     * @param id
     * @return
     */
    int deleteById(Long adminId);
    
}
