package com.avalon.holygrail;

import com.avalon.holygrail.excel.exception.ExcelException;
import com.avalon.holygrail.promise.bean.Promise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

/**
 * Created by 白超 on 2018-2-5.
 */
public class Test {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        method2();
    }

    public static void method2() {

        new Promise<>((resolve, reject) -> {
            int a = 1 / 0;
        });
    }

    public static void method1() {
        Promise promise = new Promise<String, String>((resolve, reject) -> {
            resolve.apply("666");
//            int a = 1 / 0;
            reject.apply("777");
//            throw new Exception("233");
        }).then(res -> {
            System.out.println(res);
            return new Promise<>((resolve, reject) -> {
                Thread.sleep(1000);
                reject.apply("5555");
//                resolve.apply("666666666");
            }).Catch(err -> {
                int a = 1/0;
            });
        }).then(res -> {
            System.out.println(res);
        }).Catch(err -> {
            System.out.println(err);
            return "233";
        }).Catch(err -> {
            System.out.println(err);
        }).then(res -> {
            System.out.println(res);
        });

        System.out.println("启动");
    }
}
