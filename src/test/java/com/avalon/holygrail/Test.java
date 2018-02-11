package com.avalon.holygrail;

import com.avalon.holygrail.promise.bean.Promise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

/**
 * Created by 白超 on 2018-2-5.
 */
public class Test {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        new Promise<String, HashMap>((resolve, reject) -> {

            System.out.println("准备查询数据");
            Thread.sleep(3000);
            System.out.println("数据查询完毕");
            resolve.accept("OK");
        }).then(res -> {
            return res;
        }).then(res -> {
            System.out.println(res);
        }).Catch(err -> {

        });

        System.out.println("启动");
    }
}
