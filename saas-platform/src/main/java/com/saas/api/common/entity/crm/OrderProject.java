package com.saas.api.common.entity.crm;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 订单项目
 * @Author: 徐未
 * @Date: 2019/12/16
 */
@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class OrderProject {
	
    private Long id;

    private Long orderId;

    private Long projectId;

    private Integer projectTypeId;
	private String projectTypeName;
	private Integer categoryId;
	private String categoryName;
	
	private String projectCode;
	private String projectName;
	
	private Integer unitId;
	private String unitName;
	
	private Integer priceMode;
	private String priceName;

    private BigDecimal price;
    
    private BigDecimal workHour;

    private BigDecimal quantity;
    
    private BigDecimal discount;

    private BigDecimal amount;
    

    private Integer settleMode;
    
    private String settleName;

    private Long staffId;

    private String staffName;

    private String remark;

    private Date createTime;

    private Date updateTime;

    private Integer version;

}