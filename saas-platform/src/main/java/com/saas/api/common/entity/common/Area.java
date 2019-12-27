package com.saas.api.common.entity.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 县区
 * @Author: 徐未
 * @Date: 2019/12/03
 */
@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class Area {
	
	private Integer id;
	
	private String areaId;
	
	private String areaName;
	
	private String cityId;
	
}
