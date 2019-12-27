package com.saas.api.admin.res.crm;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.saas.api.common.entity.crm.CommodityAuto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 商品定义
 * @Author: 徐未
 * @Date: 2019/12/16
 */
@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class CommodityResponse {
private Long commodityId;
    
    private Integer groupId;
	private Integer companyId;
	private Integer departmentId;
	private Long adminId;
	
	private Integer categoryId;

    private String commodityCode;
    private String commodityName;
    private String pinyinCode;
    
    private BigDecimal costPrice;
    private Integer taxRatio;
    private BigDecimal taxAmount;
    private BigDecimal taxPrice;
    private Integer costHasTax;
    private Integer adjustRatio;
    private BigDecimal salePrice;
    private Integer quantity;
    private BigDecimal amount; 
    
    private String provinceId;
    private String province;
    private String cityId;
    private String city;
    private String areaId;
    private String area;
    private String address;
    private String fullAddress;

    private Integer unitId;
    private String unitName;

    private Integer sort;

    private String remark;
    
    //状态，1：正常，2：删除
    private Integer status;
    
    private Date createTime;
    private Date updateTime;
    // 语言类型 中文zh_cn 英文en_us等
    private String lanType;
    
    private Integer version;
    
    private List<CommodityAuto> commodityAutoList;
    
    

    
}