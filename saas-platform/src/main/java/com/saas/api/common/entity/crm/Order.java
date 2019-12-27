package com.saas.api.common.entity.crm;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 订单信息
 * @Author: 徐未
 * @Date: 2019/11/26
 */
@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class Order {
	
    private Long id;
    
    private Integer groupId;
	
	private Integer companyId;
	
	private Integer departmentId;
	
	private Long adminId;

    private Integer projectTypeId;
    
    private String projectTypeName;

    private Integer maintainTypeId;
    
    private String maintainTypeName;

    private String orderNo;

    private Long carId;

    private String carNo;

    private Long customerId;

    private String customerName;

    private String customerPhone;

    private String projectIds;

    private String commodityIds;

    private Integer mileage;

    private Long memberId;

    private String memberName;

    private Date estimateFinishTime;

    private Date realFinishTime;

    private Long staffId;

    private String staffName;

    private BigDecimal projectAmount;

    private BigDecimal materialAmount;

    private BigDecimal totalAmount;

    private BigDecimal realAmount;

    private BigDecimal discount;

    private BigDecimal suspend;

    private Integer status;

    private Long payId;

    private String carDesc;

    private String orderRemark;

    private Long settleMemberId;

    private String settleMemberName;

    private String settleRemark;

    private Integer alreadyInvoice;

    private Integer invoiceId;
    
    private String invoiceName;

    private String invoiceNo;

    private Date createTime;

    private Date settleTime;

    private Date updateTime;
    
    private String lanType;

    private Integer version;
    
}