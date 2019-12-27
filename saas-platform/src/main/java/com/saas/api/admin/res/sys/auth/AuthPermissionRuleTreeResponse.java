package com.saas.api.admin.res.sys.auth;

import lombok.Data;

/**
 * 权限列表整合为多维数组的视图
 */
@Data
public class AuthPermissionRuleTreeResponse {

    private Long id;
    private Long pid;
    private String name;
    private String title;
    private String html;
    private Integer Level;
    private Long status;
    private String expression;
    private Long listorder;

}
