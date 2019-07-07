package pub.avalon.beans;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Created by 白超 on 2019/7/7.
 */
public class LimitTest {

    @Test
    void TestMySqlPagination01() {
        Pagination pagination = new Pagination(DataBaseType.MYSQL, 1L, 10L);
        Assertions.assertEquals(DataBaseType.MYSQL, pagination.getDataBaseType());
        Assertions.assertEquals(0, pagination.getLimitStartNum().longValue());
        Assertions.assertEquals(10, pagination.getLimitEndNum().longValue());
        Assertions.assertEquals(1, pagination.getRowStartNum().longValue());
        Assertions.assertEquals(10, pagination.getRowEndNum().longValue());
        Assertions.assertEquals(0, pagination.getTotal());
        Assertions.assertEquals(1, pagination.getPageCount());
    }

    @Test
    void TestMySqlPagination02() {
        Pagination pagination = new Pagination(DataBaseType.MYSQL, null, null);
        Assertions.assertEquals(DataBaseType.MYSQL, pagination.getDataBaseType());
        Assertions.assertEquals(0, pagination.getLimitStartNum().longValue());
        Assertions.assertEquals(1, pagination.getLimitEndNum().longValue());
        Assertions.assertEquals(1, pagination.getRowStartNum().longValue());
        Assertions.assertEquals(1, pagination.getRowEndNum().longValue());
        Assertions.assertEquals(0, pagination.getTotal());
        Assertions.assertEquals(1, pagination.getPageCount());
    }

    @Test
    void TestMySqlPagination03() {
        Pagination pagination = new Pagination(DataBaseType.MYSQL, 10000L, 5L, 100L);
        Assertions.assertEquals(DataBaseType.MYSQL, pagination.getDataBaseType());
        Assertions.assertEquals(400, pagination.getLimitStartNum().longValue());
        Assertions.assertEquals(100, pagination.getLimitEndNum().longValue());
        Assertions.assertEquals(401, pagination.getRowStartNum().longValue());
        Assertions.assertEquals(500, pagination.getRowEndNum().longValue());
        Assertions.assertEquals(10000, pagination.getTotal());
        Assertions.assertEquals(100, pagination.getPageCount());
    }


    @Test
    void TestOraclePagination01() {
        Pagination pagination = new Pagination(DataBaseType.ORACLE, 1L, 10L);
        Assertions.assertEquals(DataBaseType.ORACLE, pagination.getDataBaseType());
        Assertions.assertEquals(1, pagination.getLimitStartNum().longValue());
        Assertions.assertEquals(10, pagination.getLimitEndNum().longValue());
        Assertions.assertEquals(1, pagination.getRowStartNum().longValue());
        Assertions.assertEquals(10, pagination.getRowEndNum().longValue());
        Assertions.assertEquals(0, pagination.getTotal());
        Assertions.assertEquals(1, pagination.getPageCount());
    }

    @Test
    void TestOraclePagination02() {
        Pagination pagination = new Pagination(DataBaseType.ORACLE, null, null);
        Assertions.assertEquals(DataBaseType.ORACLE, pagination.getDataBaseType());
        Assertions.assertEquals(1, pagination.getLimitStartNum().longValue());
        Assertions.assertEquals(1, pagination.getLimitEndNum().longValue());
        Assertions.assertEquals(1, pagination.getRowStartNum().longValue());
        Assertions.assertEquals(1, pagination.getRowEndNum().longValue());
        Assertions.assertEquals(0, pagination.getTotal());
        Assertions.assertEquals(1, pagination.getPageCount());
    }

    @Test
    void TestOraclePagination03() {
        Pagination pagination = new Pagination(DataBaseType.ORACLE, 10000L, 5L, 100L);
        Assertions.assertEquals(DataBaseType.ORACLE, pagination.getDataBaseType());
        Assertions.assertEquals(401, pagination.getLimitStartNum().longValue());
        Assertions.assertEquals(500, pagination.getLimitEndNum().longValue());
        Assertions.assertEquals(401, pagination.getRowStartNum().longValue());
        Assertions.assertEquals(500, pagination.getRowEndNum().longValue());
        Assertions.assertEquals(10000, pagination.getTotal());
        Assertions.assertEquals(100, pagination.getPageCount());
    }

    @Test
    void TestSqlServerPagination01() {
        Pagination pagination = new Pagination(DataBaseType.SQLSERVER, 1L, 10L);
        Assertions.assertEquals(DataBaseType.SQLSERVER, pagination.getDataBaseType());
        Assertions.assertEquals(1, pagination.getLimitStartNum().longValue());
        Assertions.assertEquals(10, pagination.getLimitEndNum().longValue());
        Assertions.assertEquals(1, pagination.getRowStartNum().longValue());
        Assertions.assertEquals(10, pagination.getRowEndNum().longValue());
        Assertions.assertEquals(0, pagination.getTotal());
        Assertions.assertEquals(1, pagination.getPageCount());
    }

    @Test
    void TestSqlServerPagination02() {
        Pagination pagination = new Pagination(DataBaseType.SQLSERVER, null, null);
        Assertions.assertEquals(DataBaseType.SQLSERVER, pagination.getDataBaseType());
        Assertions.assertEquals(1, pagination.getLimitStartNum().longValue());
        Assertions.assertEquals(1, pagination.getLimitEndNum().longValue());
        Assertions.assertEquals(1, pagination.getRowStartNum().longValue());
        Assertions.assertEquals(1, pagination.getRowEndNum().longValue());
        Assertions.assertEquals(0, pagination.getTotal());
        Assertions.assertEquals(1, pagination.getPageCount());
    }

    @Test
    void TestSqlServerPagination03() {
        Pagination pagination = new Pagination(DataBaseType.SQLSERVER, 10000L, 5L, 100L);
        Assertions.assertEquals(DataBaseType.SQLSERVER, pagination.getDataBaseType());
        Assertions.assertEquals(401, pagination.getLimitStartNum().longValue());
        Assertions.assertEquals(500, pagination.getLimitEndNum().longValue());
        Assertions.assertEquals(401, pagination.getRowStartNum().longValue());
        Assertions.assertEquals(500, pagination.getRowEndNum().longValue());
        Assertions.assertEquals(10000, pagination.getTotal());
        Assertions.assertEquals(100, pagination.getPageCount());
    }

}
