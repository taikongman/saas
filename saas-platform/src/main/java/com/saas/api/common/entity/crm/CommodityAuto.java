package com.saas.api.common.entity.crm;

import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 信息
 * @Author: 徐未
 * @Date: 2019/12/06
 */
@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class CommodityAuto{
	
    private Long id;
    
    private Long commodityId;
    private Integer brandId;
	private String brandName;
	
    private Date createTime;
    private Date updateTime;

    
}