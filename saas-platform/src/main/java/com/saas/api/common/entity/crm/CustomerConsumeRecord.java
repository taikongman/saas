package com.saas.api.common.entity.crm;

import java.math.BigDecimal;
import java.util.Date;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 客户消费记录信息
 * @Author: 徐未
 * @Date: 2019/12/23
 */
@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class CustomerConsumeRecord {
    private Long id;

    private Long customerId;
    // 消费类型，1现金金额  2会员卡里储值金额  3会员卡内商品 4会员卡内项目 5积分
    private Integer consumeType;

    private Long accountId;
    private Long cardId;
    
    private Long cardContentId;
    
    private Long payId;
    
    private Long orderId;

    private BigDecimal amount;
    private BigDecimal integral;
    
    private String remark;    
    //状态，1：正常，2：删除
    private Integer status;
    
    private Date createTime;
    private Date updateTime;
    
    
    
    
}