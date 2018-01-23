package com.avalon.holygrail.ss.exception;

import com.avalon.holygrail.ss.norm.ResultInfo;

/**
 * 需要登录异常
 */
public class NeedLoginException extends ResultException {

    public NeedLoginException(ResultInfo resultInfo) {
        super(resultInfo);
    }
}
