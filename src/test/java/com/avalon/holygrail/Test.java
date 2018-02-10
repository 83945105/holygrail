package com.avalon.holygrail;

import com.avalon.holygrail.promise.bean.Promise;

/**
 * Created by 白超 on 2018-2-5.
 */
public class Test {

    public static void main(String[] args) {
        new Promise<String, String>((resolve, reject) -> {
//            throw new Exception("异常");
            Thread.sleep(3000);
            resolve.accept("成功");
            reject.accept("失败");
        }).then(res -> {
            System.out.println(res);
        }).Catch(err -> {
            System.out.println(err);
        });
        System.out.println("666");

        Promise.all(new Promise<String, Integer>((resolve, reject) -> {

        })).then(res -> {

        }).Catch(err -> {

        });
    }
}
