package com.avalon.holygrail.ss.norm;

/**
 * 结果值
 */
public interface ResultCode {

    int FAIL_CODE = 0;
    int SUCCESS_CODE = 1;
    int WARN_CODE = 2;
    int INFO_CODE = 3;
    int ERROR_CODE = 4;
    int NEED_LOGIN_CODE = 5;
    int NO_AUTHORITY_CODE = 6;
    int NOT_FOUND = 404;

    /**
     * 获取编号
     * @return
     */
    int getCode();

    /**
     * 获取名称
     * @return
     */
    String getName();

    /**
     * 获取值
     * @return
     */
    String getValue();

    /**
     * 是否成功
     * @return
     */
    boolean isSuccess();

    /**
     * 是否失败
     * @return
     */
    boolean isFail();

    /**
     * 是否错误
     */
    boolean isError();
}
