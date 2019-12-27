package com.saas.api.admin.res.crm;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.saas.api.common.entity.crm.CustomerAccountCommodity;
import com.saas.api.common.entity.crm.CustomerAccountProject;

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
public class CustomerAccountResponse {
	
    private Long id;
    
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
    
    private List<CustomerAccountCommodity> commodityList;
    
    private List<CustomerAccountProject> projectList;

}