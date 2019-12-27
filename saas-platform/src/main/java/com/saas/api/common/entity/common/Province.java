package com.saas.api.common.entity.common;


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
public class Province {
	
	private Integer id;
	
	private String provinceId;
	
	private String provinceName;
	
	private String provinceSn;
	
	private String provinceCapid;
	
	private Integer flag;
	

}
