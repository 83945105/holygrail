package com.avalon.holygrail.promise.bean;

import com.avalon.holygrail.promise.model.PromiseStatus;
import com.avalon.holygrail.promise.norm.*;

import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * 承诺模式
 * 与ES6原生Promise用法及流程一致
 * 承诺模式对象创建后立即异步执行
 * new Promise((resolve, reject) -> {resolve.accept('成功') or reject.accept('失败')})
 * .then(res -> {...do something}) 用于获取resolve.accept设置的值,可以返回一个对象或者Promise给下一个.then
 * .then(res -> {...do something}) 连续调用该方法可以获取上一个.then返回的对象或者Promise成功后的结果
 * .Catch(err -> {...do something}) 用于捕获调用该方法之前的异常
 * Created by 白超 on 2018/2/10.
 */
public class Promise<T, V> implements Callable<T> {

    private ExecutorService executorService;

    private Future<T> future;

    private PromiseStatus promiseStatus;

    private PromiseRun<T, V> promiseRun;

    private T res;

    private Object err;

    private ArrayList<CallBack> callBacks = new ArrayList<>();

    private ResolveB<T> resolve = res -> {
        if (this.promiseStatus == PromiseStatus.PENDING && this.promiseStatus != PromiseStatus.REJECTED) {
            this.promiseStatus = PromiseStatus.RESOLVED;
            this.res = res;
        }
    };

    private RejectB<V> reject = err -> {
        if (this.promiseStatus == PromiseStatus.PENDING && this.promiseStatus != PromiseStatus.RESOLVED) {
            this.promiseStatus = PromiseStatus.REJECTED;
            this.err = err;
        }
    };

    public Promise(PromiseRun<T, V> promiseRun) {
        this.promiseRun = promiseRun;
        this.executorService = Executors.newSingleThreadExecutor();
        this.future = this.executorService.submit(this);
    }


    public Promise<T, V> then(ResolveA resolve) {
        this.callBacks.add(resolve);
        return this;
    }

    public Promise<T, V> then(ResolveB resolve) {
        this.callBacks.add(resolve);
        return this;
    }

    public Promise<T, V> Catch(RejectA reject) {
        this.callBacks.add(reject);
        return this;
    }

    public Promise<T, V> Catch(RejectB reject) {
        this.callBacks.add(reject);
        return this;
    }

    public static <T, V> Promise<T, V> all(Promise... promises) {

        return null;
    }

    public static <T, V> Promise<T, V> race(Promise... promises) {

        return null;
    }

    private void doCallBacks(Object result, boolean rejected) {
        CallBack callBack;
        Promise promise;
        for (int i = 0; i < this.callBacks.size(); i++) {
            callBack = this.callBacks.get(i);
            if (callBack == null) {
                continue;
            }
            if (rejected && !(callBack instanceof RejectA || callBack instanceof RejectB)) {
                continue;
            } else if (!rejected && (callBack instanceof RejectA || callBack instanceof RejectB)) {
                continue;
            }
            try {
                result = doCallBack(callBack, result);
            } catch (Exception e) {
                rejected = true;
                result = e;
                continue;
            }
            rejected = false;
            if (result instanceof Promise) {
                promise = ((Promise) result);
                if (promise.callBacks.size() > 0) {
                    continue;
                }
                try {
                    result = promise.get();
                } catch (Exception e) {
                    rejected = true;
                    result = e;
                    continue;
                }
                if (promise.promiseStatus == PromiseStatus.RESOLVED) {
                    continue;
                } else if (promise.promiseStatus == PromiseStatus.REJECTED) {
                    result = promise.err;
                }
            }
        }
    }

    private Object doCallBack(CallBack callBack, Object result) throws Exception {
        if (callBack instanceof ResolveA) {
            result = ((ResolveA) callBack).apply(result);
        } else if (callBack instanceof ResolveB) {
            ((ResolveB) callBack).accept(result);
            result = null;
        } else if (callBack instanceof RejectA) {
            result = ((RejectA) callBack).apply(result);
        } else if (callBack instanceof RejectB) {
            ((RejectB) callBack).accept(result);
            result = null;
        }
        return result;
    }

    @Override
    public T call() throws Exception {
        this.promiseStatus = PromiseStatus.PENDING;
        try {
            this.promiseRun.start(this.resolve, this.reject);
        } catch (Exception e) {
            this.promiseStatus = PromiseStatus.REJECTED;
            this.err = e;
            this.doCallBacks(this.err, true);
            return this.res;
        }
        if (this.promiseStatus == PromiseStatus.RESOLVED) {
            this.doCallBacks(this.res, false);
        } else {
            this.doCallBacks(this.err, true);
        }
        this.executorService.shutdown();
        return this.res;
    }

    public T get() throws ExecutionException, InterruptedException {
        return this.future.get();
    }
}
