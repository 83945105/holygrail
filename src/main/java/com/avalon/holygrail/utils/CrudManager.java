package com.avalon.holygrail.utils;

import com.avalon.holygrail.ss.util.ExceptionUtil;

import java.util.function.Function;

/**
 * 增删改查管理
 *
 * @author 白超
 * @date 2018/6/13
 */
public interface CrudManager<T> {

    String INSERT_FAIL_MESSAGE = "新增数据失败";
    String INSERT_ERROR_MESSAGE = "新增数据错误";
    String UPDATE_FAIL_MESSAGE = "更新数据失败";
    String UPDATE_ERROR_MESSAGE = "更新数据错误";
    String DELETE_FAIL_MESSAGE = "删除数据失败";
    String DELETE_ERROR_MESSAGE = "删除数据错误";

    /**
     * 新增一条数据
     *
     * @param method 方法
     * @throws Exception
     */
    default void insertOne(Function<T, Integer> method) throws Exception {
        CrudManager.insertOne((T) this, method);
    }

    /**
     * 新增一条数据
     *
     * @param record 数据
     * @param method 方法
     * @param <T>
     * @throws Exception
     */
    static <T> void insertOne(T record, Function<T, Integer> method) throws Exception {
        int count = 0;
        try {
            count = method.apply(record);
        } catch (Exception e) {
            e.printStackTrace();
            ExceptionUtil.throwErrorException(INSERT_ERROR_MESSAGE);
        }
        if (count != 1) {
            ExceptionUtil.throwFailException(INSERT_FAIL_MESSAGE);
        }
    }

    /**
     * 更新一条数据
     *
     * @param method 方法
     * @throws Exception
     */
    default void updateOne(Function<T, Integer> method) throws Exception {
        CrudManager.updateOne((T) this, method);
    }

    /**
     * 更新一条数据
     *
     * @param record 数据
     * @param method 方法
     * @param <T>
     * @throws Exception
     */
    static <T> void updateOne(T record, Function<T, Integer> method) throws Exception {
        int count = 0;
        try {
            count = method.apply(record);
        } catch (Exception e) {
            e.printStackTrace();
            ExceptionUtil.throwErrorException(UPDATE_ERROR_MESSAGE);
        }
        if (count != 1) {
            ExceptionUtil.throwFailException(UPDATE_FAIL_MESSAGE);
        }
    }

    /**
     * 删除一条数据
     *
     * @param method 方法
     * @throws Exception
     */
    default void deleteOne(Function<T, Integer> method) throws Exception {
        CrudManager.deleteOne((T) this, method);
    }

    /**
     * 删除一条数据
     *
     * @param record 数据
     * @param method 方法
     * @param <T>
     * @throws Exception
     */
    static <T> void deleteOne(T record, Function<T, Integer> method) throws Exception {
        int count = 0;
        try {
            count = method.apply(record);
        } catch (Exception e) {
            e.printStackTrace();
            ExceptionUtil.throwErrorException(DELETE_ERROR_MESSAGE);
        }
        if (count != 1) {
            ExceptionUtil.throwFailException(DELETE_FAIL_MESSAGE);
        }
    }

}
