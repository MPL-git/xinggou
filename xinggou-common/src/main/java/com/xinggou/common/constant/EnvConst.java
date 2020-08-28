package com.xinggou.common.constant;

/**
 * 环境常量
 */
public class EnvConst {

    /**
     * 运行环境
     */
    public static String ENV;

    public static boolean isDevEnv() {
        return ENV.contains("dev");
    }

    public static boolean isTestEnv() {
        return ENV.contains("test");
    }

    public static boolean isProdEnv() {
        return ENV.contains("prod");
    }

    public static String JOB_URL;
    public static String TEMP_CERTIFICATE;
    public static String LOG_FILE;

}
