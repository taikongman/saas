package com.saas.api.common.dao.sys;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.saas.api.common.entity.sys.Softwareversion;

import java.util.List;

@Mapper
public interface SoftwareversionDao {
    
    /**
         *  后台业务查询列表
     * @return 列表
     */
    List<Softwareversion> listData(@Param(value = "appname") String appname, @Param(value = "osname") String osname, @Param(value = "version") String version, @Param(value = "startIndex") Integer startIndex, @Param(value = "pageSize") Integer pageSize);
    
    /**
     * 
     * @param 
     * @return
     */
    int countData(@Param(value = "appname") String appname, @Param(value = "osname") String osname, @Param(value = "version") String version);
    
    /**
         *  根据id查询
     * @param id 传入的id
     * @return
     */
    Softwareversion findById(Integer id);
    
    Softwareversion findCheckVersion(@Param(value = "appname") String appname, @Param(value = "osname") String osname,  @Param(value = "version") String version);

    
    /**
         *  插入
     * @param authAdmin
     * @return
     */
    int insertData(Softwareversion softwareversion);
    
    /**
         *  更新
     * @param authAdmin
     * @return
     */
    int updateData(Softwareversion softwareversion);
    
    /**
         * 删除
     * @param id
     * @return
     */
    int deleteById(Integer id);
    
}
