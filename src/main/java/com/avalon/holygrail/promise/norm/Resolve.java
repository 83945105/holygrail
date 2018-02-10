package com.avalon.holygrail.promise.norm;

/**
 * 完成
 * Created by 白超 on 2018/2/10.
 */
@FunctionalInterface
public interface Resolve<T> {

    void accept(T res);
}
