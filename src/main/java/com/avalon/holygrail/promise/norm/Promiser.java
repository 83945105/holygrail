package com.avalon.holygrail.promise.norm;

import java.util.concurrent.Callable;

/**
 * 承诺模式
 * Created by 白超 on 2018/2/23.
 */
public interface Promiser<V, E> extends Callable<V> {

    interface PromiseName {
        /**
         * 用于设置Promise的name
         * @param ownerName 所属Promise的name
         * @return 你要设置的name
         */
        String apply(String ownerName);
    }

    <P> Promiser<P, Object> then(ResolveA<V, P> resolve);

    <P> Promiser<P, Object> then(PromiseName nameHandler, ResolveA<V, P> resolve);

    Promiser<Object, Object> then(ResolveB<V> resolve);

    Promiser<Object, Object> then(PromiseName nameHandler, ResolveB<V> resolve);

    <P> Promiser<P, Object> Catch(RejectA<E, P> reject);

    <P> Promiser<P, Object> Catch(PromiseName nameHandler, RejectA<E, P> reject);

    Promiser<Object, Object> Catch(RejectB<E> reject);

    Promiser<Object, Object> Catch(PromiseName nameHandler, RejectB<E> reject);

    Promiser<V, E> start(PromiseRun<V, E> promiseRun);

}
