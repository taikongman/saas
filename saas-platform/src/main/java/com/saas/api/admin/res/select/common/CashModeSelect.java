package com.saas.api.admin.res.select.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 收银方式
 * @Author: 徐未
 * @Date: 2019/12/09
 */
@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class CashModeSelect {
	
	private Integer id;
	
	private String name;
	
	
}
