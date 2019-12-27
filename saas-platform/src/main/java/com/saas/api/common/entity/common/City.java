package com.saas.api.common.entity.common;

import java.math.BigDecimal;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 城市
 * @Author: 徐未
 * @Date: 2019/12/03
 */
@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class City {
	
	private Integer id;
	
	private String cityId;
	
	private String cityName;
	
	private String autoCode;
	
	private BigDecimal longitude;
	
	private BigDecimal latitude;
	
	private String provinceId;
	
	
}
