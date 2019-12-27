package com.saas.api.admin.res.select.common;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 汽车型号
 * @Author: 徐未
 * @Date: 2019/11/29
 */
@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class AutoModelSelect {
	
	 private Integer modelId;
	 private String modelName;

}
