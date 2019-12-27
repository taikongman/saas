package com.saas.api.common.enums;

import lombok.Getter;

/**
 * 返回结果的枚举类
 */
@Getter
public enum ResultEnum {

    SUCCESS(0, "success"),
    NOT_NETWORK(1, "系统繁忙，请稍后再试。"),
    LOGIN_VERIFY_FALL(2, "登录失效"),
    PARAM_VERIFY_FALL(3, "参数验证错误"),
    AUTH_FAILED(4, "权限验证失败"),
    DATA_NOT(5, "没有相关数据"),
    DATA_CHANGE(6, "数据没有任何更改"),
    DATA_REPEAT(7, "数据已存在"),
    UPLOAD_FILE_EMPTY(8, "上传失败，请选择文件"),
    UPLOAD_FILE_FAILED(9, "上传失败"),
    USER_NOT_PERMISSION(10, "该账号已被冻结"),
    DATE_FORMAT_ERROR(11, "日期格式化错误"),
    USER_NOT_EXIST(12, "用户不存在"),
    ORIGINAL_PASSWORD_ERROR(13, "原始密码错误"),
    SAME_PASSWORD_NO_CHANGE(14, "新密码和旧密码一样"),
    BAIDU_API_PROCESS_ERROR(15, "人脸API处理错误"),
    BAIDU_API_CAN_NOT_FIND_FACE(16, "人脸识别失败，未匹配到人脸数据"),
    EXPORT_DATA_FAILED(17, "导出数据失败"),
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
