package com.saas.api.admin.res.select.common;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 省份
 * @Author: 徐未
 * @Date: 2019/12/03
 */
@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class ProvinceAll {
	private String provinceId;
	
	private String provinceName;
	
	private List<CityAll> cityList;
	
}
