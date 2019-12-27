package com.saas.api.admin.res.select.crm;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 客户信息类
 * @Author: 徐未
 * @Date: 2019/11/26
 */
@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class CustomerSelect {
    private Long id;
    
    private String name;

}