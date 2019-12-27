package com.saas.api.common.entity.crm;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 客户账户信息
 * @Author: 徐未
 * @Date: 2019/12/23
 */
@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class CustomerAccount {
	
    private Long id;
    
    private Integer groupId;
	
	private Integer companyId;
	
	private Integer departmentId;
	
	private Long adminId;
    
    private Long customerId;

    // 账户类型，1：储值，2：卡
    private Integer accountType;

    private Long cardId;

    private BigDecimal used;
    private BigDecimal remain;
    private BigDecimal amount;
    private BigDecimal integral;

    private Date effectiveTime;

    private Date expiryTime;

    private String remark;    
    //状态，1：正常，2：删除
    private Integer status;
    
    private Date createTime;
    private Date updateTime;
    
    private Integer version;
    

}