package com.saas.api.common.dao.crm;

import com.saas.api.common.entity.crm.Car;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CarDao {
    int deleteByPrimaryKey(Long id);

    int insert(Car record);

    int insertSelective(Car record);

    Car selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Car record);

    int updateByPrimaryKey(Car record);

    Car selectByCarNo(@Param(value = "carNo") String carNo, @Param(value = "groupId") Integer groupId,
    		@Param(value = "companyId") Integer companyId, @Param(value = "departmentId") Integer departmentId,
    		@Param(value = "adminId") Integer adminId, @Param(value = "lanType") String lanType);

    int countByCarNo(@Param(value = "carNo") String carNo, @Param(value = "groupId") Integer groupId,
    		@Param(value = "companyId") Integer companyId, @Param(value = "departmentId") Integer departmentId,
    		@Param(value = "adminId") Integer adminId, @Param(value = "lanType") String lanType);

    Car selectByCarId(Long carId);
    
    List<Car> selectByCarIdList(List<Long> carIdList);
}