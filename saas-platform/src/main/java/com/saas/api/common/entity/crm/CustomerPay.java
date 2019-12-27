package com.saas.api.common.entity.crm;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 客户付款信息
 * @Author: 徐未
 * @Date: 2019/12/23
 */
@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class CustomerPay {
    private Long id;
    private Long customerId;
    private String customerName;
    private String customerPhone;
    private Long orderId;
    private Integer projectTypeId;
    private Integer cashMode;
    private Long carId;
    private String carNo;
    

    private BigDecimal needPay;
    private BigDecimal accountPay;
    private BigDecimal discount;
    private BigDecimal realPay;
    
    private Integer alreadyInvoice;
    private Integer invoiceId;
    private String invoiceNo;

    private String remark;    
    //状态，1：正常，2：删除
    private Integer status;
    
    private Date createTime;
    private Date updateTime;
    
    private Integer version;
}