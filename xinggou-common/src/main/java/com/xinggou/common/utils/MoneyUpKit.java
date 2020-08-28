package com.xinggou.common.utils;

import java.math.BigDecimal;

/**
 *
 */
public final class MoneyUpKit {

    public static final String EMPTY = "";
    public static final String COMMA = ",";
    public static final String DOT = ".";
    public static final String DAWN = "、";
    public static final String NEGATIVE = "-";
    public static final String PERCENT = "%";
    public static final String SEMICOLON = "‰";

    // 大写数字
    private static final String[] NUMBERS = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
    // 整数部分的单位
    private static final String[] IUNIT = {"元", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "万", "拾", "佰", "仟"};
    // 小数部分的单位
    private static final String[] DUNIT = {"角", "分", "厘"};

    /**
     * 金额转为大写。
     *
     * @param amount 输入金额，注意输入金额应仅保留两位小数
     * @return 中文大写金额
     */
    public static String convert(BigDecimal amount) {
        if (amount == null) {
            return EMPTY;
        }
        if (amount.compareTo(BigDecimal.ZERO) == 0) {
            return "零元";
        }
        String amountStr = amount.stripTrailingZeros().toPlainString();
        return toChinese(amountStr);
    }

    private static String toChinese(String str) {
        //判断是否存在负号"-"
        boolean flag = false;
        if (str.startsWith(NEGATIVE)) {
            flag = true;
            str = str.replaceAll(NEGATIVE, EMPTY);
        }

        str = str.replaceAll(COMMA, EMPTY);//去掉","
        String integerStr;//整数部分数字
        String decimalStr;//小数部分数字


        //初始化：分离整数部分和小数部分
        if (str.indexOf(DOT) > 0) {
            integerStr = str.substring(0, str.indexOf(DOT));
            decimalStr = str.substring(str.indexOf(DOT) + 1);
        } else if (str.indexOf(DOT) == 0) {
            integerStr = EMPTY;
            decimalStr = str.substring(1);
        } else {
            integerStr = str;
            decimalStr = EMPTY;
        }

        //beyond超出计算能力，直接返回
        if (integerStr.length() > IUNIT.length) {
            return str;
        }

        int[] integers = toIntArray(integerStr);//整数部分数字
        boolean isWan = isWan5(integerStr);//设置万单位
        int[] decimals = toIntArray(decimalStr);//小数部分数字
        String result = getChineseInteger(integers, isWan) + getChineseDecimal(decimals);//返回最终的大写金额
        if (flag) {
            return "负" + result;//如果是负数，加上"负"
        } else {
            return result;
        }
    }

    //将字符串转为int数组
    private static int[] toIntArray(String number) {
        int[] array = new int[number.length()];
        for (int i = 0; i < number.length(); i++) {
            array[i] = Integer.parseInt(number.substring(i, i + 1));
        }
        return array;
    }

    //将整数部分转为大写的金额
    private static String getChineseInteger(int[] integers, boolean isWan) {
        StringBuilder chineseInteger = new StringBuilder();
        int length = integers.length;
        if (length == 1 && integers[0] == 0) {
            return EMPTY;
        }
        for (int i = 0; i < length; i++) {
            String key = EMPTY;
            if (integers[i] == 0) {
                if ((length - i) == 13)//万（亿）
                {
                    key = IUNIT[4];
                } else if ((length - i) == 9) {//亿
                    key = IUNIT[8];
                } else if ((length - i) == 5 && isWan) {//万
                    key = IUNIT[4];
                } else if ((length - i) == 1) {//元
                    key = IUNIT[0];
                }
                if ((length - i) > 1 && integers[i + 1] != 0) {
                    key += NUMBERS[0];
                }
            }
            chineseInteger.append(integers[i] == 0 ? key : (NUMBERS[integers[i]] + IUNIT[length - i - 1]));
        }
        return chineseInteger.toString();
    }

    //将小数部分转为大写的金额
    private static String getChineseDecimal(int[] decimals) {
        StringBuilder chineseDecimal = new StringBuilder();
        for (int i = 0; i < decimals.length; i++) {
            if (i == 3) {
                break;
            }
            chineseDecimal.append(decimals[i] == 0 ? EMPTY : (NUMBERS[decimals[i]] + DUNIT[i]));
        }
        return chineseDecimal.toString();
    }

    //判断当前整数部分是否已经是达到【万】
    private static boolean isWan5(String integerStr) {
        int length = integerStr.length();
        if (length > 4) {
            String subInteger;
            if (length > 8) {
                subInteger = integerStr.substring(length - 8, length - 4);
            } else {
                subInteger = integerStr.substring(0, length - 4);
            }
            return Integer.parseInt(subInteger) > 0;
        } else {
            return false;
        }
    }

}
