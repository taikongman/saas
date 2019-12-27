package com.saas.api.common.constant;

import lombok.Getter;
import lombok.ToString;

/**
 * @Description: 多语言,国际化接口返回码枚举
 * @Author: 徐未
 * @Date: 2019/11/12
 */
@Getter
@ToString
public enum ResponseCodeI18n {
	SUCCESS(1000, "api.response.code.success"),
    FAIL(-1, "api.response.code.fail"),
    UNKNOWN_ERROR(-1000,"api.response.code.unknownError"),
    NOT_NETWORK(100,"api.response.code.networkError"),
    
    
    // 公共参数
    LOGIN_VERIFY_FALL(999, "api.response.code.loginVerifyFail"),
    PARAM_ERROR(1001, "api.response.code.paramError"),
    LANGUAGE_TYPE_ERROR(1002, "api.response.code.languageTypeError"),
    AUTH_FAILED(1003, "api.response.code.authFailed"),
    DATE_FORMAT_FAILED(1004, "api.response.code.dateFormatFailed"),
    
    
    // 组 公司 部门 数据校验
    GROUP_IS_EXIST(2001,"api.response.code.sys.groupIsExist"),
    GROUP_IS_NOT_EXIST(2002,"api.response.code.sys.groupIsNotExist"),
    COMPANY_IS_EXIST(2003,"api.response.code.sys.companyIsExist"),
    COMPANY_IS_NOT_EXIST(2004,"api.response.code.sys.companyIsNotExist"),
    DEPARTMENT_IS_EXIST(2005,"api.response.code.sys.departmentIsExist"),
    DEPARTMENT_IS_NOT_EXIST(2006,"api.response.code.sys.departmentIsNotExist"),
    ROLE_IS_EXIST(2007,"api.response.code.sys.auth.roleIsExist"),
    ROLE_IS_NOT_EXIST(2008,"api.response.code.sys.auth.roleIsNotExist"),
    ROLE_ADMIN_IS_NOT_EXIST(2009,"api.response.code.sys.auth.roleAdminIsNotExist"),
    MENU_IS_EXIST(2010,"api.response.code.sys.auth.menuIsExist"),
    MENU_IS_NOT_EXIST(2011,"api.response.code.sys.auth.menuIsNotExist"),
    PARENT_MENU_IS_NOT_EXIST(2012,"api.response.code.sys.auth.parentMenuIsNotExist"),
    USER_IS_EXIST(2013,"api.response.code.sys.auth.userIsExist"),
    USER_IS_NOT_EXIST(2014,"api.response.code.sys.auth.userIsNotExist"),
    USERNAME_OR_PASSWORD_ERROR(2015,"api.response.code.sys.auth.userNameOrPasswordError"),
    USER_HAVE_NO_PRIVILEDGE(2016,"api.response.code.sys.auth.userHaveNoPriviledge"),
    MENU_HAVE_DOWN_MENUS(2017,"api.response.code.sys.auth.menuHaveDownMenus"),
    ROLE_HAVE_ADMIN_USERS(2018,"api.response.code.sys.auth.roleHaveAdminUsers"),
    
    
    // 帐号用户模块
    ACCOUNT_NULL_ERROR(3001,"api.response.code.user.accountNullError"),
    ACCOUNT_FORMAT_ERROR(3002, "api.response.code.user.accountFormatError"),
    ACCOUNT_NOT_EXIST(3003,"api.response.code.user.accountNotExist"),
    ACCOUNT_EXIST(3004,"api.response.code.user.accountExist"),
    
    // 密码
    PASSWORD_NULL_ERROR(3101, "api.response.code.user.passwordNullError"),
    PASSWORD_FORMAT_ERROR(3102, "api.response.code.user.passwordFormatError"),
    PASSWORD_ERROR(3103,"api.response.code.user.passwordError"),
    
    
    //CRM_MODULE
    MODULE_CRM_ORDER_IS_EXIST(4001,"api.response.code.module.crm.orderIsExist"),
    MODULE_CRM_ORDER_IS_NOT_EXIST(4001,"api.response.code.module.crm.orderIsNotExist"),
    MODULE_CRM_ORDER_ID_IS_NOT_EXIST(4001,"api.response.code.module.crm.orderIdIsNotExist"),
    MODULE_CRM_ORDER_PROJECT_IS_EMPTY(4001,"api.response.code.module.crm.orderProjectIsEmpty"),
    MODULE_CRM_ORDER_COMMODITY_IS_EMPTY(4001,"api.response.code.module.crm.orderCommodityIsEmpty"),
    MODULE_CRM_ORDER_COMMODITY_CODE_IS_EXIST(4002,"api.response.code.module.crm.orderCommodityCodeIsExist"),
    MODULE_CRM_ORDER_ALREADY_SETTLE(4003,"api.response.code.module.crm.orderAlreadySettle"),
    MODULE_CRM_ACCOUNT_IS_NOT_EXIST(4004,"api.response.code.module.crm.acountIsNotExist"),
    MODULE_CRM_ACCOUNT_BALANCE_IS_ERROR(4005,"api.response.code.module.crm.accountBalanceIsError"),
    MODULE_CRM_ACCOUNT_EFFECTIVE_TIME_LESS_THAN_BEFORE(4006,"api.response.code.module.crm.accountEffectTimeLessThanBefore"),
    MODULE_CRM_ACCOUNT_COMMODITY_BALANCE_IS_ERROR(4005,"api.response.code.module.crm.accountCommodityBalanceIsError"),
    MODULE_CRM_ACCOUNT_PROJECT_BALANCE_IS_ERROR(4005,"api.response.code.module.crm.accountProjectBalanceIsError"),
    MODULE_CRM_ACCOUNT_BALANCE_HAS_BEEN_USED(4402,"api.response.code.module.crm.accountBalanceHasBeenUsed"),
    
