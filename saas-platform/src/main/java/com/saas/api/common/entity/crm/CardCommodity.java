package com.saas.api.common.entity.crm;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 会员卡内的商品信息
 * @Author: 徐未
 * @Date: 2019/12/18
 */
@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class CardCommodity {
	
	private Long id;
	
	private Long cardId;
	
	private Long inventoryId;
	private Long commodityId;
    private String commodityCode;
    private String commodityName;
    
    private Integer supplierId;
    private String supplierCode;
    private String supplierName;
    
    private Integer unitId;
	private String unitName;
	private Integer settleMode;
	private String settleName;
    
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal discount;
    private BigDecimal amount;

    private String remark;    
    private Date createTime;
    private Date updateTime;

    
}