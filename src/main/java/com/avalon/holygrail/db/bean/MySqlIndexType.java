package com.avalon.holygrail.db.bean;

import com.avalon.holygrail.db.norm.IndexType;

/**
 * MySql索引类型
 *
 * @author 白超
 * @date 2018/2/26
 */
public enum MySqlIndexType implements IndexType {
    /**
     * 普通索引
     */
    Normal,
    /**
     * 唯一索引
     */
    Unique
}
