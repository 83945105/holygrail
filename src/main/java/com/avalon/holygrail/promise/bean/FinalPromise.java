package com.avalon.holygrail.promise.bean;

import com.avalon.holygrail.promise.exception.PromiseException;
import com.avalon.holygrail.promise.exception.RejectedException;
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
 * .Catch(err -> {...do something}) 用于捕获调用该方法之前的最后一个异常
 * Created by 白超 on 2018/2/23.
 */
public final class FinalPromise<V, E> implements Promiser<V, E> {

    private String name;

    private PromiseStatus promiseStatus;

    private ExecutorService executorService;

    private Future<V> future;

    private PromiseRun<V, E> promiseRun;

    private V res;

    private E err;

    private FinalPromise owner;

    private ArrayList<FinalPromise> nextList = new ArrayList<>();

    private CallBack callBack;

    private ResolveA<V, Object> resolve = res -> {
        if (this.promiseStatus == PromiseStatus.PENDING && this.promiseStatus != PromiseStatus.REJECTED) {
            this.promiseStatus = PromiseStatus.RESOLVED;
            this.res = res;
        }
        return null;
    };

    private RejectA<E, Object> reject = err -> {
        if (this.promiseStatus == PromiseStatus.PENDING && this.promiseStatus != PromiseStatus.RESOLVED) {
            this.promiseStatus = PromiseStatus.REJECTED;
            this.err = err;
        }
        return null;
    };

    public FinalPromise() {
    }

    public FinalPromise(String name) {
        this.name = name;
    }

    public FinalPromise(FinalPromise owner, CallBack callBack) {
        this.owner = owner;
        this.callBack = callBack;
    }

    public FinalPromise(String name, FinalPromise owner, CallBack callBack) {
        this.name = name;
        this.owner = owner;
        this.callBack = callBack;
    }

    public FinalPromise(PromiseRun<V, E> promiseRun) {
        this.start(promiseRun);
    }

    public FinalPromise(String name, PromiseRun<V, E> promiseRun) {
        this.name = name;
        this.start(promiseRun);
    }

    @Override
    public <P> Promiser<P, Object> then(ResolveA<V, P> resolve) {
        FinalPromise<P, Object> next = new FinalPromise<>(this, resolve);
        this.nextList.add(next);
        return next;
    }

    @Override
    public <P> Promiser<P, Object> then(PromiseName nameHandler, ResolveA<V, P> resolve) {
        FinalPromise<P, Object> next = new FinalPromise<>(nameHandler.apply(this.name), this, resolve);
        this.nextList.add(next);
        return next;
    }

    @Override
    public Promiser<Object, Object> then(ResolveB<V> resolve) {
        FinalPromise<Object, Object> next = new FinalPromise<>(this, resolve);
        this.nextList.add(next);
        return next;
    }

    @Override
    public Promiser<Object, Object> then(PromiseName nameHandler, ResolveB<V> resolve) {
        FinalPromise<Object, Object> next = new FinalPromise<>(nameHandler.apply(this.name), this, resolve);
        this.nextList.add(next);
        return next;
    }

    @Override
    public <P> Promiser<P, Object> Catch(RejectA<E, P> reject) {
        FinalPromise<P, Object> next = new FinalPromise<>(this, reject);
        this.nextList.add(next);
        return next;
    }

    @Override
    public <P> Promiser<P, Object> Catch(PromiseName nameHandler, RejectA<E, P> reject) {
        FinalPromise<P, Object> next = new FinalPromise<>(nameHandler.apply(this.name), this, reject);
        this.nextList.add(next);
        return next;
    }

    @Override
    public Promiser<Object, Object> Catch(RejectB<E> reject) {
        FinalPromise<Object, Object> next = new FinalPromise<>(this, reject);
        this.nextList.add(next);
        return next;
    }

    @Override
    public Promiser<Object, Object> Catch(PromiseName nameHandler, RejectB<E> reject) {
        FinalPromise<Object, Object> next = new FinalPromise<>(nameHandler.apply(this.name), this, reject);
        this.nextList.add(next);
        return next;
    }

