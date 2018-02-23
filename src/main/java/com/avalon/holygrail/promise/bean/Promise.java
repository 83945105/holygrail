package com.avalon.holygrail.promise.bean;

import com.avalon.holygrail.promise.exception.PromiseException;
import com.avalon.holygrail.promise.exception.RejectedException;
import com.avalon.holygrail.promise.model.PromiseStatus;
import com.avalon.holygrail.promise.norm.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.*;

/**
 * 承诺模式
 * 与ES6原生Promise用法及流程一致
 * 承诺模式对象创建后立即异步执行
 * new Promise((resolve, reject) -> {resolve.accept('成功') or reject.accept('失败')})
 * .then(res -> {...do something}) 用于获取resolve.accept设置的值,可以返回一个对象或者Promise给下一个.then
 * .then(res -> {...do something}) 连续调用该方法可以获取上一个.then返回的对象或者Promise成功后的结果
 * .Catch(err -> {...do something}) 用于捕获调用该方法之前的最后一个异常
 * Created by 白超 on 2018/2/10.
 */
public class Promise<T, V> implements Callable<T> {

    protected ExecutorService executorService;

    protected Future<T> future;

    protected PromiseStatus promiseStatus;

    protected PromiseRun<T, V> promiseRun;

    private T res;

    private Object err;

    private Boolean isReturn = false;//是否是返回的Promise

    protected ArrayList<CallBack> callBacks = new ArrayList<>();

    protected int waitForSetCallBackCount = 3;//等待设置回调的次数

    protected long waitForSetCallBackTime = 100;//等待设置回调的时间(毫秒)

    protected ResolveA<T> resolve = res -> {
        if (this.promiseStatus == PromiseStatus.PENDING && this.promiseStatus != PromiseStatus.REJECTED) {
            this.promiseStatus = PromiseStatus.RESOLVED;
            this.res = res;
        }
        return null;
    };

    protected RejectA<V> reject = err -> {
        if (this.promiseStatus == PromiseStatus.PENDING && this.promiseStatus != PromiseStatus.RESOLVED) {
            this.promiseStatus = PromiseStatus.REJECTED;
            this.err = err;
        }
        return null;
    };

    public Promise(PromiseRun<T, V> promiseRun) {
        this.promiseRun = promiseRun;
        this.executorService = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>()) {

            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                //开始执行前先将Promise状态改为运行中
                Promise.this.promiseStatus = PromiseStatus.PENDING;
                super.beforeExecute(t, r);
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                super.afterExecute(r, t);
                if (Promise.this.isReturn) {
                    return;
                }
                if (t == null && r instanceof Future<?>) {
                    try {
                        Future<?> future = (Future<?>) r;
                        if (future.isDone()) {
                            future.get();
                        }
                    } catch (CancellationException e) {
                        t = e;
                    } catch (ExecutionException e) {
                        t = e.getCause();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                if (t != null) {
                    t.printStackTrace();
                }
            }


        };
        this.future = this.executorService.submit(this);
    }


    public Promise<T, V> then(ResolveA<?> resolve) {
        if (resolve != null) {
            this.callBacks.add(resolve);
        }
        return this;
    }

    public Promise<T, V> then(ResolveB<?> resolve) {
        if (resolve != null) {
            this.callBacks.add(resolve);
        }
        return this;
    }

    public Promise<T, V> Catch(RejectA<?> reject) {
        if (reject != null) {
            this.callBacks.add(reject);
        }
        return this;
    }

    public Promise<T, V> Catch(RejectB<?> reject) {
        if (reject != null) {
            this.callBacks.add(reject);
        }
        return this;
    }

