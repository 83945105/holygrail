package pub.avalon.beans;

/**
 * 分页工具类
 *
 * @author 白超
 * @version 1.0
 * @since 2018/7/10
 */
public class Pagination implements LimitHandler {

    /**
     * 数据库类型
     */
    private DataBaseType dataBaseType;

    /**
     * 总记录数
     */
    private int total;
    /**
     * 当前页号
     */
    private int currentPage = 1;
    /**
     * 每页显示条数
     */
    private int pageSize = 1;
    /**
     * Oracle起始记录号
     */
    private int oracleStartNum;
    /**
     * Oracle结束记录号
     */
    private int oracleEndNum;
    /**
     * MySQL起始记录号
     */
    private int mySqlStartNum = -1;
    /**
     * SqlServer起始记录号
     */
    private int sqlServerStartNum;
    /**
     * SqlServer结束记录号
     */
    private int sqlServerEndNum;

    public Pagination(DataBaseType dataBaseType) {
        this.dataBaseType = dataBaseType;
    }

    public Pagination(DataBaseType dataBaseType, Integer currentPage, Integer pageSize) {
        this.dataBaseType = dataBaseType;
        this.setCurrentPage(currentPage);
        this.setPageSize(pageSize);
    }

    public Pagination(DataBaseType dataBaseType, Integer total, Integer currentPage, Integer pageSize) {
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
    public Integer getTotal() {
        return this.total;
    }

    @Override
    public void setTotal(Integer total) {
        this.total = (total == null || total <= 0) ? 0 : total;
    }

    @Override
    public Integer getCurrentPage() {
        return this.currentPage;
    }

    @Override
    public void setCurrentPage(Integer currentPage) {
        this.currentPage = (currentPage == null || currentPage <= 0) ? 1 : currentPage;
    }

    @Override
    public Integer getPageSize() {
        return this.pageSize;
    }

    @Override
    public void setPageSize(Integer pageSize) {
        this.pageSize = (pageSize == null || pageSize <= 0) ? 1 : pageSize;
    }


    @Override
    public Integer getPageCount() {
        if (this.total <= 0) {
            return 1;
        }
        if (this.total % this.pageSize == 0) {
            return this.total / this.pageSize;
        }
        if (total % pageSize > 0) {
            return this.total / this.pageSize + 1;
        }
        return 1;
    }

    public int getOracleStartNum() {
        return this.oracleStartNum > 0 ? this.oracleStartNum : (this.currentPage - 1) * this.pageSize + 1;
    }

    public void setOracleStartNum(Integer oracleStartNum) {
        this.oracleStartNum = (oracleStartNum == null || oracleStartNum <= 0) ? 1 : oracleStartNum;
    }

    public int getOracleEndNum() {
        return this.oracleEndNum > 0 ? this.oracleEndNum : this.currentPage * this.pageSize;
    }

    public void setOracleEndNum(Integer oracleEndNum) {
        this.oracleEndNum = (oracleEndNum == null || oracleEndNum <= 0) ? 1 : oracleEndNum;
    }

    public int getMySqlStartNum() {
        return this.mySqlStartNum >= 0 ? this.mySqlStartNum : (this.currentPage - 1) * this.pageSize;
    }

    public void setMySQLStartNum(Integer mySQLStartNum) {
        this.mySqlStartNum = (mySQLStartNum == null || mySQLStartNum <= 0) ? 0 : mySQLStartNum;
    }

    public int getSqlServerStartNum() {
        return this.sqlServerStartNum > 0 ? this.sqlServerStartNum : (this.currentPage - 1) * this.pageSize + 1;
    }

    public void setSqlServerStartNum(Integer sqlServerStartNum) {
        this.sqlServerStartNum = (sqlServerStartNum == null || sqlServerStartNum <= 0) ? 1 : sqlServerStartNum;
    }

    public int getSqlServerEndNum() {
        return this.sqlServerEndNum > 0 ? this.sqlServerEndNum : this.currentPage * this.pageSize;
    }

    public void setSqlServerEndNum(Integer sqlServerEndNum) {
        this.sqlServerEndNum = (sqlServerEndNum == null || sqlServerEndNum <= 0) ? 1 : sqlServerEndNum;
    }

    @Override
    public Integer getLimitStart() {
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
    public void setLimitStart(Integer limitStart) {
        switch (this.dataBaseType) {
            case ORACLE:
                this.setOracleStartNum(limitStart);
                return;
            case MYSQL:
                this.setMySQLStartNum(limitStart);
                return;
            case SQLSERVER:
                this.setSqlServerStartNum(limitStart);
                return;
            default:
        }
    }

    @Override
    public Integer getLimitEnd() {
        switch (this.dataBaseType) {
            case ORACLE:
                return this.getOracleEndNum();
            case MYSQL:
                return this.getPageSize();
            case SQLSERVER:
                return this.getSqlServerEndNum();
            default:
                return 0;
        }
    }

    @Override
    public void setLimitEnd(Integer limitEnd) {
        switch (this.dataBaseType) {
            case ORACLE:
                this.setOracleEndNum(limitEnd);
                return;
            case MYSQL:
                this.setPageSize(limitEnd);
                return;
            case SQLSERVER:
                this.setSqlServerEndNum(limitEnd);
                return;
            default:
        }
    }

}
