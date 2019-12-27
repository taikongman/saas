package com.saas.api.common.constant;

/**
 * Created by kangs on 2018/10/15.
 */
public class ResponseCodeConstant {

    public static Integer SUCCESS = Integer.valueOf(0);

    public static Integer SYS_ERROR = Integer.valueOf(100001);

    public static Integer ORDER_PROJECT_EMPTY = Integer.valueOf(100002);

    public static Integer MEMBER_NOT_EXIST = Integer.valueOf(100003);

    public static Integer BALANCE_ERROR = Integer.valueOf(100004);

    public static Integer ACCOUNT_NOT_EXIST = Integer.valueOf(100005);

    public static Integer EXPIRE_AT_SMALL = Integer.valueOf(100006);

    public static Integer HAVE_THIS_CARD = Integer.valueOf(100007);

    public static Integer COMMODITY_CODE_EXIST = Integer.valueOf(100008);

    public static Integer COMMODITY_EXIST_INVENTORYRECORD = Integer.valueOf(100009);

    public static Integer MANUFACTURER_EXIST= Integer.valueOf(100010);

    public static Integer PROJECT_CODE_EXIST= Integer.valueOf(100011);

    public static Integer CUSTOMER_EXIST= Integer.valueOf(100012);

    public static Integer CAR_EXIST= Integer.valueOf(100013);

    public static Integer ORDER_ALREADY_SETTLE= Integer.valueOf(100014);

    public static Integer GET_OCR_TOKEN_FAIL= Integer.valueOf(100015);

    public static Integer OCR_FAIL = Integer.valueOf(100016);
}
