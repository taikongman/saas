package com.saas.api.common.entity.common;

import java.util.Date;

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
public class AutoSeries {
	
	 private Integer id;
	 
	 private String firstName;
	 
	 private Integer brandId;
	 
	 private Integer seriesId;
		
	 private String seriesName;
	 
	 private String seriesPhoto;
	 
	 private String manufacture;
	 
	 private Integer seriesLevel;
	 
	 private Integer lowPrice;
	 
	 private Integer highPrice;
	 
	 private Date createTime;
		
	 private Date updateTime;
		
	 private String lanType;
	 

}
