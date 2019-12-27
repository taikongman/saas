package com.saas.api.common.entity.crm;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 项目定义
 * @Author: 徐未
 * @Date: 2019/12/06
 */
@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class Project {
    private Long id;
    
    private Integer groupId;
	
	private Integer companyId;
	
	private Integer departmentId;
	
	private Long adminId;

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
	
	private BigDecimal workHour;
	
	private BigDecimal price;
	
	private BigDecimal amount;

    private Integer sort;

    private String remark;
    
    //状态，1：正常，2：删除
    private Integer status;
    
    private Date createTime;
    private Date updateTime;
    // 语言类型 中文zh_cn 英文en_us等
    private String lanType;

    private Integer version;
    
    
}