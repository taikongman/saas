package com.saas.api.common.entity.crm;

import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 商品供应商银行账号
 * @Author: 徐未
 * @Date: 2019/12/06
 */
@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class SupplierBank{
	private Integer id;
	
    private Integer supplierId;    
	private String accountName;
    private String taxNo;
    private String bankName;
    private String bankNo;
    
    private String contact;
    private String phone;
    
    private String provinceId;
    private String province;
    private String cityId;
    private String city;
    private String areaId;
    private String area;
    private String address;
    private String fullAddress;
    
    private String remark;    
    
    //状态，1：正常，2：删除
    private Integer status;
    
    private Date createTime;
    private Date updateTime;
    // 语言类型 中文zh_cn 英文en_us等
    private String lanType;

    
}