    public static Promise all(Promise... promises) {
        System.out.println("Promise All 启动");
        return new Promise<>((resolve, reject) -> {
            Object[] rs = new Object[promises.length];
            if (promises == null) {
                resolve.apply(rs);
            }
            for (int i = 0; i < promises.length; i++) {
                try {
                    rs[i] = promises[i].get();
                } catch (ExecutionException e) {
                    if (e.getCause() instanceof RejectedException) {
                        reject.apply(((RejectedException) e.getCause()).getError());
                    } else {
                        reject.apply(e.getCause());
                    }
                } catch (InterruptedException e) {
                    reject.apply(e);
                }
            }
            resolve.apply(rs);
        });
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Long startTime = System.currentTimeMillis();
        Promise.all(new Promise((resolve, reject) -> {
//            Thread.sleep(1000);
            resolve.apply(100);
        }), new Promise((resolve, reject) -> {
//            Thread.sleep(1000);
//            resolve.apply(200);
            reject.apply(1);
        })).then(res -> {
            System.out.println(Arrays.toString((Object[]) res));
            System.out.println("耗时:" + (System.currentTimeMillis() - startTime));
        }).Catch(err -> {
            System.out.println(err);
            System.out.println("耗时:" + (System.currentTimeMillis() - startTime));
        });
        System.out.println("启动");
    }

    public static <T, V> Promise<T, V> race(Promise... promises) {

        return null;
    }

    protected void doCallBacks(Object result, boolean rejected, int size) throws Exception {
        if (rejected && size == 0) {//失败了且无回调可以处理,抛出异常
            if (result instanceof Exception) {
                throw new PromiseException((Exception) result);
            }
            throw new RejectedException(result);
        }
        if (size > 0) {
            this.doCallBacks(result, rejected);
        }
    }

    protected void doCallBacks(Object result, boolean rejected) throws Exception {
        CallBack callBack;
        Promise promise;
        for (int i = 0; i < this.callBacks.size(); i++) {
            callBack = this.callBacks.get(i);
            if (rejected && !(callBack instanceof RejectA || callBack instanceof RejectB)) {
                continue;//如果是失败状态且当前回调不是处理失败的回调,略过
            } else if (!rejected && (callBack instanceof RejectA || callBack instanceof RejectB)) {
                continue;//如果不是失败状态且当前回调是处理失败的回调,略过
            }
            try {
                result = doCallBack(callBack, result);//使用给当前回调处理结果
            } catch (Exception e) {
                rejected = true;
                result = e;
                continue;//处理过程出异常了,寻找下一个回调来处理
            }
            rejected = false;//能走到这步说明异常已经处理完了
            if (result instanceof Promise) {//如果回调返回的是Promise对象
                promise = ((Promise) result);
                promise.isReturn = true;
                try {
                    promise.get();//等待Promise的结果
                } catch (Exception e) {
                    rejected = true;
                    result = e;
                    continue;
                }
                if (promise.callBacks.size() > 0) {
                    result = null;
                } else {
                    result = promise.res;
                }
            }
        }
        this.doCallBacks(result, rejected, 0);
    }

    protected Object doCallBack(CallBack callBack, Object result) throws Exception {
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

    //异步执行
    @Override
    public T call() throws Exception {
        System.out.println("Promise启动");
        //用来保证所有预先设置的回调函数成功设置
        int i = 0;
        for (; i < this.waitForSetCallBackCount && this.callBacks.size() == 0; i++) {
            Thread.sleep(this.waitForSetCallBackTime);
        }
        Object result = null;
        boolean rejected = false;
        try {
            this.promiseRun.start(this.resolve, this.reject);
        } catch (Exception e) {//发生异常
            //只有当Promise状态为运行中才处理异常,否则表示在出现异常前,已经执行了resolve或reject改变了Promise状态
            if (this.promiseStatus == PromiseStatus.PENDING) {
                this.promiseStatus = PromiseStatus.REJECTED;//先将Promise状态改为失败
                this.err = e;//记录
            }
        }
        if (this.promiseStatus == PromiseStatus.RESOLVED) {
            result = this.res;
            rejected = false;
        } else if (this.promiseStatus == PromiseStatus.REJECTED) {
            result = this.err;
            rejected = true;
        }
        try {
            this.doCallBacks(result, rejected, this.callBacks.size());
        } catch (Exception e) {
            throw e;//回调没有处理掉异常,抛出
        } finally {
            this.executorService.shutdown();
        }
        return this.res;
    }

    /**
     * 获取执行结果
     *
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public T get() throws ExecutionException, InterruptedException {
        return this.future.get();
    }

}
