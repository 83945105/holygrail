package com.avalon.holygrail.db.model;

import com.avalon.holygrail.db.exception.DBException;
import com.avalon.holygrail.db.norm.CharacterSet;
import com.avalon.holygrail.db.norm.Engine;
import com.avalon.holygrail.utils.StringUtil;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * 数据库表
 *
 * @author 白超
 * @date 2018/2/8
 */
public abstract class AbstractTable {

    /**
     * 表名
     */
    protected String name;

    /**
     * 是否构建主键列
     */
    protected boolean buildPrimaryKey;

    /**
     * 主键列
     */
    protected AbstractColumn primaryKey;

    /**
     * 是否构建自增长列
     */
    protected boolean buildAutoIncrement;

    /**
     * 自增长列
     */
    protected AbstractColumn autoIncrement;

    /**
     * 列
     */
    protected Iterable<AbstractColumn> columns;

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

    public AbstractColumn getPrimaryKey() {
        return primaryKey;
    }

    public boolean isBuildPrimaryKey() {
        return buildPrimaryKey;
    }

    public void setBuildPrimaryKey(boolean buildPrimaryKey) {
        this.buildPrimaryKey = buildPrimaryKey;
    }

    public boolean isBuildAutoIncrement() {
        return buildAutoIncrement;
    }

    public void setBuildAutoIncrement(boolean buildAutoIncrement) {
        this.buildAutoIncrement = buildAutoIncrement;
    }

    public void setPrimaryKey(AbstractColumn primaryKey) throws DBException {
        if (!primaryKey.isPrimaryKey()) {
            throw new DBException("Column未设置为primaryKey,columnName:" + primaryKey.getName());
        }
        this.buildPrimaryKey = true;
        this.primaryKey = primaryKey;
    }

    public AbstractColumn getAutoIncrement() {
        return autoIncrement;
    }

    public void setAutoIncrement(AbstractColumn autoIncrement) throws DBException {
        if (!autoIncrement.isAutoIncrement()) {
            throw new DBException("Column未设置为autoIncrement,columnName:" + primaryKey.getName());
        }
        this.buildAutoIncrement = true;
        this.autoIncrement = autoIncrement;
    }

    public Iterable<AbstractColumn> getColumns() {
        return columns;
    }

    public void setColumns(Iterable<AbstractColumn> columns) {
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
     *
     * @return
     * @throws DBException
     */
    abstract public String buildCreateSql() throws DBException;

    /**
     * 构建删除语句
     *
     * @return
     */
    abstract public String buildDropSql();

    /**
     * 构建批量插入预编译语句
     *
     * @param tableName   表名
     * @param columns     列
     * @param valueLength 数据长度
     * @return
     * @throws DBException
     */
    public static String buildBatchInsertPrecompileSql(String tableName, Iterable<AbstractColumn> columns, int valueLength) throws DBException {
        if (StringUtil.isEmpty(tableName)) {
            throw new DBException("未指定tableName");
        }
        if (columns == null) {
            throw new DBException("未指定columns");
        }
        if (valueLength < 1) {
            throw new DBException("valueLength必须大于0");
        }
        StringBuilder sql = new StringBuilder("INSERT INTO `").append(tableName).append("` (");
        Iterator<AbstractColumn> iterator = columns.iterator();
        AbstractColumn column;
        int len = 0;
        while (iterator.hasNext()) {
            column = iterator.next();
            sql.append("`").append(column.getName()).append("`,");
            len++;
        }
        if (len == 0) {
            throw new DBException("columns长度必须大于0");
        }
        sql.replace(0, sql.length(), sql.substring(0, sql.length() - 1)).append(") VALUES ");
        for (int i = 0; i < valueLength; i++) {
            sql.append("(");
            for (int j = len; j > 0; --j) {
                sql.append("?");
                if (j > 1) {
                    sql.append(",");
                }
            }
            sql.append("),");
        }
        return sql.substring(0, sql.length() - 1);
    }

    /**
     * 构建批量插入语句
     *
     * @param values 数据
     * @return
     * @throws DBException
     */
    abstract public String buildBatchInsertSql(Collection<Map<String, Object>> values) throws DBException;

    /**
     * 构建批量插入语句
     *
     * @param values            数据
     * @param insertColumnNames 指定插入列
     * @return
     * @throws DBException
     */
    abstract public String buildBatchInsertSql(Collection<Map<String, Object>> values, String... insertColumnNames) throws DBException;

}
