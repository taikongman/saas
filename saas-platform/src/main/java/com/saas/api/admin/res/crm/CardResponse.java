package com.saas.api.admin.res.crm;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.saas.api.common.entity.crm.CardCommodity;
import com.saas.api.common.entity.crm.CardProject;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 会员卡定义
 * @Author: 徐未
 * @Date: 2019/12/18
 */
@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class CardResponse {
    private Long id;

    private Integer groupId;
	
	private Integer companyId;
	
	private Integer departmentId;
	
	private Long adminId;
	
    private Integer categoryId;

    private String cardName;
    
    // 项目折扣
    private Integer projectDiscount;
    // 项目优惠上限
    private Integer projectUpLimit;
    // 商品折扣
    private Integer commodityDiscount;
    // 商品优惠上限
    private Integer commodityUpLimit;
    // 低金额的告警阈值
    private Integer lowLimit;
    
    // 生效日期
    private Date effectiveDate;
    // 失效日期
    private Date expiryDate;

    // 市场价格-套餐合计费用
    private BigDecimal price;
    
    // 储值金额
    private BigDecimal discount;
    
    // 折扣金额
    private BigDecimal amount;
    
    // 销售金额
    private BigDecimal salePrice;
    
    // 权益
    private String rights;
    // 使用须知
    private String usages;
    
    private String remark;    
    //状态，1：正常，2：删除
    private Integer status;
    
    private Date createTime;
    private Date updateTime;
    // 语言类型 中文zh_cn 英文en_us等
    private String lanType;
    
    private List<CardCommodity> commodityList;
    
    private List<CardProject> projectList;
    
}