    MODULE_CRM_COMMODITY_EXIST_INVENTORY_RECORD(4101,"api.response.code.module.crm.commodityExistInventoryRecord"),
    
    MODULE_CRM_COMMODITY_CATEGORY_IS_EXIST(4201,"api.response.code.module.crm.commodityCategoryIsExist"),
    MODULE_CRM_COMMODITY_CATEGORY_IS_NOT_EXIST(4202,"api.response.code.module.crm.commodityCategoryIsNotExist"),
    MODULE_CRM_COMMODITY_PARENT_CATEGORY_IS_NOT_EXIST(4202,"api.response.code.module.crm.commodityParentCategoryIsNotExist"),
    MODULE_CRM_COMMODITY_IS_EXIST(4201,"api.response.code.module.crm.commodityIsExist"),
    MODULE_CRM_COMMODITY_IS_NOT_EXIST(4202,"api.response.code.module.crm.commodityIsNotExist"),
    MODULE_CRM_SUPPLIER_IS_EXIST(4203,"api.response.code.module.crm.supplierIsExist"),
    MODULE_CRM_SUPPLIER_IS_NOT_EXIST(4204,"api.response.code.module.crm.supplierIsNotExist"),
    MODULE_CRM_WAREHOUSE_IS_EXIST(4203,"api.response.code.module.crm.warehouseIsExist"),
    MODULE_CRM_WAREHOUSE_IS_NOT_EXIST(4204,"api.response.code.module.crm.warehouseIsNotExist"),
    MODULE_CRM_WAREHOUSE_LOCATION_IS_EXIST(4203,"api.response.code.module.crm.warehouseLocationIsExist"),
    MODULE_CRM_WAREHOUSE_LOCATION_IS_NOT_EXIST(4204,"api.response.code.module.crm.warehouseLocationIsNotExist"),
    MODULE_CRM_UNIT_IS_EXIST(4201,"api.response.code.module.crm.unitIsExist"),
    MODULE_CRM_UNIT_IS_NOT_EXIST(4202,"api.response.code.module.crm.unitIsNotExist"),
    MODULE_CRM_INVOICE_IS_EXIST(4203,"api.response.code.module.crm.invoiceIsExist"),
    MODULE_CRM_INVOICE_IS_NOT_EXIST(4204,"api.response.code.module.crm.invoiceIsNotExist"),
    MODULE_CRM_OPERATOR_IS_EXIST(4203,"api.response.code.module.crm.operatorIsExist"),
    MODULE_CRM_OPERATOR_IS_NOT_EXIST(4204,"api.response.code.module.crm.operatorIsNotExist"),
    MODULE_CRM_TRANSPORT_MODE_IS_EXIST(4203,"api.response.code.module.crm.transportModeIsExist"),
    MODULE_CRM_TRANSPORT_MODE_IS_NOT_EXIST(4204,"api.response.code.module.crm.transportModeIsNotExist"),
    MODULE_CRM_PURCHASE_RECORD_IS_EXIST(4203,"api.response.code.module.crm.purchaseRecordIsExist"),
    MODULE_CRM_PURCHASE_RECORD_IS_NOT_EXIST(4204,"api.response.code.module.crm.purchaseRecordIsNotExist"),
    MODULE_CRM_INVENTORY_RECORD_IS_EXIST(4203,"api.response.code.module.crm.inventoryRecordIsExist"),
    MODULE_CRM_INVENTORY_RECORD_IS_NOT_EXIST(4204,"api.response.code.module.crm.inventoryRecordIsNotExist"),
    MODULE_CRM_CASH_MODE_IS_EXIST(4201,"api.response.code.module.crm.cashModeIsExist"),
    MODULE_CRM_CASH_MODE_IS_NOT_EXIST(4202,"api.response.code.module.crm.cashModeIsNotExist"),
    MODULE_CRM_MAINTAIN_TYPE_IS_EXIST(4201,"api.response.code.module.crm.maintainTypeIsExist"),
    MODULE_CRM_MAINTAIN_TYPE_IS_NOT_EXIST(4202,"api.response.code.module.crm.maintainTypeIsNotExist"),
    MODULE_CRM_PRICING_MODE_IS_EXIST(4201,"api.response.code.module.crm.pricingModeIsExist"),
    MODULE_CRM_PRICING_MODE_IS_NOT_EXIST(4202,"api.response.code.module.crm.pricingModeIsNotExist"),
    MODULE_CRM_SETTLE_MODE_IS_EXIST(4201,"api.response.code.module.crm.settleModeIsExist"),
    MODULE_CRM_SETTLE_MODE_IS_NOT_EXIST(4202,"api.response.code.module.crm.settleModeIsNotExist"),
    MODULE_CRM_PROJECT_CATEGORY_IS_EXIST(4201,"api.response.code.module.crm.projectCategoryIsExist"),
    MODULE_CRM_PROJECT_CATEGORY_IS_NOT_EXIST(4202,"api.response.code.module.crm.projectCategoryIsNotExist"),
    MODULE_CRM_PROJECT_PARENT_CATEGORY_IS_NOT_EXIST(4202,"api.response.code.module.crm.projectParentCategoryIsNotExist"),
    MODULE_CRM_PROJECT_TYPE_IS_EXIST(4201,"api.response.code.module.crm.projectTypeIsExist"),
    MODULE_CRM_PROJECT_TYPE_IS_NOT_EXIST(4202,"api.response.code.module.crm.projectTypeIsNotExist"),
    MODULE_CRM_ORDER_ID_IN_COMMODITY_ID_IS_DIFFERENT_FROM_ORDER_ID(4202,"api.response.code.module.crm.orderIdInCommodityIsDifferent"),
    MODULE_CRM_ORDER_COMMODITY_NO_LESS_THAN_NEED(4202,"api.response.code.module.crm.orderCommodityNoIsLessThanNeed"),
    MODULE_CRM_ORDER_COMMODITY_OPERATE_TYPE_ERROR(4202,"api.response.code.module.crm.orderCommodityOperateTypeError"),
    
