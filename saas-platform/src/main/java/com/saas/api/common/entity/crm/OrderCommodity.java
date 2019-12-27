package com.saas.api.common.entity.crm;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 商品销售领料退料记录
 * @Author: 徐未
 * @Date: 2019/12/06
 */
@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class OrderCommodity {
	
	private Long id;
	
    private Integer groupId;
	private Integer companyId;
	private Integer departmentId;
	private Long adminId;
	
	private Long inventoryId;
	
	private Long commodityId;
    private String commodityCode;
    private String commodityName;
    
    private Integer supplierId;
    private String supplierCode;
    private String supplierName;
    
    // 类型，1入库 2出库领料销售 3退料退库
    private Integer operateType;
    private String barCode;
    
    private BigDecimal costPrice;
    private BigDecimal salePrice;
    private Integer quantity;
	private BigDecimal discount;
    private BigDecimal amount;
    
    private Integer settleMode;
    private String settleName;
    
    private Integer unitId;
    private String unitName;
    
    private Integer invoiceId;
    private String invoiceName;
    
    private Long operatorId;
    private String operatorName;
    
    private String carNo;
    
    private Long orderId;

    private String remark;
    
    //状态，1：正常，2：删除
    private Integer status;
    
    private Date createTime;
    private Date updateTime;
    // 语言类型 中文zh_cn 英文en_us等
    private String lanType;
    
    private Integer version;

    
}