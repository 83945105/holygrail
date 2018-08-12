package com.avalon.holygrail.ss.exception;

import com.avalon.holygrail.ss.norm.ResultInfo;

/**
 * @author 白超
 * @date 2018/6/25
 */
public class JsonViewParseException extends ResultException {

    public JsonViewParseException(ResultInfo resultInfo) {
        super(resultInfo);
    }

    public JsonViewParseException(ResultInfo resultInfo, String jumpUrl) {
        super(resultInfo, jumpUrl);
    }
}
