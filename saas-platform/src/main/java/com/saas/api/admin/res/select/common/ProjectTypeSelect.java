package com.saas.api.admin.res.select.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 项目类型
 * @Author: 徐未
 * @Date: 2019/12/17
 */
@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class ProjectTypeSelect {
	
	private Integer id;
	
	private String name;
	
	
}
