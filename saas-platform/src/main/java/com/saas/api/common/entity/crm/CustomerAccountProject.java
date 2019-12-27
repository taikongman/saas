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
public class CustomerAccountProject {
	
    private Long id;

    private Long accountId;
    
    private Long cardId;

    private Long projectId;
    private String projectCode;
	private String projectName;
	
	private Integer unitId;
	private String unitName;
	private Integer settleMode;
	private String settleName;

    private BigDecimal price;
    private Integer quantity;
    private BigDecimal discount;
    private BigDecimal amount;
    
    private BigDecimal used;
    private BigDecimal remain;


    private String remark;
    private Date createTime;
    private Date updateTime;

}