package com.avalon.holygrail.ss.bean;

/**
 * Created by 白超 on 2018/6/22.
 */
public class JsonResultInfo extends ResultInfoRealization {

    public void setType(int type) {
        for (ResultCode code : ResultCode.values()) {
            if (code.getCode() == type) {
                super.setType(code);
                return;
            }
        }
    }
}
