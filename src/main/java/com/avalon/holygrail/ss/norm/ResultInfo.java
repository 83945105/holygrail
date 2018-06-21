package com.avalon.holygrail.ss.norm;

import java.io.Serializable;

/**
 * 结果集信息
 */
public interface ResultInfo extends Serializable {

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
     * @return
     */
    boolean isError();

    /**
     * 结果集类型
     * @return
     */
    int getType();

    /**
     * 获取信息提示
     * @return
     */
    String getMessage();

    /**
     * 获取异常信息
     * @return
     */
    String getExceptionMessage();

    /**
     * 设置异常信息
     * @param exceptionMessage
     */
    void setExceptionMessage(String exceptionMessage);
}