    @Override
    public Promiser<V, E> start(PromiseRun<V, E> promiseRun) {
        this.promiseRun = promiseRun;
        this.executorService = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>()) {

            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                //开始执行前先将Promise状态改为运行中
                FinalPromise.this.promiseStatus = PromiseStatus.PENDING;
                super.beforeExecute(t, r);
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                super.afterExecute(r, t);
                if (t == null && r instanceof Future<?>) {
                    try {
                        if(FinalPromise.this.res instanceof FinalPromise) {
                            FinalPromise.this.res = (V) ((FinalPromise) FinalPromise.this.res).get();
                        }
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
                switch (FinalPromise.this.promiseStatus) {
                    case PENDING://运行中,表示没有使用resolve和reject
                        if (t != null) {//报错了
                            try {
                                FinalPromise.this.doNextList(true, t);
                            } catch (PromiseException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case RESOLVED://成功,使用了resolve
                        try {
                            FinalPromise.this.doNextList(false, FinalPromise.this.res);
                        } catch (PromiseException e) {
                            e.printStackTrace();
                        }
                        break;

                    case REJECTED://失败,使用了reject
                        try {
                            FinalPromise.this.doNextList(true, FinalPromise.this.err);
                        } catch (PromiseException e) {
                            e.printStackTrace();
                        }
                        break;
                }
                FinalPromise.this.executorService.shutdown();
                System.out.println("Promise:" + FinalPromise.this.name + " => 结束");
            }


        };
        this.future = this.executorService.submit(this);
        return this;
    }

    @Override
    public V get() throws ExecutionException, InterruptedException {
        if (this.owner != null) {
            this.owner.get();
        }
        return this.future.get();
    }

    /**
     * 处理兄弟级别回调
     * 所谓兄弟级别,一般情况链式调用只有一个,如果有多个,同时执行
     *
     * @param rejected
     * @param param
     * @throws PromiseException
     */
    private void doNextList(boolean rejected, Object param) throws PromiseException {
        if (rejected && this.nextList.size() == 0) {//有错误,但是没有可以处理的回调,抛异常
            if (param instanceof Exception) {
                throw new PromiseException("未找到Catch处理发生的错误", (Exception) param);
            }
            throw new RejectedException("未找到Catch处理发生的错误", param);
        }
        for (int i = 0; i < this.nextList.size(); i++) {
            this.nextList.get(i).doCallBack(rejected, param);
        }
    }

    /**
     * 处理Promise的回调
     * 用于将Promise的CallBack放入Promise的执行体中异步执行
     *
     * @param param
     */
    private void doCallBack(boolean rejected, Object param) {
        this.start((resolve, reject) -> {
            if (callBack instanceof Resolve) {
                if (rejected) {
                    reject.apply((E) param);
                    return;
                }
                if (callBack instanceof ResolveA) {
                    resolve.apply((V) ((ResolveA) callBack).apply(param));
                    return;
                }
                if (callBack instanceof ResolveB) {
                    ((ResolveB) callBack).accept(param);
                    resolve.apply(null);
                    return;
                }
            }
            if (callBack instanceof Reject) {
                if (!rejected) {
                    resolve.apply((V) param);
                    return;
                }
                if (callBack instanceof RejectA) {
                    resolve.apply((V) ((RejectA) callBack).apply(param));
                    return;
                }
                if (callBack instanceof RejectB) {
                    ((RejectB) callBack).accept(param);
                    resolve.apply(null);
                    return;
                }
            }
        });
    }

    @Override
    public V call() throws Exception {
        if (this.name == null) {
            if (this.owner != null) {
                this.name = this.owner.name + " > " + Thread.currentThread().getName();
            } else {
                this.name = Thread.currentThread().getName();
            }
        }
        System.out.println("Promise:" + this.name + " => 启动");
        this.promiseRun.start(this.resolve, this.reject);
        return this.res;
    }


}
