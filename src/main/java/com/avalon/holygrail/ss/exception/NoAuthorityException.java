package com.avalon.holygrail.ss.exception;

import com.avalon.holygrail.ss.norm.ResultInfo;

/**
 * 无权异常
 *
 * @author 白超
 */
public class NoAuthorityException extends ResultException {

    public NoAuthorityException(ResultInfo resultInfo) {
        super(resultInfo);
    }
}
