package com.avalon.holygrail.promise.norm;

/**
 * 失败-有返回值
 * Created by 白超 on 2018/2/10.
 */
@FunctionalInterface
public interface RejectA<T, R> extends Reject {

    R apply(T err) throws Exception;
}
