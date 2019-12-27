package com.saas.api.common.entity.common;

import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 商品计量单位
 * @Author: 徐未
 * @Date: 2019/12/06
 */
@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class Unit {
	
	private Integer id;
	
	private String unitName;
	
	private String remark;
	private Date createTime;
	private Date updateTime;
	private String lanType;
	 
}
