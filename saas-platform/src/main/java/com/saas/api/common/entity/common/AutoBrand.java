package com.saas.api.common.entity.common;

import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 汽车品牌
 * @Author: 徐未
 * @Date: 2019/11/29
 */
@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class AutoBrand {
	
	private Integer id;
	
	private String firstName;
	
	private Integer brandId;
	
	private String brandName;
	
	private String brandLogo;
	
	private Date createTime;
	
	private Date updateTime;
	
	private String lanType;
	
}
