package com.saas.api.common.dao.sys;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.saas.api.common.entity.sys.Config;

import java.util.List;

@Mapper
public interface ConfigDao {
    
    /**
         *  后台业务查询列表
     * @return 列表
     */
    List<Config> listData(@Param(value = "keyName") String keyName, @Param(value = "startIndex") Integer startIndex, @Param(value = "pageSize") Integer pageSize);
    
    /**
     * 
     * @param authAdminQueryRequest
     * @return
     */
    int countData(@Param(value = "keyName") String keyName);
    
    
    Config findByKeyName(@Param(value = "keyName") String keyName);

    /**
         *  根据id查询
     * @param id 传入的id
     * @return
     */
    Config findById(Integer id);

    
    /**
         *  插入
     * @param authAdmin
     * @return
     */
    int insertData(Config config);
    
    /**
         *  更新
     * @param authAdmin
     * @return
     */
    int updateData(Config config);
    
    /**
         * 删除
     * @param id
     * @return
     */
    int deleteById(Integer id);
    
}
