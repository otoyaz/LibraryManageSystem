package com.bokehai.book.common;

import org.apache.commons.codec.digest.DigestUtils;

public class Md5Util {

    public final static String md5key = "Ms2fadskfjlaxiaofasfd";
    /**
     * MD5方法
     * @param text 明文
     * @return 密文
     * @throws Exception
     */
    public static String getMd5(String text) throws Exception {
        //加密后的字符串
        String encodeStr= DigestUtils.md5Hex(text + md5key);
//        System.out.println("MD5加密后的字符串为:encodeStr="+encodeStr);
        return encodeStr;
    }

    /**
     * MD5验证方法
     * @param text 明文
     * @param md5 密文
     * @return true/false
     * @throws Exception
     */
    public static boolean verifyMd5(String text, String md5) throws Exception {
        //根据传入的密钥进行验证
        String md5Text = getMd5(text);
        if(md5Text.equalsIgnoreCase(md5))
        {
//            System.out.println("MD5验证通过");
            return true;
        }
        return false;
    }
}
