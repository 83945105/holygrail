package com.avalon.holygrail.db.bean;

import com.avalon.holygrail.db.model.Column;
import com.avalon.holygrail.db.norm.ColumnType;
import com.avalon.holygrail.util.StringUtil;

/**
 * MySql数据库列
 * Created by 白超 on 2018/2/8.
 */
public class MySqlColumn extends Column {

    public MySqlColumn() {
    }

    public MySqlColumn(String name, ColumnType columnType, int length) {
        super(name, columnType, length);
    }

    public MySqlColumn(String name, ColumnType columnType, int length, String comment) {
        super(name, columnType, length, comment);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public ColumnType getColumnType() {
        return this.columnType == null ? MySqlColumnType.varchar : this.columnType;
    }

    @Override
    public int getLength() {
        return this.length;
    }

    @Override
    public int getDecimalLength() {
        return this.decimalLength;
    }

    @Override
    public boolean isAuto_increment() {
        return this.auto_increment;
    }

    @Override
    public boolean isUnsigned() {
        return this.unsigned;
    }

    @Override
    public boolean isZerofill() {
        return this.zerofill;
    }

    @Override
    public boolean isNot_null() {
        return this.not_null;
    }

    @Override
    public String getDefaultValue() {
        return this.defaultValue;
    }

    @Override
    public String getComment() {
        return this.comment;
    }

    @Override
    public String toString() {
        StringBuilder rs = new StringBuilder();
        if (StringUtil.isEmpty(this.getName())) {
            System.err.println("MySqlColumn name is empty");
            return rs.toString();
        }
        rs.append("`").append(this.getName()).append("`");
        rs.append(" ").append(this.getColumnType().toString()).append("(").append(this.getLength());
        if (this.getDecimalLength() != 0 && this.getDecimalLength() < this.getLength()) {
            rs.append(",").append(this.getDecimalLength());
        } else if(this.getDecimalLength() != 0) {
            System.err.println("MySqlColumn decimalLength must less then length");
        }
        rs.append(")");
        if (this.unsigned) {
            rs.append(" unsigned");
        }
        if (this.zerofill) {
            rs.append(" zerofill");
        }
        if (this.not_null) {
            rs.append(" NOT NULL");
        }
        if (this.auto_increment) {
            rs.append(" AUTO_INCREMENT");
        }
        if(this.getDefaultValue() == null) {
            rs.append(" DEFAULT NULL");
        }else {
            rs.append(" DEFAULT ").append("'").append(this.getDefaultValue()).append("'");
        }
        if (!StringUtil.isEmpty(this.getComment())) {
            rs.append(" COMMENT").append(" '").append(this.getComment()).append("'");
        }
        return rs.toString();
    }

}
