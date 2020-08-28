package com.xinggou.common.constant;

/**
 * 响应编码
 */
public final class CodeConst {

    public static int SUCCESS = 0;
    public static int ERROR = 500;


    public static int CODE_401 = 401;   // 没有提供认证信息。请求的时候没有带上 Token 等。
    public static int CODE_402 = 402;   // token失效
    public static int CODE_403 = 403;   // 请求的资源不允许访问。就是说没有权限。

}
