package com.avalon.holygrail.db.bean;

import com.avalon.holygrail.db.model.Column;
import com.avalon.holygrail.db.model.Table;
import com.avalon.holygrail.db.norm.CharacterSet;
import com.avalon.holygrail.db.norm.Engine;
import com.avalon.holygrail.util.StringUtil;

import java.util.Iterator;

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

    @Override
    public String buildCreateSql() {
        StringBuilder rs = new StringBuilder();
        if (StringUtil.isEmpty(this.getName())) {
            System.err.println("MySqlTable name is empty");
            return rs.toString();
        }
        boolean isBuildPrimaryColumn = this.getPrimaryKey() == null;
        boolean isBuildAutoIncrementColumn = this.getAuto_increment() == null;
        Iterator<Column> columns = this.getColumns();
        Column column;
        while (columns.hasNext()) {
            column = columns.next();
            if (!isBuildPrimaryColumn && this.getPrimaryKey().getName().equals(column.getName())) {
                isBuildPrimaryColumn = true;
            }
            if (!isBuildAutoIncrementColumn && this.getAuto_increment().getName().equals(column.getName())) {
                isBuildAutoIncrementColumn = true;
            }
            rs.append("\n\t").append(column.toString()).append(",");
        }

        if (!isBuildAutoIncrementColumn) {
            rs.insert(0, "\n\t" + this.getAuto_increment().toString());
        }
        if (!isBuildPrimaryColumn) {
            rs.insert(0, "\n\t" + this.getPrimaryKey().toString());
            rs.append("\n\t").append("PRIMARY KEY ").append("(`").append(this.getPrimaryKey().getName()).append("`)").append(",");
        }

        rs.insert(0, "CREATE TABLE `" + this.getName() + "` (");
        rs.replace(0, rs.length(), rs.substring(0, rs.length() - 1));
        rs.append("\n").append(" ENGINE=").append(this.getEngine());
        if (!isBuildAutoIncrementColumn) {
            rs.append(" AUTO_INCREMENT=").append(StringUtil.isEmpty(this.getAuto_increment().getDefaultValue()) ? 0 : Math.abs(Integer.parseInt(this.getAuto_increment().getDefaultValue())));
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
