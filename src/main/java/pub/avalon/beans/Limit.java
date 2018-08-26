package pub.avalon.beans;

/**
 * 分页接口
 *
 * @author 白超
 */
public interface Limit {

    /**
     * 获取总数
     *
     * @return
     */
    Integer getTotal();

    /**
     * 获取当前页号
     *
     * @return
     */
    Integer getCurrentPage();

    /**
     * 获取每页显示数量
     *
     * @return
     */
    Integer getPageSize();

    /**
     * 获取总页数
     *
     * @return
     */
    Integer getPageCount();

}
