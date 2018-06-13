package com.avalon.holygrail.enums;

/**
 * 状态枚举
 * Created by 白超 on 2018/3/21.
 */
public enum Status implements EnumMethods {

    /**
     * 正常
     */
    NORMAL,
    /**
     * 启用
     */
    ENABLED,
    /**
     * 禁用
     */
    DISABLED,
    /**
     * 删除
     */
    DELETED;

    @Override
    public Object getValue() {
        return this.name();
    }
}