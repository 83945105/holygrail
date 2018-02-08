package com.avalon.holygrail.db.bean;

import com.avalon.holygrail.db.norm.Engine;

/**
 * MySql数据库引擎
 * Created by 白超 on 2018/2/8.
 */
public enum MySqlEngine implements Engine {
    ARCHIVE,
    BLACKHOLE,
    CSV,
    InnoDB,
    MEMORY,
    MRG_MYISAM,
    MyISAM,
    PERFORMANCE_SCHEMA
}
