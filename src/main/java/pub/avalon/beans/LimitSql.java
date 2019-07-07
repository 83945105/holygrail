package pub.avalon.beans;

/**
 * 分页操作
 *
 * @author 白超
 * @date 2018/8/24
 */
public interface LimitSql extends Limit {

    /**
     * 获取分页开始号<br/>
     * 用于分页Sql时的起始号<br/>
     * 通常情况: MySql = (currentPage - 1) * pageSize | Oracle = (currentPage - 1) * pageSize + 1 | SqlServer = (currentPage - 1) * pageSize + 1
     *
     * @return {@link java.lang.Long}
     */
    Long getLimitStartNum();

    /**
     * 获取分页结束号<br/>
     * 用于分页Sql时的结束号<br/>
     * 通常情况: MySql = pageSize | Oracle = currentPage * pageSize | SqlServer = currentPage * pageSize
     *
     * @return {@link java.lang.Long}
     */
    Long getLimitEndNum();

    /**
     * 获取分页数据的起始行号<br/>
     * 从1号开始
     *
     * @return {@link java.lang.Long}
     */
    Long getRowStartNum();

    /**
     * 获取分页数据的结束行号<br/>
     * 从1号开始
     *
     * @return {@link java.lang.Long}
     */
    Long getRowEndNum();

}
