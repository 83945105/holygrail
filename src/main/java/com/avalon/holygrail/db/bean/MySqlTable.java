package com.avalon.holygrail.db.bean;

import com.avalon.holygrail.db.exception.DBException;
import com.avalon.holygrail.db.model.AbstractColumn;
import com.avalon.holygrail.db.model.AbstractTable;
import com.avalon.holygrail.db.norm.CharacterSet;
import com.avalon.holygrail.db.norm.Engine;
import com.avalon.holygrail.util.StringUtil;

import java.util.*;

/**
 * MySql表
 * Created by 白超 on 2018/2/8.
 */
public class MySqlTable extends AbstractTable {

    /**
     * 数据库引擎
     */
    protected Engine engine = MySqlEngine.InnoDB;

    /**
     * 字符集
     */
    protected CharacterSet characterSet = MySqlCharacterSet.utf8mb4;

    @Override
    public Engine getEngine() {
        return engine;
    }

    @Override
    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    @Override
    public CharacterSet getCharacterSet() {
        return characterSet;
    }

    @Override
    public void setCharacterSet(CharacterSet characterSet) {
        this.characterSet = characterSet;
    }

    private void validateColumn(Map<String, Object> columns, String columnName, boolean join) throws DBException {
        if (columns.get(columnName) != null) {
            throw new DBException("column字段名重复,columnName:" + columnName);
        }
        if (join) {
            columns.put(columnName, true);
        }
    }

    @Override
    public String buildCreateSql() throws DBException {
        if (StringUtil.isEmpty(this.getName())) {
            throw new DBException("未设置MySqlTable名称");
        }

        ArrayList<AbstractColumn> indexList = new ArrayList<>();

        StringBuilder rs = new StringBuilder();
        Map<String, Object> columnMap = new HashMap<>();

        //是否构建了主键Sql
        boolean appendPrimaryKeySql = false;
        AbstractColumn primaryKey = null;
        //是否构建了自增长Sql
        boolean appendAutoIncrementSql = false;
        AbstractColumn autoIncrement = null;
        //是否有主键
        if (this.isBuildPrimaryKey() && this.getPrimaryKey() != null) {
            this.validateColumn(columnMap, this.getPrimaryKey().getName(), false);
            primaryKey = this.getPrimaryKey();
            rs.append("\n\t").append(primaryKey.buildColumnCreateSql()).append(",");
            appendPrimaryKeySql = true;
            if (primaryKey.isUseIndex()) {
                indexList.add(primaryKey);
            }
            if (this.getPrimaryKey().isAutoIncrement()) {
                //如果主键同时也是自增长,那就已经设置了自增长Sql
                autoIncrement = primaryKey;
                appendAutoIncrementSql = true;
            }
        }
        if (this.isBuildAutoIncrement() && !appendAutoIncrementSql && this.getAutoIncrement() != null) {//是否有自增长
            this.validateColumn(columnMap, this.getAutoIncrement().getName(), false);
            autoIncrement = this.getAutoIncrement();
            rs.append("\n\t").append(autoIncrement.buildColumnCreateSql()).append(",");
            appendAutoIncrementSql = true;
            if (autoIncrement.isUseIndex()) {
                indexList.add(autoIncrement);
            }
        }
        Iterable<AbstractColumn> columns = this.getColumns();
        AbstractColumn column;
        Iterator<AbstractColumn> iterator = columns.iterator();
        while (iterator.hasNext()) {
            column = iterator.next();
            //校验是否重复
            this.validateColumn(columnMap, column.getName(), true);
            //如果不需要构建主键或者已经构建过,而当前列为主键
            if ((!this.isBuildPrimaryKey() || appendPrimaryKeySql) && column.isPrimaryKey()) {
                continue;
            }
            //如果不需要构建自增或者已经构建过,而当前列为自增
            if ((!this.isBuildAutoIncrement() || appendAutoIncrementSql) && column.isAutoIncrement()) {
                continue;
            }
            rs.append("\n\t").append(column.buildColumnCreateSql()).append(",");
            if (column.isPrimaryKey()) {
                primaryKey = column;
                appendPrimaryKeySql = true;
            }
            if (column.isAutoIncrement()) {
                autoIncrement = column;
                appendAutoIncrementSql = true;
            }
            if (column.isUseIndex()) {
                indexList.add(column);
            }
        }

        //如果构建了主键Sql
        if (appendPrimaryKeySql) {
            rs.append("\n\t").append("PRIMARY KEY ").append("(`").append(primaryKey.getName()).append("`)").append(",");
        }

        /*索引开始*/
        for (AbstractColumn index : indexList) {
            if (index.getIndexType() == MySqlIndexType.Unique) {
                rs.append("\n\t").append("UNIQUE KEY `").append(index.getIndexName()).append("` (`").append(index.getName()).append("`) USING ").append(index.getIndexMethod());
                if (index.getIndexComment() != null) {
                    rs.append(" COMMENT '").append(index.getIndexComment()).append("'");
                }
                rs.append(",");
                continue;
            }
            if (index.getIndexType() == MySqlIndexType.Normal) {
                rs.append("\n\t").append("KEY `").append(index.getIndexName()).append("` (`").append(index.getName()).append("`) USING ").append(index.getIndexMethod());
                if (index.getIndexComment() != null) {
                    rs.append(" COMMENT '").append(index.getIndexComment()).append("'");
                }
                rs.append(",");
                continue;
            }
        }
        /*索引结束*/

        rs.insert(0, "CREATE TABLE `" + this.getName() + "` (");
        rs.replace(0, rs.length(), rs.substring(0, rs.length() - 1));
        rs.append("\n").append(") ENGINE=").append(this.getEngine());
        //如果构建了自增Sql
        if (appendAutoIncrementSql) {
            rs.append(" AUTO_INCREMENT=").append(Math.abs(autoIncrement.getAutoIncrementStartValue()));
        }
        rs.append(" DEFAULT CHARSET=").append(this.getCharacterSet()).append(";");
        return rs.toString();
    }

