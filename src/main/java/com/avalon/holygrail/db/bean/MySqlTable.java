package com.avalon.holygrail.db.bean;

import com.avalon.holygrail.db.exception.DBException;
import com.avalon.holygrail.db.model.Column;
import com.avalon.holygrail.db.model.Table;
import com.avalon.holygrail.db.norm.CharacterSet;
import com.avalon.holygrail.db.norm.Engine;
import com.avalon.holygrail.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * MySql表
 * Created by 白超 on 2018/2/8.
 */
public class MySqlTable extends Table {

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

        ArrayList<Column> indexList = new ArrayList<>();

        StringBuilder rs = new StringBuilder();
        Map<String, Object> columnMap = new HashMap<>();

        boolean appendPrimaryKeySql = false;//是否构建了主键Sql
        Column primaryKey = null;
        boolean appendAutoIncrementSql = false;//是否构建了自增长Sql
        Column autoIncrement = null;

        if (this.isBuildPrimaryKey() && this.getPrimaryKey() != null) {//是否有主键
            this.validateColumn(columnMap, this.getPrimaryKey().getName(), false);
            primaryKey = this.getPrimaryKey();
            rs.append("\n\t").append(primaryKey.buildColumnCreateSql()).append(",");
            appendPrimaryKeySql = true;
            if (primaryKey.isUseIndex()) {
                indexList.add(primaryKey);
            }
            if (this.getPrimaryKey().isAuto_increment()) {
                //如果主键同时也是自增长,那就已经设置了自增长Sql
                autoIncrement = primaryKey;
                appendAutoIncrementSql = true;
            }
        }
        if (this.isBuildAutoIncrement() && !appendAutoIncrementSql && this.getAuto_increment() != null) {//是否有自增长
            this.validateColumn(columnMap, this.getAuto_increment().getName(), false);
            autoIncrement = this.getAuto_increment();
            rs.append("\n\t").append(autoIncrement.buildColumnCreateSql()).append(",");
            appendAutoIncrementSql = true;
            if (autoIncrement.isUseIndex()) {
                indexList.add(autoIncrement);
            }
        }
        Iterable<Column> columns = this.getColumns();
        Column column;
        Iterator<Column> iterator = columns.iterator();
        while (iterator.hasNext()) {
            column = iterator.next();
            //校验是否重复
            this.validateColumn(columnMap, column.getName(), true);
            //如果不需要构建主键或者已经构建过,而当前列为主键
            if ((!this.isBuildPrimaryKey() || appendPrimaryKeySql) && column.isPrimaryKey()) {
                continue;
            }
            //如果不需要构建自增或者已经构建过,而当前列为自增
            if ((!this.isBuildAutoIncrement() || appendAutoIncrementSql) && column.isAuto_increment()) {
                continue;
            }
            rs.append("\n\t").append(column.buildColumnCreateSql()).append(",");
            if (column.isPrimaryKey()) {
                primaryKey = column;
                appendPrimaryKeySql = true;
            }
            if (column.isAuto_increment()) {
                autoIncrement = column;
                appendAutoIncrementSql = true;
            }
            if (column.isUseIndex()) {
                indexList.add(column);
            }
        }

        if (appendPrimaryKeySql) {//如果构建了主键Sql
            rs.append("\n\t").append("PRIMARY KEY ").append("(`").append(primaryKey.getName()).append("`)").append(",");
        }

        /*索引开始*/
        for (Column index : indexList) {
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
        if (appendAutoIncrementSql) {//如果构建了自增Sql
            rs.append(" AUTO_INCREMENT=").append(Math.abs(autoIncrement.getAuto_increment_start_value()));
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
}
