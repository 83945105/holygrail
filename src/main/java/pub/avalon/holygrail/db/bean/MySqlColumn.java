package pub.avalon.holygrail.db.bean;

import pub.avalon.holygrail.db.model.AbstractColumn;
import pub.avalon.holygrail.db.norm.ColumnType;
import pub.avalon.holygrail.db.norm.IndexMethod;
import pub.avalon.holygrail.db.norm.IndexType;
import pub.avalon.holygrail.utils.StringUtil;

/**
 * MySql数据库列
 *
 * @author 白超
 * @date 2018/2/8
 */
public class MySqlColumn extends AbstractColumn {

    public MySqlColumn() {
    }

    public MySqlColumn(String name, ColumnType columnType, int length) {
        super(name, columnType, length);
    }

    public MySqlColumn(String name, ColumnType columnType, int length, String comment) {
        super(name, columnType, length, comment);
    }

    @Override
    public boolean isPrimaryKey() {
        return this.primaryKey;
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
        if (this.getColumnType() == MySqlColumnType.double_) {
            return this.decimalLength > 0 ? this.decimalLength : 1;
        }
        return this.decimalLength;
    }

    @Override
    public int getAutoIncrementStartValue() {
        return this.autoIncrementStartValue;
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
    public boolean isNotNull() {
        return isPrimaryKey() ? true : this.notNull;
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
    public boolean isUseIndex() {
        return this.useIndex;
    }

    @Override
    public String getIndexName() {
        return StringUtil.isEmpty(this.indexName) ? "idx_" + this.getName() : this.indexName;
    }

    @Override
    public IndexType getIndexType() {
        return this.indexType != null ? this.indexType : this.isPrimaryKey() ? MySqlIndexType.Unique : MySqlIndexType.Normal;
    }

    @Override
    public IndexMethod getIndexMethod() {
        return this.indexMethod != null ? this.indexMethod : MySqlIndexMethod.BTREE;
    }

    @Override
    public String getIndexComment() {
        return this.indexComment;
    }

    @Override
    public String buildColumnCreateSql() {
        StringBuilder rs = new StringBuilder();
        if (StringUtil.isEmpty(this.getName())) {
            System.err.println("MySqlColumn name is empty");
            return rs.toString();
        }
        rs.append("`").append(this.getName()).append("`");
        rs.append(" ").append(this.getColumnType().toString()).append("(").append(this.getLength());
        if (this.getDecimalLength() != 0 && this.getDecimalLength() < this.getLength()) {
            rs.append(",").append(this.getDecimalLength());
        } else if (this.getDecimalLength() != 0) {
            System.err.println("MySqlColumn decimalLength must less then length");
        }
        rs.append(")");
        if (this.unsigned) {
            rs.append(" unsigned");
        }
        if (this.zerofill) {
            rs.append(" zerofill");
        }
        if (this.notNull) {
            rs.append(" NOT NULL");
        }
        if (this.autoIncrement) {
            rs.append(" AUTO_INCREMENT");
        }
        if (!this.notNull && this.getDefaultValue() == null) {
            rs.append(" DEFAULT NULL");
        } else if (this.getDefaultValue() != null) {
            rs.append(" DEFAULT ").append("'").append(this.getDefaultValue()).append("'");
        }
        if (!StringUtil.isEmpty(this.getComment())) {
            rs.append(" COMMENT").append(" '").append(this.getComment()).append("'");
        }
        return rs.toString();
    }

}
