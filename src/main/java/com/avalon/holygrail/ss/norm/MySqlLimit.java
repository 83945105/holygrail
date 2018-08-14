package com.avalon.holygrail.ss.norm;

/**
 * MySql分页
 *
 * @author 白超
 * @date 2018/2/2
 */
public interface MySqlLimit extends Limit {

    /**
     * 获取分页开始号
     *
     * @return 分页开始好
     */
    Integer getLimitStart();

    /**
     * 获取分页结束号
     *
     * @return 分页结束号
     */
    int getLimitEnd();

}
