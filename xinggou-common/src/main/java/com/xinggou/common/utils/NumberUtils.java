package com.xinggou.common.utils;

import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 *
 */
public final class NumberUtils {

    public static BigDecimal BIG_DECIMAL_5 = new BigDecimal(5);
    public static BigDecimal BIG_DECIMAL_100 = new BigDecimal(100);
    public static BigDecimal BIG_DECIMAL_1000 = new BigDecimal(1000);

    public static int get(Integer val) {
        return val == null ? 0 : val;
    }

    public static long get(Long val) {
        return val == null ? 0L : val;
    }

    public static String get(BigDecimal val) {
        return val == null ? "" : val.toString();
    }

    public static BigDecimal add(BigDecimal... values) {
        BigDecimal result = BigDecimal.ZERO;
        for (BigDecimal value : values) {
            if (value != null) {
                result = result.add(value);
            }
        }
        return result;
    }

    public static Integer add(Integer... values) {
        Integer result = 0;
        for (Integer value : values) {
            if (value != null) {
                result += value;
            }
        }
        return result;
    }

    public static BigDecimal zeroIfNull(BigDecimal num) {
        return num == null ? BigDecimal.ZERO : num;
    }

    /**
     * 取反
     */
    public static BigDecimal inverse(BigDecimal val) {
        if (val == null) {
            return null;
        }
        return new BigDecimal(-1).multiply(val);
    }

    /**
     * 装饰折扣：0.30 转化为 3
     */
    public static String decorateDiscount(BigDecimal discount) {
        return discount.multiply(BigDecimal.TEN).stripTrailingZeros().toPlainString();
    }

    /**
     * 装饰折扣：0.30 转化为 30%
     */
    public static String decoratePercent(BigDecimal discount) {
        return discount.multiply(BIG_DECIMAL_100).stripTrailingZeros().toPlainString() + MoneyUpKit.PERCENT;
    }

    /**
     * 装饰千分数：0.30 转化为 300‰
     */
    public static String decorateSemicolon(BigDecimal num) {
        return num == null ? MoneyUpKit.EMPTY : num.multiply(BIG_DECIMAL_1000).stripTrailingZeros().toPlainString() + MoneyUpKit.SEMICOLON;
    }

    /**
     * 金额大于 0 判断
     */
    public static boolean gtZero(BigDecimal amount) {
        if (amount == null) {
            return false;
        }
        return amount.compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * 多个值{@link BigDecimal}比较，取最小值
     */
    public static BigDecimal min(BigDecimal... values) {
        BigDecimal result = values[0];
        for (BigDecimal value : values) {
            result = result.compareTo(value) < 0 ? value : result;
        }
        return result;
    }

    /**
     * 随机数 min - max 之间
     */
    public static int randomNum(int min, int max) {
        return min + ThreadLocalRandom.current().nextInt(max - min + 1);
    }

    /**
     * 随机金额 min - max 之间，支持小数位
     */
    public static BigDecimal randomAmount(BigDecimal min, BigDecimal max, int scale) {
        float minF = min.floatValue();
        float maxF = max.floatValue();
        BigDecimal result = new BigDecimal(Math.random() * (maxF - minF) + minF).setScale(scale, BigDecimal.ROUND_DOWN);
        if (result.compareTo(min) < 0) {
            result = randomAmount(min, max, scale);
        }
        return result;
    }

    /**
     * 判断是否相交{@link Long}
     *
     * @param values 由{@link MoneyUpKit#COMMA} 分隔的数值字串
     * @param list2  数值集合2
     */
    public static boolean notIntersect(String values, List<Long> list2) {
        List<Long> list1 = Arrays.stream(values.split(MoneyUpKit.COMMA)).map(Long::parseLong).distinct().collect(Collectors.toList());
        return CollectionUtils.isEmpty(intersect(list1, list2));
    }

    /**
     * 数值{@link Long}集合取交集
     */
    public static List<Long> intersect(List<Long> list1, List<Long> list2) {
        return list1.stream().filter(list2::contains).collect(Collectors.toList());
    }

    /**
     * 折扣计算，若discount为null 返回 原价
     * 精确：保留整数，四舍五入
     *
     * @param price    原价
     * @param discount 折扣（单位1，如0.5 表示5折）
     * @return 返回 原价 表示不使用优惠
     */
    public static BigDecimal calculateDiscountPrice(BigDecimal price, BigDecimal discount) {
        if (discount == null) {
            return price;
        }
        return price.multiply(discount).setScale(0, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * {@link BigDecimal#ZERO} 去小数点后面多余的0
     */
    public static String cleanZero(BigDecimal num) {
        return num.stripTrailingZeros().toPlainString();
    }

    /**
     * 修正金额数值：四舍五入，保留整数
     */
    public static BigDecimal fixAmount(BigDecimal origin) {
        return fixBigDecimal(origin, 0);
    }

    /**
     * 修正重量数值：四舍五入，最多保留2位小数
     */
    public static BigDecimal fixWeight(BigDecimal origin) {
        return fixBigDecimal(origin, 2);
    }

    private static BigDecimal fixBigDecimal(BigDecimal origin, int scale) {
        if (origin == null) {
            return null;
        }
        return new BigDecimal(origin.setScale(scale, BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString());
    }

    /**
     * 总额 = 单价 * 重量
     * 精确：保留2为小数，四舍五入
     */
    public static BigDecimal calculateAmount(BigDecimal singlePrice, BigDecimal weight) {
        if (singlePrice == null || weight == null) {
            return null;
        }
        return singlePrice.multiply(weight).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * @param goldWeight 金重，单位：克
     * @param stoneCarat 石重，单位：克拉
     * @return 总重，单位：克
     */
    public static BigDecimal calculateProductWeight(BigDecimal goldWeight, BigDecimal stoneCarat) {
        BigDecimal stoneWeight = stoneCarat == null ? BigDecimal.ZERO : stoneCarat.divide(BIG_DECIMAL_5, 4, BigDecimal.ROUND_HALF_UP);
        return add(goldWeight, stoneWeight);
    }

    public static String getUnit(Integer unitCode) {
        if (unitCode != null) {
            if (10 == unitCode) {
                return "g";
            }
            if (20 == unitCode) {
                return "ct";
            }
        }
        return "";
    }
}
