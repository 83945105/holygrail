package com.avalon.holygrail.db.bean;

import com.avalon.holygrail.db.norm.IndexMethod;

/**
 * MySql索引方法
 *
 * @author 白超
 * @date 2018/2/26
 */
public enum MySqlIndexMethod implements IndexMethod {
    /**
     *
     */
    BTREE,
    HASH
}
