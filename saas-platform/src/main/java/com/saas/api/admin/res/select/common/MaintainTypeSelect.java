package com.saas.api.admin.res.select.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 计价方式
 * @Author: 徐未
 * @Date: 2019/12/17
 */
@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class MaintainTypeSelect {
	
	private Integer id;
	
	private String name;
	
	
}
