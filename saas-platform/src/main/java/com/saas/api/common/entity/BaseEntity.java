package com.saas.api.common.entity;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 实体基础类
 * @Author: 徐未
 * @Date: 2019/11/26
 */
@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class BaseEntity {
	
	private Integer groupId;
	
	private Integer companyId;
	
	private Integer departmentId;
	
	private Integer adminId;
	
	private String lanType;

}
