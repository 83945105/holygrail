package pub.avalon.beans;

/**
 * 分页操作
 *
 * @author 白超
 * @date 2018/8/24
 */
public interface LimitHandler extends Limit {

    /**
     * 获取分页开始号
     *
     * @return 分页开始好
     */
    Integer getLimitStart();

    /**
     * 设置分页开始号
     *
     * @param limitStart 分页开始号
     */
    void setLimitStart(int limitStart);

    /**
     * 获取分页结束号
     *
     * @return 分页结束号
     */
    Integer getLimitEnd();

    /**
     * 设置分页结束号
     *
     * @param limitEnd 分页结束号
     */
    void setLimitEnd(int limitEnd);

}
