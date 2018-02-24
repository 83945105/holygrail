package com.avalon.holygrail.promise.norm;

import java.util.concurrent.Callable;

/**
 * 承诺模式
 * Created by 白超 on 2018/2/23.
 */
public interface Promiser<V, E> extends Callable<V> {

    <P> Promiser<P, Object> then(ResolveA<V, P> resolve);

    Promiser<Object, Object> then(ResolveB<V> resolve);

    <P> Promiser<P, Object> Catch(RejectA<E, P> reject);

    Promiser<Object, Object> Catch(RejectB<E> reject);

    Promiser<V, E> start(PromiseRun<V, E> promiseRun);

}
