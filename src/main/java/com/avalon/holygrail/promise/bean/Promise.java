package com.avalon.holygrail.promise.bean;

import com.avalon.holygrail.promise.model.PromiseStatus;
import com.avalon.holygrail.promise.norm.PromiseRun;
import com.avalon.holygrail.promise.norm.Reject;
import com.avalon.holygrail.promise.norm.Resolve;

/**
 * 承诺模式
 * Created by 白超 on 2018/2/10.
 */
public class Promise<T, V> implements Runnable {

    private PromiseStatus promiseStatus;

    private PromiseRun<T, V> promiseRun;

    private T t;

    private Object v;

    private Throwable error;

    private Resolve<T> resolve = t -> {
        this.t = t;
        this.promiseStatus = PromiseStatus.RESOLVED;
    };

    private Reject<V> reject = v -> {
        this.v = v;
        this.promiseStatus = PromiseStatus.REJECTED;
    };

    public Promise(PromiseRun<T, V> promiseRun) {
        this.promiseRun = promiseRun;
        new Thread(this).start();
    }


    public Promise<T, V> then(Resolve<T> resolve) {
        if(this.promiseStatus == PromiseStatus.RESOLVED) {
            resolve.accept(this.t);
        }
        return this;
    }

    public Promise<T, V> Catch(Reject<V> reject) {
        if(this.promiseStatus == PromiseStatus.REJECTED) {
            reject.accept((V) this.v);
        }
        return this;
    }

    public static <T, V> Promise<T, V> all(Promise ... promises) {

        return null;
    }

    public static <T, V> Promise<T, V> race(Promise ... promises) {

        return null;
    }

    @Override
    public void run() {
        try {
            this.promiseRun.start(this.resolve, this.reject);
        } catch (Exception e) {
            this.error = e;
        }
    }
}
