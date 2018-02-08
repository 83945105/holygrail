package com.avalon.holygrail.db.model;

import com.avalon.holygrail.db.norm.ColumnType;

/**
 * 数据库列
 * Created by 白超 on 2018/2/8.
 */
public abstract class Column {

    /**
     * 列名
     */
    protected String name;

    /**
     * 列类型
     */
    protected ColumnType columnType;

    /**
     * 长度
     */
    protected int length = 1;

    /**
     * 小数长度
     */
    protected int decimalLength;

    /**
     * 自增长
     */
    protected boolean auto_increment;

    /**
     * 无符号
     * 表示全为正数,如: -128~127 => 0~255
     */
    protected boolean unsigned;

    /**
     * 填充零
     */
    protected boolean zerofill;

    /**
     * 非空
     */
    protected boolean not_null;

    /**
     * 默认值
     */
    protected String defaultValue;

    /**
     * 注释
     */
    protected String comment;

    public Column() {
    }

    public Column(String name, ColumnType columnType, int length) {
        this.name = name;
        this.columnType = columnType;
        this.length = length;
    }

    public Column(String name, ColumnType columnType, int length, String comment) {
        this.name = name;
        this.columnType = columnType;
        this.length = length;
        this.comment = comment;
    }

    abstract public String getName();

    abstract public ColumnType getColumnType();

    abstract public int getLength();

    abstract public int getDecimalLength();

    abstract public boolean isAuto_increment();

    abstract public boolean isUnsigned();

    abstract public boolean isZerofill();

    abstract public boolean isNot_null();

    abstract public String getDefaultValue();

    abstract public String getComment();

    public void setName(String name) {
        this.name = name;
    }

    public void setColumnType(ColumnType columnType) {
        this.columnType = columnType;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setDecimalLength(int decimalLength) {
        this.decimalLength = decimalLength;
    }

    public void setAuto_increment(boolean auto_increment) {
        this.auto_increment = auto_increment;
    }

    public void setUnsigned(boolean unsigned) {
        this.unsigned = unsigned;
    }

    public void setZerofill(boolean zerofill) {
        this.zerofill = zerofill;
    }

    public void setNot_null(boolean not_null) {
        this.not_null = not_null;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
