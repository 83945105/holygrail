package com.avalon.holygrail.promise.norm;

/**
 * Created by 白超 on 2018/2/10.
 */
@FunctionalInterface
public interface PromiseRun<T, V> {

    void start(ResolveA<T> resolve, RejectA<V> reject) throws Exception;
}
