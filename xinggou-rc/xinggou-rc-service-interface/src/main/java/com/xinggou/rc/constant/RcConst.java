package com.xinggou.rc.constant;

/**
 * 资源中心常量
 */
public class RcConst {

    // 定时任务状态
    public static final int JOB_STATUS_NORMAL = 10; //正常
    public static final int JOB_STATUS_STOP = 20; //暂停

    public static final int JOB_LOG_STATUS_SUCCESS = 10; //成功
    public static final int JOB_LOG_STATUS_FAIL = 20; //失败

    // 显示状态
    public static final int DISPLAY_STATUS_NORMAL = 10; //显示
    public static final int DISPLAY_STATUS_HIDDEN = 20; //暂停

    public static final int CAPTCHA_SMS_LIVE_SECONDS = 30 * 60; // 短信验证码有效时间30分钟（单位：秒）
    public static final int CAPTCHA_SMS_SEND_INTERVAL = 60; // 短信验证码连续发送间隔时间（单位：秒）

    public static final String DEFAULT_PIC_URL = "图片上传地址";


    // 微信应用类型
    public static final int WX_APP_TYPE_WECHAT = 10; //公众号

    // 微信场景二维码
    public static final int SCENE_TYPE_SYS_USER = 10; // 用户二维码

    // 微信appId
    public static final String WX_WECHAT_DEFAULT = ""; // 公众号

    // 微信模板消息ID
    public static final String MESSAGE_TEMPLATE_CUSTOMER_NOTICE = "OPENTM204431262";


    // APP推送消息代号
    public static final int APP_MESSAGE_CODE_MEMBER_MSG = 100000;    // 客户消息

    public static final String APP_MESSAGE_TEXT_WRAP = "\n"; // 消息换行
}
