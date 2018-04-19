package com.avalon.holygrail.ss.norm;

/**
 * MySql分页
 * Created by 白超 on 2018/2/2.
 */
public interface MySqlLimit extends Limit {

    /**
     * 获取分页开始号
     */
    Integer getLimitStart();

    /**
     * 获取分页结束号
     */
    int getLimitEnd();

    @Override
    default String getSql() {
        return "limit " + this.getLimitStart() == null ? "0" : this.getLimitStart() + "," + this.getLimitEnd();
    }
}
