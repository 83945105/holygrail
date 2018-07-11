package com.avalon.holygrail.ss.norm;

/**
 * 分页接口
 */
public interface Limit {

    /**
     * 获取总数
     *
     * @return
     */
    int getTotal();

    /**
     * 获取当前页号
     *
     * @return
     */
    int getCurrentPage();

    /**
     * 获取每页显示数量
     *
     * @return
     */
    int getPageSize();

    /**
     * 获取总页数
     *
     * @return
     */
    int getPageCount();

}
