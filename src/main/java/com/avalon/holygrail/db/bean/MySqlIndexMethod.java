package com.avalon.holygrail.db.bean;

import com.avalon.holygrail.db.norm.IndexMethod;

/**
 * MySql索引方法
 * Created by 白超 on 2018/2/26.
 */
public enum MySqlIndexMethod implements IndexMethod {
    BTREE,
    HASH
}
