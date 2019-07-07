package pub.avalon.beans;

/**
 * 分页工具类
 *
 * @author 白超
 * @version 1.0
 * @since 2018/7/10
 */
public class Pagination implements LimitSql {

    /**
     * 数据库类型
     */
    private DataBaseType dataBaseType;
    /**
     * 总记录数
     */
    private long total;
    /**
     * 当前页号
     */
    private long currentPage = 1;
    /**
     * 每页显示条数
     */
    private long pageSize = 1;

    public Pagination(DataBaseType dataBaseType) {
        this.dataBaseType = dataBaseType;
    }

    public Pagination(DataBaseType dataBaseType, Long currentPage, Long pageSize) {
        this.dataBaseType = dataBaseType;
        this.setCurrentPage(currentPage);
        this.setPageSize(pageSize);
    }

    public Pagination(DataBaseType dataBaseType, Long total, Long currentPage, Long pageSize) {
        this.dataBaseType = dataBaseType;
        this.setTotal(total);
        this.setCurrentPage(currentPage);
        this.setPageSize(pageSize);
    }

    public DataBaseType getDataBaseType() {
        return dataBaseType;
    }

    public void setDataBaseType(DataBaseType dataBaseType) {
        this.dataBaseType = dataBaseType;
    }

    @Override
    public long getTotal() {
        return this.total;
    }

    @Override
    public void setTotal(Long total) {
        this.total = (total == null || total <= 0) ? 0 : total;
    }

    @Override
    public long getCurrentPage() {
        return this.currentPage;
    }

    @Override
    public void setCurrentPage(Long currentPage) {
        this.currentPage = (currentPage == null || currentPage <= 0) ? 1 : currentPage;
    }

    @Override
    public long getPageSize() {
        return this.pageSize;
    }

    @Override
    public void setPageSize(Long pageSize) {
        this.pageSize = (pageSize == null || pageSize <= 0) ? 1 : pageSize;
    }


    @Override
    public long getPageCount() {
        if (this.total <= 0) {
            return 1L;
        }
        if (this.total % this.pageSize == 0) {
            return this.total / this.pageSize;
        }
        if (total % pageSize > 0) {
            return this.total / this.pageSize + 1;
        }
        return 1L;
    }

    public long getOracleStartNum() {
        return (this.currentPage - 1) * this.pageSize + 1;
    }

    public long getOracleEndNum() {
        return this.currentPage * this.pageSize;
    }

    public long getMySqlStartNum() {
        return (this.currentPage - 1) * this.pageSize;
    }

    public long getMySqlEndNum() {
        return this.getPageSize();
    }

    public long getSqlServerStartNum() {
        return (this.currentPage - 1) * this.pageSize + 1;
    }

    public long getSqlServerEndNum() {
        return this.currentPage * this.pageSize;
    }

    @Override
    public Long getLimitStartNum() {
        switch (this.dataBaseType) {
            case ORACLE:
                return this.getOracleStartNum();
            case MYSQL:
                return this.getMySqlStartNum();
            case SQLSERVER:
                return this.getSqlServerStartNum();
            default:
                return null;
        }
    }

    @Override
    public Long getLimitEndNum() {
        switch (this.dataBaseType) {
            case ORACLE:
                return this.getOracleEndNum();
            case MYSQL:
                return this.getMySqlEndNum();
            case SQLSERVER:
                return this.getSqlServerEndNum();
            default:
                return null;
        }
    }

    @Override
    public Long getRowStartNum() {
        return (this.currentPage - 1) * this.pageSize + 1;
    }

    @Override
    public Long getRowEndNum() {
        return this.currentPage * pageSize;
    }

}
