package com.saas.api.common.entity.crm;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 会员卡内的项目信息
 * @Author: 徐未
 * @Date: 2019/12/18
 */
@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class CardProject {
	
    private Long id;

    private Long cardId;

    private Long projectId;
    private String projectCode;
	private String projectName;

    private Integer projectTypeId;
	private String projectTypeName;
	private Integer categoryId;
	private String categoryName;
	
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