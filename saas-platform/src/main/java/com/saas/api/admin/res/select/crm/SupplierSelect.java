package com.saas.api.admin.res.select.crm;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 
 * @Author: 徐未
 * @Date: 2019/12/09
 */
@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class SupplierSelect {
	
    private Integer supplierId;
    
    private String supplierName;
	
	
}
