package com.avalon.holygrail.promise.norm;

/**
 * 失败-无返回值
 * Created by 白超 on 2018/2/10.
 */
@FunctionalInterface
public interface RejectB<T> extends Reject {

    void accept(T err) throws Exception;
}
