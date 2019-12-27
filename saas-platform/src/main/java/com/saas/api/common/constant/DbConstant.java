package com.saas.api.common.constant;

public class DbConstant {
	
	public static Integer INT_ZERO = Integer.valueOf(0);
	
	public static Integer INT_ONE = Integer.valueOf(1);
	
	public static Integer INE_TWO = Integer.valueOf(2);
	
	public static Integer INIT_VERSION = Integer.valueOf(1);
	
	public static Integer STATUS_ONE = Integer.valueOf(1);
	
	public static Integer STATUS_TWO = Integer.valueOf(2);
	
	// 开单未领料
	public static Integer ORDER_MATERIAL_STATUS_INIT = Integer.valueOf(1);

	//待派工
	public static Integer ORDER_STATUS_INIT = Integer.valueOf(0);

	//施工中
	public static Integer ORDER_STATUS_DOING = Integer.valueOf(1);

	//待结算
	public static Integer ORDER_STATUS_UNSETTLE = Integer.valueOf(2);

	//挂账
	public static Integer ORDER_STATUS_SUSPEND = Integer.valueOf(3);

	//已完成
	public static Integer ORDER_STATUS_FINISH = Integer.valueOf(4);

	//已报审
	public static Integer ORDER_STATUS_APPLY = Integer.valueOf(5);

	//已审核
	public static Integer ORDER_STATUS_REVIEWED = Integer.valueOf(6);

	//已取消
	public static Integer ORDER_STATUS_CANCEL = Integer.valueOf(7);

	public static Integer COMM_STATUS_NORMAL = Integer.valueOf(1);

	public static Integer COMM_STATUS_DEL = Integer.valueOf(2);

	public static Integer INVENTORY_IN = Integer.valueOf(1);

	public static Integer INVENTORY_OUT = Integer.valueOf(2);

	public static Integer INVENTORY_RETURN = Integer.valueOf(3);

	public static Integer MANUFACTURER_STATUS_NORMAL = Integer.valueOf(1);

	public static Integer MANUFACTURER_STATUS_DEL = Integer.valueOf(2);

	public static Integer WARN_TYPE_DOWN = Integer.valueOf(1);

	public static Integer WARN_TYPE_UP = Integer.valueOf(2);

	public static Integer PROJECT_STATUS_NORMAL = Integer.valueOf(1);

	public static Integer PROJECT_STATUS_DEL = Integer.valueOf(2);

	public static Integer CUSTOMER_STATUS_NORMAL = Integer.valueOf(1);

	public static Integer CUSTOMER_STATUS_DEL = Integer.valueOf(2);

	public static Integer CAR_STATUS_NORMAL = Integer.valueOf(1);

	public static Integer CAR_STATUS_DEL = Integer.valueOf(2);

	public static Integer ACCOUNT_TYPE_NORMAL = Integer.valueOf(1);

	public static Integer ACCOUNT_TYPE_CARD = Integer.valueOf(2);

	public static Integer STAFF_STATUS_NORMAL = Integer.valueOf(1);

	public static Integer STAFF_STATUS_DEL = Integer.valueOf(2);

	public static Integer FEEDBACK_STATUS_INIT = Integer.valueOf(0);

	public static Integer FEEDBACK_STATUS_FINISH = Integer.valueOf(1);
}
