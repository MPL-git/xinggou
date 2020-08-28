package com.xinggou.common.utils;

import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * 随机数工具
 */
public final class RandomUtil {

    public static final String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 返回一个定长的随机字符串(只包含大小写字母、数字)
     *
     * @param length 随机字符串长度
     * @return 随机字符串
     */
    public static String generateString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
        }
        return sb.toString();
    }

    /**
     * 产生随机UUID，不带"-"
     *
     * @return String
     */
    public static String randomUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 创建序列号
     *
     * @param prefix   前缀
     * @param appendix 后缀
     * @return String
     */
    public static String createSerialNumber(String prefix, String appendix) {
        String serialNumber = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());

        if (StringUtils.isNotBlank(prefix)) {
            serialNumber = prefix + serialNumber;
        }
        if (StringUtils.isNotBlank(appendix)) {
            serialNumber = serialNumber + appendix;
        }

        return serialNumber;
    }

    /**
     * 产生随机字符串
     *
     * @param length    长度
     * @param hasNumber 是否有数字
     * @return String
     */
    public static String randomStr(int length, boolean hasNumber) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        if (!hasNumber) {
            base = "abcdefghijklmnopqrstuvwxyz";
        }

        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }

        return sb.toString();
    }

    /**
     * 产生随机数值字符
     *
     * @param length 长度
     * @return string
     */
    public static String randomNumericStr(int length) {
        String base = "0123456789";

        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }

        return sb.toString();
    }

    /**
     * 产生随机数值字符
     *
     * @param nextInt 随即数范围
     * @return string
     */
    public static int nextInt(int nextInt) {
        Random random = new Random();
        return random.nextInt(nextInt);
    }

    /**
     * 产生随机手机号
     */
    private static String[] telFirst = "134,135,136,137,138,139,150,151,152,153,154,155,156,157,158,159,185,186,187,188,189".split(",");

    public static String randomMobile() {
        int index = nextInt(telFirst.length);
        String first = telFirst[index];
        String second = String.valueOf(nextInt(100000000) + 100000000).substring(1);
        return first + second;
    }

}
