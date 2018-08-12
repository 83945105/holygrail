package com.avalon.holygrail.ss.util;

import com.avalon.holygrail.ss.exception.ResultException;
import com.avalon.holygrail.ss.norm.ResultInfo;

/**
 * 异常工具类
 *
 * @author 白超
 * @date 2018-1-20
 */
public interface ExceptionUtil {

    /**
     * 抛出失败异常
     *
     * @param message 提示信息
     * @throws ResultException
     */
    static void throwFailException(String message) throws ResultException {
        throw ResultUtil.createResultException(ResultUtil.createFail(message));
    }

    /**
     * 抛出失败异常
     *
     * @param message          提示信息
     * @param exceptionMessage 异常信息
     * @throws ResultException
     */
    static void throwFailException(String message, String exceptionMessage) throws ResultException {
        throw ResultUtil.createResultException(ResultUtil.createFail(message, exceptionMessage));
    }

    /**
     * 抛出失败异常
     *
     * @param messageCode 异常代码
     * @throws ResultException
     */
    static void throwFailException(int messageCode) throws ResultException {
        throw ResultUtil.createResultException(ResultUtil.createFail(messageCode, null));
    }

    /**
     * 抛出失败异常
     *
     * @param messageCode 异常代码
     * @param params      参数
     * @throws ResultException
     */
    static void throwFailException(int messageCode, Object[] params) throws ResultException {
        throw ResultUtil.createResultException(ResultUtil.createFail(messageCode, params));
    }

    /**
     * 抛出错误异常
     *
     * @param message 提示信息
     * @throws ResultException
     */
    static void throwErrorException(String message) throws ResultException {
        throw ResultUtil.createResultException(ResultUtil.createError(message));
    }

    /**
     * 抛出错误异常
     *
     * @param messageCode 异常代码
     * @throws ResultException
     */
    static void throwErrorException(int messageCode) throws ResultException {
        throw ResultUtil.createResultException(ResultUtil.createError(messageCode, null));
    }

    /**
     * 抛出错误异常
     *
     * @param messageCode 异常代码
     * @param params      参数
     * @throws ResultException
     */
    static void throwErrorException(int messageCode, Object[] params) throws ResultException {
        throw ResultUtil.createResultException(ResultUtil.createError(messageCode, params));
    }

    /**
     * 抛出提示异常
     *
     * @param message 提示信息
     * @throws ResultException
     */
    static void throwInfoException(String message) throws ResultException {
        throw ResultUtil.createResultException(ResultUtil.createInfo(message));
    }

    /**
     * 抛出警告异常
     *
     * @param message 提示信息
     * @throws ResultException
     */
    static void throwWarnException(String message) throws ResultException {
        throw ResultUtil.createResultException(ResultUtil.createWarn(message));
    }

    /**
     * 抛出需要登录异常
     *
     * @param message 提示信息
     * @throws ResultException
     */
    static void throwNeedLoginException(String message) throws ResultException {
        throw ResultUtil.createNeedLoginException(ResultUtil.createNeedLogin(message));
    }

    /**
     * 抛出需要登录异常
     *
     * @param messageCode 异常代码
     * @throws ResultException
     */
    static void throwNeedLoginException(int messageCode) throws ResultException {
        throw ResultUtil.createNeedLoginException(ResultUtil.createNeedLogin(messageCode, null));
    }

    /**
     * 抛出需要登录异常
     *
     * @param messageCode 异常代码
     * @param params      参数
     * @throws ResultException
     */
    static void throwNeedLoginException(int messageCode, Object[] params) throws ResultException {
        throw ResultUtil.createNeedLoginException(ResultUtil.createNeedLogin(messageCode, params));
    }

    /**
     * 抛出没有权限异常
     *
     * @param message 提示信息
     * @throws ResultException
     */
    static void throwNoAuthorityException(String message) throws ResultException {
        throw ResultUtil.createNoAuthorityException(ResultUtil.createNoAuthority(message));
    }

    /**
     * 抛出没有权限异常
     *
     * @param messageCode 异常代码
     * @throws ResultException
     */
    static void throwNoAuthorityException(int messageCode) throws ResultException {
        throw ResultUtil.createNoAuthorityException(ResultUtil.createNoAuthority(messageCode, null));
    }

    /**
     * 抛出没有权限异常
     *
     * @param messageCode 异常代码
     * @param params      参数
     * @throws ResultException
     */
    static void throwNoAuthorityException(int messageCode, Object[] params) throws ResultException {
        throw ResultUtil.createNoAuthorityException(ResultUtil.createNoAuthority(messageCode, params));
    }

    /**
     * 抛出404异常
     *
     * @throws ResultException
     */
    static void throwNotFoundException() throws ResultException {
        throw ResultUtil.createNotFoundException(ResultUtil.createNotFound("您要查找的页面不存在"));
    }

    /**
     * 抛出404异常
     *
     * @param message 提示信息
     * @throws ResultException
     */
    static void throwNotFoundException(String message) throws ResultException {
        throw ResultUtil.createNotFoundException(ResultUtil.createNotFound(message));
    }

    /**
     * 抛出404异常
     *
     * @param messageCode 异常代码
     * @throws ResultException
     */
    static void throwNotFoundException(int messageCode) throws ResultException {
        throw ResultUtil.createNotFoundException(ResultUtil.createNotFound(messageCode, null));
    }

    /**
     * 抛出404异常
     *
     * @param messageCode 异常代码
     * @param params      参数
     * @throws ResultException
     */
    static void throwNotFoundException(int messageCode, Object[] params) throws ResultException {
        throw ResultUtil.createNotFoundException(ResultUtil.createNotFound(messageCode, params));
    }

    /**
     * 抛出ResultException异常
     *
     * @param resultInfo 结果集
     */
    static void throwResultException(ResultInfo resultInfo) throws ResultException {
        throw ResultUtil.createResultException(resultInfo);
    }
}
