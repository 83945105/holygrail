package pub.avalon.beans;

import java.util.function.Function;

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
     * 设置总数
     *
     * @param total
     */
    void setTotal(Integer total);

    /**
     * 获取当前页号
     *
     * @return
     */
    Integer getCurrentPage();

    /**
     * 设置当前页号
     *
     * @param currentPage
     */
    void setCurrentPage(Integer currentPage);

    /**
     * 获取每页显示数量
     *
     * @return
     */
    Integer getPageSize();

    /**
     * 设置每页显示数量
     *
     * @param pageSize
     */
    void setPageSize(Integer pageSize);

    /**
     * 获取总页数
     *
     * @return
     */
    Integer getPageCount();

    /**
     * 执行分页
     *
     * @param limit    分页对象
     * @param callback 回调函数,返回true继续执行下一页
     */
    static <L extends Limit> void execute(L limit, Function<L, Boolean> callback) {
        if (!callback.apply(limit) || limit.getCurrentPage() >= limit.getPageCount()) {
            // 如果用户返回false 或者 已经是最后一页了, 终止
            return;
        }
        // 继续执行下一页
        limit.setCurrentPage(limit.getCurrentPage() + 1);
        execute(limit, callback);
    }

}
