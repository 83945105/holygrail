package com.avalon.holygrail.promise.norm;

/**
 * 完成-需要返回值
 * Created by 白超 on 2018/2/10.
 */
@FunctionalInterface
public interface ResolveA<T, R> extends Resolve {

    R apply(T res) throws Exception;
}
