package com.avalon.holygrail.ss.bean;

import com.avalon.holygrail.ss.norm.ResultCode;

/**
 * 结果码枚举
 */
public enum ResultCodeEnum implements ResultCode {

    /**
     * 失败
     */
    FAIL(ResultCode.FAIL_CODE, "失败", "fail"),
    /**
     * 成功
     */
    SUCCESS(ResultCode.SUCCESS_CODE, "成功", "success"),
    /**
     * 警告
     */
    WARN(ResultCode.WARN_CODE, "警告", "warn"),
    /**
     * 信息提示
     */
    INFO(ResultCode.INFO_CODE, "信息", "info"),
    /**
     * 错误
     */
    ERROR(ResultCode.ERROR_CODE, "错误", "error"),
    /**
     * 需要登录
     */
    NEED_LOGIN(ResultCode.NEED_LOGIN_CODE, "需要登录", "needLogin"),
    /**
     * 无权
     */
    NO_AUTHORITY(ResultCode.NO_AUTHORITY_CODE, "没有权限", "noAuthority"),
    /**
     * 资源不存在
     */
    NOT_FOUND(ResultCode.NOT_FOUND, "资源不存在", "404");

    private final int code;

    private final String name;

    private final String value;

    ResultCodeEnum(int code, String name, String value) {
        this.code = code;
        this.name = name;
        this.value = value;
    }


    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean isSuccess() {
        return this == ResultCodeEnum.SUCCESS;
    }

    @Override
    public boolean isFail() {
        return this == ResultCodeEnum.FAIL;
    }

    @Override
    public boolean isError() {
        return this == ResultCodeEnum.ERROR;
    }

}
