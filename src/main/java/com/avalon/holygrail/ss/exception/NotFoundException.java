package com.avalon.holygrail.ss.exception;

import com.avalon.holygrail.ss.norm.ResultInfo;

/**
 * 404异常
 */
public class NotFoundException extends ResultException {

    public NotFoundException(ResultInfo resultInfo) {
        super(resultInfo);
    }
}
