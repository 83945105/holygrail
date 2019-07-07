package pub.avalon.factory;

import pub.avalon.beans.DataBaseType;
import pub.avalon.beans.Pagination;

/**
 * JavaBean工厂
 *
 * @author 白超
 * @date 2018/8/25
 */
public final class BeanFactory {

    private DataBaseType dataBaseType;

    public BeanFactory(DataBaseType dataBaseType) {
        this.dataBaseType = dataBaseType;
    }

    public Pagination newPagination() {
        return new Pagination(this.dataBaseType);
    }

    public Pagination newPagination(long currentPage, long pageSize) {
        return new Pagination(this.dataBaseType, currentPage, pageSize);
    }

    public Pagination newPagination(long total, long currentPage, long pageSize) {
        return new Pagination(this.dataBaseType, total, currentPage, pageSize);
    }

    public DataBaseType getDataBaseType() {
        return dataBaseType;
    }

    public void setDataBaseType(DataBaseType dataBaseType) {
        this.dataBaseType = dataBaseType;
    }
}