    MODULE_CRM_CARD_CATEGORY_IS_EXIST(4201,"api.response.code.module.crm.cardCategoryIsExist"),
    MODULE_CRM_CARD_CATEGORY_IS_NOT_EXIST(4202,"api.response.code.module.crm.cardCategoryIsNotExist"),
    MODULE_CRM_CARD_PARENT_CATEGORY_IS_NOT_EXIST(4202,"api.response.code.module.crm.cardParentCategoryIsNotExist"),
    MODULE_CRM_CARD_IS_EXIST(4201,"api.response.code.module.crm.cardIsExist"),
    MODULE_CRM_CARD_IS_NOT_EXIST(4202,"api.response.code.module.crm.cardIsNotExist"),
    
    
    MODULE_CRM_PROJECT_IS_EXIST(4301,"api.response.code.module.crm.projectIsExist"),
    MODULE_CRM_PROJECT_IS_NOT_EXIST(4302,"api.response.code.module.crm.projectIsNotExist"),
    
    MODULE_CRM_CUSTOMER_IS_EXIST(4401,"api.response.code.module.crm.customerIsExist"),
    MODULE_CRM_CUSTOMER_IS_NOT_EXIST(4402,"api.response.code.module.crm.customerIsNotExist"),
    
    MODULE_CRM_CAR_IS_EXIST(4501,"api.response.code.module.crm.carIsExist"),
    MODULE_CRM_CAR_IS_NOT_EXIST(4502,"api.response.code.module.crm.carIsNotExist"),
    
    
    // 百度
    MODULE_BAIDU_OCR_AUTOLICENSE_FAILED(4601,"api.response.code.module.baidu.ocrAutoLicenseFailed"),
    MODULE_BAIDU_OCR_GET_OCR_TOKEN_FAILED(4602,"api.response.code.module.baidu.getOcrTokenFailed"),
    
    // 文件上传
 	UPLOAD_FILE_IS_EMPTY(9101, "api.response.code.file.upLoadFileIsEmpty"),
 	CREATE_DIRECTORY_FAILED(9102, "api.response.code.file.createDirectoryFailed"),
 	UPLOAD_FILE_IS_FAILDED(9103, "api.response.code.file.upLoadFileFailed"),
    
    SYSTEM_ERROR(10000,"api.response.code.systemError");	
	
    // 返回码
    private Integer code;
    
    // 返回信息
    private String message;
    
    private ResponseCodeI18n(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    
}
