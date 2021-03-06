package com.saas.api.common.entity.crm;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 库存记录
 * @Author: 徐未
 * @Date: 2019/12/06
 */
@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class InventoryRecord {
	
	private Long id;
    
    private Integer groupId;
	private Integer companyId;
	private Integer departmentId;
	private Long adminId;
	
	private Long purchaseId;
	private String purchaseCode;
	
	private Long commodityId;
    private String commodityCode;
    private String commodityName;
    private String specification;
    
    private Integer supplierId;
    private String supplierCode;
    private String supplierName;
    
    private String barCode;
    
    private BigDecimal costPrice;
    private Integer taxRatio;
    private BigDecimal taxAmount;
    private BigDecimal taxPrice;
    private Integer costHasTax;
    private Integer adjustRatio;
    private BigDecimal salePrice;
    private Integer quantity;
    private BigDecimal amount; 
    
    private Integer remainQuantity;
    
    private Integer upQuantity;
    private Integer downQuantity;
    
    private Integer unitId;
    private String unitName;
    
    private Long operatorId;
    private String operatorName;
    
    private Integer warehouseId;
    private String warehouseName;
    
    private Long locationId;
    private String locationName;
    
    private Integer sort;

    private String remark;
    
    //状态，1：正常，2：删除
    private Integer status;
    
    private Date createTime;
    private Date updateTime;
    // 语言类型 中文zh_cn 英文en_us等
    private String lanType;
    
    private Integer version;
    
    
}