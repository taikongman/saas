package com.saas.api.common.entity.common;

import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 发票方式
 * @Author: 九尾
 * @Date: 2019/12/06
 */
@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class Invoice {
	
	private Integer id;
	
	private String invoiceName;
	
	private String remark;
	private Date createTime;
	private Date updateTime;
	private String lanType;
	 
}
