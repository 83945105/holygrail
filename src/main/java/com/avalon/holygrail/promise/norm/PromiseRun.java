package com.avalon.holygrail.promise.norm;

/**
 * 承诺模式运行体
 * Created by 白超 on 2018/2/10.
 */
@FunctionalInterface
public interface PromiseRun<T, V> {

    /**
     * 开始
     *
     * @param resolve 用于设置成功结果
     * @param reject  用于设置失败结果
     * @throws Exception
     */
    void start(ResolveA<T, Object> resolve, RejectA<V, Object> reject) throws Exception;
}
