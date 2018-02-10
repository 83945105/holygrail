package com.avalon.holygrail.promise.norm;

/**
 * Created by 白超 on 2018/2/10.
 */
@FunctionalInterface
public interface PromiseRun<T, V> {

    void start(Resolve<T> resolve, Reject<V> reject) throws Exception;
}
