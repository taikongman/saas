package com.saas.api.common.entity.crm;

import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 门店仓库货位信息
 * @Author: 徐未
 * @Date: 2019/12/13
 */
@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class WarehouseLocation{
    private Long id;
    
    private Integer warehouseId;
    
	private String locationCode;
    private String locationName;
    
    private String remark;
    
    //状态，1：正常，2：删除
    private Integer status;
    
    private Date createTime;
    private Date updateTime;
    // 语言类型 中文zh_cn 英文en_us等
    private String lanType;

    
}