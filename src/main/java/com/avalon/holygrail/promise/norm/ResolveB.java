package com.avalon.holygrail.promise.norm;

/**
 * 完成-不需要返回值
 * Created by 白超 on 2018/2/10.
 */
@FunctionalInterface
public interface ResolveB<T> extends CallBack {

    void accept(T res);
}
