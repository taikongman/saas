package com.saas.api.admin.res.select.sys;

import com.saas.api.common.entity.sys.Company;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 账号所属公司
 * @author by 九尾 2019-11-12
 *
 */
@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class CompanySelect {
	    
    // 公司ID 主键
    private Integer companyId;
    
    // 公司名称
    private String companyName;
    
     
    public void copyData(Company company) {
    	this.companyId = company.getCompanyId();
    	this.companyName = company.getCompanyName();
    }
    
}
