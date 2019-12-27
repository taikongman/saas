package com.saas.api.admin.res.sys.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;


/**
 * @Description: 用户人员管理后台返回信息
 * @Author: 徐未
 * @Date: 2019/11/20
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
	
	 // 主键
    private Long adminId;
    
 // 公司ID
    private Integer groupId;
    
    // 公司名称
    private String groupName;
	
	// 公司ID
    private Integer companyId;
    
    // 公司名称
    private String companyName;
    
    // 部门ID
    private Integer departmentId;
    
    // 部门名称
    private String departmentName;
	
    // 昵称
    private String userName;
    
    // 昵称
    private String nickName;
    // 真实姓名
    private String realName;
    // 工号
    private String workNo;
    // 手机号
    private String phone;
    // 邮箱
    private String email;
    // 头像
    private String avatar;
    
    private String avatarUrl;
    // 性别；0：保密，1：男；2：女
    private Integer sex;
    // 用户状态 0：禁用或删除； 1：正常
    private Integer status;
    // 职位
    private Integer position;
    
    private String wechatOpenid;
    
    private String wechatNickname;
    // 最后登录ip
    private String lastLoginIp;
    // 最后登录时间
    private Date lastLoginTime;
    // 创建时间
    private Date createTime;
    private Date updateTime;
    
    // 角色ids
    private Integer roleId;
    
    private String roleName;
    

}
