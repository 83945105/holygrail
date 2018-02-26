package com.avalon.holygrail.db.model;

import com.avalon.holygrail.db.norm.ColumnType;
import com.avalon.holygrail.db.norm.IndexMethod;
import com.avalon.holygrail.db.norm.IndexType;

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
     * 主键
     */
    protected boolean primaryKey;

    /**
     * 自增长
     */
    protected boolean auto_increment;

    /**
     * 自增长开始值
     */
    protected int auto_increment_start_value = 0;

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

    /**
     * 是否使用索引
     */
    protected boolean useIndex;

    /**
     * 索引名称
     */
    protected String indexName;

    /**
     * 索引类型
     */
    protected IndexType indexType;

    /**
     * 索引方法
     */
    protected IndexMethod indexMethod;

    /**
     * 索引注释
     */
    protected String indexComment;

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

    abstract public boolean isPrimaryKey();

    abstract public String getName();

    abstract public ColumnType getColumnType();

    abstract public int getLength();

    abstract public int getDecimalLength();

    abstract public boolean isAuto_increment();

    abstract public int getAuto_increment_start_value();

    abstract public boolean isUnsigned();

    abstract public boolean isZerofill();

    abstract public boolean isNot_null();

    abstract public String getDefaultValue();

    abstract public String getComment();

    abstract public boolean isUseIndex();

    abstract public String getIndexName();

    abstract public IndexType getIndexType();

    abstract public IndexMethod getIndexMethod();

    abstract public String getIndexComment();

    abstract public String buildColumnCreateSql();

    public void setPrimaryKey(boolean primaryKey) {
        if (primaryKey) {
            this.setNot_null(true);
        }
        this.primaryKey = primaryKey;
    }

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

    public void setAuto_increment_start_value(int auto_increment_start_value) {
        this.auto_increment_start_value = auto_increment_start_value;
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

    public void setUseIndex(boolean useIndex) {
        this.useIndex = useIndex;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public void setIndexType(IndexType indexType) {
        this.indexType = indexType;
    }

    public void setIndexMethod(IndexMethod indexMethod) {
        this.indexMethod = indexMethod;
    }

    public void setIndexComment(String indexComment) {
        this.indexComment = indexComment;
    }
}
