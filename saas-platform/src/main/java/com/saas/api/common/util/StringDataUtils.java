package com.saas.api.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.saas.api.common.constant.CommonConstant;


public class StringDataUtils {
	

	public static String join(List<String> stringList, String regex) {
        
        StringBuilder sb = new StringBuilder();
        String convertedListStr = "";
        for (String item : stringList) {
            sb.append(item);
            sb.append(regex);
        }
        convertedListStr = sb.toString();
        convertedListStr = convertedListStr.substring(0, convertedListStr.length() - regex.length());
        return convertedListStr;
	}
	
	public static String genOrderNo() {
        SimpleDateFormat sdf = new SimpleDateFormat(CommonConstant.DATE_FORMAT_YYMMDDHHMMSS);
        StringBuilder orderNo = new StringBuilder();
        orderNo.append(sdf.format(new Date())).append("-").append(genRandomNum(2));
        return orderNo.toString();
    }
	
	public static String genPurchaseCode() {
        SimpleDateFormat sdf = new SimpleDateFormat(CommonConstant.DATE_FORMAT_YYMMDDHHMMSS);
        StringBuilder purchaseCode = new StringBuilder("PURCHASE_");
        purchaseCode.append(sdf.format(new Date())).append("-").append(genRandomNum(2));
        return purchaseCode.toString();
    }

    public static String genRandomNum(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(CommonConstant.BASE_NUMBER.length());
            sb.append(CommonConstant.BASE_NUMBER.charAt(number));
        }
        return sb.toString();
    }
    
    public static Long genRandomLong(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(CommonConstant.BASE_NUMBER.length());
            sb.append(CommonConstant.BASE_NUMBER.charAt(number));
        }
        return Long.valueOf(sb.toString());
    }
}
