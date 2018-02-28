package com.avalon.holygrail.promise.bean;

import com.avalon.holygrail.promise.norm.Promiser;

import java.util.concurrent.ExecutionException;

/**
 * Created by 白超 on 2018/2/24.
 */
public class Test {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        method1();
    }

    public static void method1() {
        Promiser p = new Promise<>("起始线程", (resolve, reject) -> {
            Thread.sleep(1000);
            resolve.apply(123);
//            reject.apply(666);
//            int a = 1 / 0;
        }).then(ownerName -> ownerName + "> 起始线程1", res -> {
            System.out.println(res);
            Thread.sleep(1000);
            return 222;
        }).then(ownerName -> ownerName + "> 起始线程2", res -> {
            return new Promise("返回的Promise", (resolve, reject) -> {
                int i = 0;
                while (i++ < 3) {
                    Thread.sleep(1000);
                    System.out.println("~~~~:" + i);
                }
                resolve.apply(99999);
            }).then(ownerName -> "特殊线程", res1 -> {
                System.out.println("98797897897979");
                return 101010;
            });
        }).Catch(err -> {
            ((Exception) err).printStackTrace();
            Thread.sleep(1000);
            System.out.println(err);
            return 333;
        }).Catch(err -> {
            System.out.println("应该被无视的Catch");
        }).then(res -> {
            System.out.println(res);
            return 444;
        }).then(res -> {
            System.out.println(res);
            return 555;
        });
//        System.out.println(p.get());
    }

    public static void method2() throws ExecutionException, InterruptedException {
        Promise p = new Promise<>((resolve, reject) -> {
            Thread.sleep(3000);
        });
        System.out.println("11111111111111");
        System.out.println(p.get());
        System.out.println("22222222222222");
    }
}
