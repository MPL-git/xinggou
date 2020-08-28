package com.xinggou.bc.constant;

/**
 * 系统用户中心常量
 */
public class BcRedisKeys {

    /**
     * refreshToken
     *
     * @param refreshToken refreshToken
     */
    public static String getRefreshTokenKey(String refreshToken) {
        return "bc:refreshToken:" + refreshToken;
    }
    /**
     * token黑名单
     *
     * @param token token
     */
    public static String getBlackTokenKey(String token) {
        return "bc:blackToken:" + token;
    }
    /**
     * 用户权限
     *
     * @param staffId staffId
     */
    public static String getStaffPermsKey(long staffId) {
        return "bc:perms:" + staffId;
    }


}