    @Override
    public String buildDropSql() {
        if (StringUtil.isEmpty(this.getName())) {
            System.err.println("MySqlTable name is empty");
            return "";
        }
        return "DROP TABLE IF EXISTS `" + this.getName() + "`;";
    }

    @Override
    public String buildBatchInsertSql(Collection<Map<String, Object>> values) throws DBException {
        LinkedHashSet<AbstractColumn> columns = new LinkedHashSet<>();
        if (this.getPrimaryKey() != null) {
            columns.add(this.getPrimaryKey());
        }
        if (this.getAutoIncrement() != null) {
            columns.add(this.getAutoIncrement());
        }
        this.getColumns().forEach(column -> columns.add(column));
        String precompileSql = AbstractTable.buildBatchInsertPrecompileSql(this.getName(), columns, values.size());
        //使用正则表达式填充?
        StringBuilder value = new StringBuilder(32);
        for (Map<String, Object> map : values) {
            value.replace(0, value.length(), "(");
            for (AbstractColumn column : columns) {
                if (map.get(column.getName()) == null) {
                    value.append(map.get(column.getName())).append(",");
                } else {
                    value.append("'").append(map.get(column.getName())).append("'").append(",");
                }
            }
            value.replace(0, value.length(), value.substring(0, value.length() - 1)).append(")");
            precompileSql = precompileSql.replaceFirst("\\([?,\\,]+\\)", value.toString());
        }
        return precompileSql;
    }

    @Override
    public String buildBatchInsertSql(Collection<Map<String, Object>> values, String... insertColumnNames) throws DBException {
        LinkedList<AbstractColumn> columns = new LinkedList<>();
        if (this.getPrimaryKey() != null) {
            columns.add(this.getPrimaryKey());
        }
        if (this.getAutoIncrement() != null) {
            columns.add(this.getAutoIncrement());
        }
        this.getColumns().forEach(column -> columns.add(column));
        boolean removed = true;
        for (int i = 0; i < columns.size(); ) {
            for (String insertColumnName : insertColumnNames) {
                if (insertColumnName.equals(columns.get(i))) {
                    removed = false;
                    break;
                }
            }
            if (removed) {
                columns.remove(i);
            } else {
                removed = true;
                i++;
            }
        }
        String precompileSql = AbstractTable.buildBatchInsertPrecompileSql(this.getName(), columns, values.size());
        //使用正则表达式填充?
        StringBuilder value = new StringBuilder(32);
        for (Map<String, Object> map : values) {
            value.replace(0, value.length(), "(");
            for (AbstractColumn column : columns) {
                value.append("'").append(map.get(column.getName())).append("'").append(",");
            }
            value.replace(0, value.length(), value.substring(0, value.length() - 1)).append(")");
            precompileSql = precompileSql.replaceFirst("\\([?,\\,]+\\)", value.toString());
        }
        return precompileSql;
    }

}
