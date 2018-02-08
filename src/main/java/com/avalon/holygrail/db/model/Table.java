package com.avalon.holygrail.db.model;

import com.avalon.holygrail.db.norm.CharacterSet;
import com.avalon.holygrail.db.norm.Engine;

import java.util.Iterator;

/**
 * 数据库表
 * Created by 白超 on 2018/2/8.
 */
public abstract class Table {

    /**
     * 表名
     */
    protected String name;

    /**
     * 主键列
     */
    protected Column primaryKey;

    /**
     * 自增长列
     */
    protected Column auto_increment;

    /**
     * 列
     */
    protected Iterator<Column> columns;

    /**
     * 数据库引擎
     */
    protected Engine engine;

    /**
     * 字符集
     */
    protected CharacterSet characterSet;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Column getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(Column primaryKey) {
        this.primaryKey = primaryKey;
    }

    public Column getAuto_increment() {
        return auto_increment;
    }

    public void setAuto_increment(Column auto_increment) {
        this.auto_increment = auto_increment;
    }

    public Iterator<Column> getColumns() {
        return columns;
    }

    public void setColumns(Iterator<Column> columns) {
        this.columns = columns;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public CharacterSet getCharacterSet() {
        return characterSet;
    }

    public void setCharacterSet(CharacterSet characterSet) {
        this.characterSet = characterSet;
    }

    /**
     * 构建创建语句
     * @return
     */
    abstract public String buildCreateSql();

    /**
     * 构建删除语句
     * @return
     */
    abstract public String buildDropSql();
}
