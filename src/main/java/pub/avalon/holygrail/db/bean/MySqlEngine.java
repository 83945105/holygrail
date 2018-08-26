package pub.avalon.holygrail.db.bean;

import pub.avalon.holygrail.db.norm.Engine;

/**
 * MySql数据库引擎
 *
 * @author 白超
 * @date 2018/2/8
 */
public enum MySqlEngine implements Engine {
    /**
     *
     */
    ARCHIVE,
    BLACKHOLE,
    CSV,
    InnoDB,
    MEMORY,
    MRG_MYISAM,
    MyISAM,
    PERFORMANCE_SCHEMA
}
