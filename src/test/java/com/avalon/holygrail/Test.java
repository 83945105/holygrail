package com.avalon.holygrail;

import com.avalon.holygrail.promise.bean.Promise;

import java.util.concurrent.*;

/**
 * Created by 白超 on 2018-2-5.
 */
public class Test {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Promise p = new Promise<String, String>((resolve, reject) -> {
//            throw new Exception("异常");
            Thread.sleep(1000);
            resolve.accept("成功");
            reject.accept("失败");
        }).then(res -> {
            System.out.println(res);
        }).then(res -> {
            System.out.println(res);
        }).Catch(err -> {
            System.out.println(err);
        }).then(res -> {
            System.out.println(res);
            return "666";
        }).then(res -> {
            System.out.println(res);
            return new Promise<Boolean, String>((resolve, reject) -> {
                Thread.sleep(5000);
                resolve.accept(true);
            });
        }).then(res -> {
            System.out.println(res);
        }).Catch(err -> {
            System.out.println(err);
        });

        System.out.println("启动");

        System.out.println(p.get());
    }
}
