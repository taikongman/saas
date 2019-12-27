package com.saas.api.admin.res.select.crm;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: WarehouseSelect 
 * @Author: 徐未
 * @Date: 2019/12/13
 */
@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class WarehouseSelect {
	
	private Integer warehouseId;
    private String warehouseName;
	
	
}
