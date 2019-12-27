package com.saas.api.common.entity.crm;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 客户购买的会员卡内的项目信息
 * @Author: 徐未
 * @Date: 2019/12/23
 */
@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class CustomerRechargeRecord {
	
    private Long id;
    
    private Long customerId;
    
    // 充值方式，1：现金,2:支付宝
    private Integer rechargeMode;
    // 账户类型，1：储值，2：卡
    private Integer accountType;

    private Long accountId;

    private Long cardId;

    private BigDecimal amount;

    private BigDecimal gift;

    private BigDecimal total;

    private BigDecimal accountBalance;

    private Long operatorId;

    private String operatorName;
    
    private Date expiryTime;

    private String remark;
    
    private Integer status;

    private Date createTime;

    private Date updateTime;


}