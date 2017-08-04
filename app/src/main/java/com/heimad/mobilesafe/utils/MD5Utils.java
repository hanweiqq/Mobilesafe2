package com.heimad.mobilesafe.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 文件加密
 * Created by Administrator on 2017/8/4.
 */

public class MD5Utils {
    /**
     * 文件加密
     *
     * @param password
     * @return
     */
    public static String digest(String password) {
        StringBuilder sb = new StringBuilder();
        try {
            //创建md5加密算法  不可逆

            MessageDigest digest = MessageDigest.getInstance("md5");
            //加密后的结果返回byte数组
            byte[] bytes = digest.digest(password.getBytes());
            for (byte b : bytes) {
                //int i = (b&0xff)+3  //把负数转化成了正数 不规则的md5加密
                int i = b & 0xff;
                String hexString = Integer.toHexString(i);//把一个10进制数转换为16进制的字符串
                if (hexString.length() < 2) {
                    sb.append("0");//如果是1位  前面补0
                }
                sb.append((hexString));
            }
            String result = sb.toString();
            return result;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
