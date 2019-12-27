package com.saas.api.admin.res.crm;

import java.util.Date;
import java.util.List;

import com.saas.api.common.entity.crm.Car;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 实体基础类
 * @Author: 徐未
 * @Date: 2019/11/26
 */
@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class CustomerResponse {
    private Long id;
    
    private Integer groupId;
	
	private Integer companyId;
	
	private Integer departmentId;
	
	private Integer adminId;
	
	private String lanType;

    private String name;

    private Integer sex;

    private String phone;

    private String tel;

    private String wechat;

    private String birthday;

    private String provinceId;
    private String province;
    private String cityId;
    private String city;
    private String areaId;
    private String area;
    private String address;
    private String fullAddress;

    private Date licenceVerifyAt;

    private Integer status;

    private String remark;

    private Date createAt;

    private Date modifyAt;

    private Integer version;

    private List<Car> carList;
    
}