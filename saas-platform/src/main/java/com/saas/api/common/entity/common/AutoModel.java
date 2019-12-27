package com.saas.api.common.entity.common;

import java.util.Date;

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
public class AutoModel {
	
	 private Integer id;
	 
	 private String firstName;
	 private Integer brandId;
	 private String brandName;
	 private String brandLogo;
		
	 private Integer seriesId;
	 private String seriesName;
	 private String seriesPhoto;
	 
	 private Integer modelId;
	 private String modelName;
	 
	 private String saleStatus;
	 private String manufacture;
	 private Integer year;
	 
	 private String updateDate;
	 private String startDate;
	 private String guidePrice;
	 private String autoLevel;
	 private String engine;
	 private String gearbox;
	 private String size;
	 private String structure;
	 private String highSpeed;
	 
	 private Date createTime;
		
	 private Date updateTime;
		
	 private String lanType;

}
