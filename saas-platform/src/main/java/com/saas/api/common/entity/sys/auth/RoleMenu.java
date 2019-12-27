package com.saas.api.common.entity.sys.auth;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 权限菜单关联表
 * @Author: 徐未
 * @Date: 2019/11/19
 */
@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class RoleMenu {

    private Long id;

    private Integer roleId;

    private Integer menuId;

    private String type;

}
