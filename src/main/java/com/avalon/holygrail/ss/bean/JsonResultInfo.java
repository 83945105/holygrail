package com.avalon.holygrail.ss.bean;

import com.avalon.holygrail.enums.EnumMethods;

/**
 * Created by 白超 on 2018/6/22.
 */
public class JsonResultInfo extends ResultInfoRealization {

    public void setType(String type) {
        super.setType(EnumMethods.getEnumMethods(ResultCodeEnum.values(), enumMethods -> ((ResultCodeEnum) enumMethods).getCode(), Integer.parseInt(type)));
    }
}
