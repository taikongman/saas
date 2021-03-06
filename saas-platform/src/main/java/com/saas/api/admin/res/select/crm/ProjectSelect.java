package com.saas.api.admin.res.select.crm;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 项目定义
 * @Author: 徐未
 * @Date: 2019/12/06
 */
@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class ProjectSelect {
    private Long id;
    
	private String projectName;
    
    
}