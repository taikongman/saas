package com.saas.api.common.entity.crm;

import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 会员卡分类信息
 * @Author: 徐未
 * @Date: 2019/12/18
 */
@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class CardCategory{
    private Integer categoryId;
    
    private Integer groupId;
	private Integer companyId;
	private Integer departmentId;
	private Long adminId;
    
	//卡片类型，1: 会员卡 2：套餐卡
	private Integer cardType;
	
    private String categoryName;
    // 商品上级分类cateid 顶级分类为0
    private Integer parentId;
    // 商品分类等级 顶级分类为1
    private Integer categoryLevel;
    
    private String remark;
    
    //状态，1：正常，2：删除
    private Integer status;
    
    private Date createTime;
    private Date updateTime;
    // 语言类型 中文zh_cn 英文en_us等
    private String lanType;

    
}