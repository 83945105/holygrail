package com.avalon.holygrail.db.bean;

import com.avalon.holygrail.db.norm.ColumnType;

/**
 * MySql数据库列类型
 * Created by 白超 on 2018/2/8.
 */
public enum MySqlColumnType implements ColumnType {
    /**
     *
     */
    tinyint("tinyint"),
    smallint("smallint"),
    mediumint("mediumint"),
    int_("int"),
    integer_("integer"),
    bigint("bigint"),
    bit("bit"),
    real("real"),
    double_("double"),
    float_("float"),
    decimal("decimal"),
    numeric("numeric"),
    char_("char"),
    varchar("varchar"),
    date("date"),
    time("time"),
    year("year"),
    timestamp("timestamp"),
    datetime("datetime"),
    tinyblob("tinyblob"),
    blob("blob"),
    mediumblob("mediumblob"),
    longblob("longblob"),
    tinytext("tinytext"),
    text("text"),
    mediumtext("mediumtext"),
    longtext("longtext"),
    enum_("enum"),
    set("set"),
    binary("binary"),
    varbinary("varbinary"),
    point("point"),
    linestring("linestring"),
    polygon("polygon"),
    geometry("geometry"),
    multipoint("multipoint"),
    multilinestring("multilinestring"),
    multipolygon("multipolygon"),
    geometrycollection("geometrycollection");

    public String value;

    MySqlColumnType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
