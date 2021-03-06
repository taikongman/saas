package com.saas.api.admin.res.select.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 维修类别
 * @Author: 徐未
 * @Date: 2019/12/09
 */
@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class PricingModeSelect {
	
	private Integer id;
	
	private Integer isTime;
	
	private String name;
	
	
}
