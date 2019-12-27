package com.saas.api.admin.req.sys.auth;

import lombok.Data;

/**
 * @Description: 新增用户提交的数据
 * @Author: 徐未
 * @Date: 2019/11/20
 */
@Data
public class UserDataRequest {
    // id
    private Long adminId;
    
    private Integer groupId;
    
    private Integer companyId;
    
    private Integer departmentId;
    // 昵称
    private String userName;
    
    private String realName;
    // 登录密码
    private String password;
    // 工号
    private String workNo;
    
    // 状态 用户状态 0：禁用或删除； 1：正常
    private Integer status;
    
    // 邮箱
    private String email;
    // 头像
    private String avatar;
    private Integer sex;
    // 职位
    private Integer position;
    
    private String wechatOpenid;
    private String wechatNickname;
    
    private String lanType;
    
    private Integer roleId;
}
