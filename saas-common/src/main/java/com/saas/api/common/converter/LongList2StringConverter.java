package com.saas.api.common.converter;



import java.util.*;

/**
 *  Long 类型的 List 转为字符串
 */
public class LongList2StringConverter {

    /**
     * Long 类型的 List 转为字符串
     * @param longList
     * @param regex
     * @return
     */
    public static String convert(List<Long> longList, String regex) {

        if (longList.isEmpty()) {
            return null;
        }
        Set<String> stringSet = new HashSet<>();
        for (Long value: longList){
            stringSet.add(value.toString());
        }
        if (stringSet.isEmpty()) {
            return null;
        }
        
        StringBuilder sb = new StringBuilder();
        String convertedListStr = "";
        for (String item : stringSet) {
            sb.append(item);
            sb.append(regex);
        }
        convertedListStr = sb.toString();
        convertedListStr = convertedListStr.substring(0, convertedListStr.length() - regex.length());
        return convertedListStr;
        //return null;//StringUtils.join(stringSet, regex);
    }
}
