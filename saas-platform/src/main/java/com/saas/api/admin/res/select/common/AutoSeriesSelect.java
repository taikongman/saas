package com.saas.api.admin.res.select.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 汽车系列
 * @Author: 徐未
 * @Date: 2019/11/29
 */
@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class AutoSeriesSelect {
	
	 private Integer seriesId;
		
	 private String seriesName;
	 
	 

}
