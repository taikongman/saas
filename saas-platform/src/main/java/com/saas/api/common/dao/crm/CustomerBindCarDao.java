package com.saas.api.common.dao.crm;

import com.saas.api.common.entity.crm.CustomerBindCar;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CustomerBindCarDao {
    int deleteByPrimaryKey(Long id);
    
    int deleteByCustomerId(Long customerId);

    int insert(CustomerBindCar record);

    int insertSelective(CustomerBindCar record);

    CustomerBindCar selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerBindCar record);

    int updateByPrimaryKey(CustomerBindCar record);

    CustomerBindCar selectByCustomerIdAndCarId(@Param(value = "customerId") Long customerId, @Param(value = "carId") Long carId);

    List<CustomerBindCar> selectByCustomerId(@Param(value = "customerId") Long customerId);

    List<CustomerBindCar> selectByCarId(@Param(value = "carId") Long carId);
}