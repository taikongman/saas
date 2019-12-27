package com.saas.api.common.entity.common;

import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 项目类型
 * @Author: 徐未
 * @Date: 2019/12/16
 */
@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class ProjectType {
	
	private Integer id;
	
	private String name;
	
	private String remark;
	private Date createTime;
	private Date updateTime;
	private String lanType;
	 
}
