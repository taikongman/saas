package com.saas.api.admin.req.sys.auth;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 角色的授权提交表单
 */
@Data
public class RoleRequest {
    @NotNull(message = "请选择角色")
    private Integer roleId;
    
    @NotEmpty(message = "请选择授权的权限规则")
    private List<Integer> menuIds;
    
    @NotEmpty(message = "请提交当前语言类型")
    private String lanType;
}
