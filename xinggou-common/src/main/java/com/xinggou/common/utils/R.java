package com.xinggou.common.utils;

import com.xinggou.common.constant.CodeConst;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "响应参数")
public class R<T> {

    @ApiModelProperty(value = "是否成功")
    private boolean success = true;
    @ApiModelProperty(value = "响应编码")
    private int code = CodeConst.SUCCESS;
    @ApiModelProperty(value = "错误信息")
    private String msg;
    @ApiModelProperty(value = "返回数据")
    private T data;

    public static R ok() {
        return new R();
    }

    public static <T> R<T> ok(T data) {
        R<T> r = new R<>();
        r.setData(data);
        return r;
    }

    public static R error() {
        return error(CodeConst.ERROR, "未知异常，请联系管理员");
    }

    public static R error(String msg) {
        return error(CodeConst.ERROR, msg);
    }

    public static R error(int code, String msg) {
        R r = new R();
        r.setSuccess(false);
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
