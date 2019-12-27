package com.saas.api.common.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class PhotoBase64Utils {
	
	/**
     * 图片转BASE64
     * @param imagePath 路径
     * @return
     */
    public static String imageChangeBase64(String imagePath){
        InputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(imagePath);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 加密
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }
 
    /**
     * BASE转图片
     * @param baseStr  base64字符串
     * @param imagePath 生成的图片
     * @return
     */
    public static boolean base64ChangeImage(String baseStr,String imagePath){
        if (baseStr == null){
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // 解密
            byte[] b = decoder.decodeBuffer(baseStr);
            // 处理数据
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream(imagePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
