package com.saas.api.common.entity.crm;

import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 客户绑定汽车信息
 * @Author: 徐未
 * @Date: 2019/12/23
 */
@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class CustomerBindCar {
    private Long id;

    private Long customerId;

    private Long carId;

    private Date createAt;

    private Date modifyAt;

    private Integer version;
    
    private String lanType;    
    
}