package pub.avalon.holygrail.response.utils;

import pub.avalon.holygrail.response.exception.NeedLoginException;
import pub.avalon.holygrail.response.exception.NoAuthorityException;
import pub.avalon.holygrail.response.exception.NotFoundException;
import pub.avalon.holygrail.response.exception.ResultException;

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
        throw new ResultException(0, ResultUtil.createFail(message));
    }

    /**
     * 抛出失败异常
     *
     * @param code    异常代号
     * @param message 提示信息
     * @throws ResultException
     */
    static void throwFailException(Integer code, String message) throws ResultException {
        throw new ResultException(code, ResultUtil.createFail(message));
    }

    /**
     * 抛出错误异常
     *
     * @param message 提示信息
     * @throws ResultException
     */
    static void throwErrorException(String message) throws ResultException {
        throw new ResultException(0, ResultUtil.createError(message));
    }

    /**
     * 抛出错误异常
     *
     * @param code    异常代号
     * @param message 提示信息
     * @throws ResultException
     */
    static void throwErrorException(Integer code, String message) throws ResultException {
        throw new ResultException(code, ResultUtil.createError(message));
    }

    /**
     * 抛出提示异常
     *
     * @param message 提示信息
     * @throws ResultException
     */
    static void throwInfoException(String message) throws ResultException {
        throw new ResultException(0, ResultUtil.createInfo(message));
    }

    /**
     * 抛出提示异常
     *
     * @param code    异常代号
     * @param message 提示信息
     * @throws ResultException
     */
    static void throwInfoException(Integer code, String message) throws ResultException {
        throw new ResultException(code, ResultUtil.createInfo(message));
    }

    /**
     * 抛出警告异常
     *
     * @param message 提示信息
     * @throws ResultException
     */
    static void throwWarnException(String message) throws ResultException {
        throw new ResultException(0, ResultUtil.createWarn(message));
    }

    /**
     * 抛出警告异常
     *
     * @param code    异常代号
     * @param message 提示信息
     * @throws ResultException
     */
    static void throwWarnException(Integer code, String message) throws ResultException {
        throw new ResultException(code, ResultUtil.createWarn(message));
    }

    /**
     * 抛出需要登录异常
     *
     * @param message 提示信息
     * @throws ResultException
     */
    static void throwNeedLoginException(String message) throws ResultException {
        throw new NeedLoginException(0, ResultUtil.createNeedLogin(message));
    }

    /**
     * 抛出需要登录异常
     *
     * @param code    异常代号
     * @param message 提示信息
     * @throws ResultException
     */
    static void throwNeedLoginException(Integer code, String message) throws ResultException {
        throw new NeedLoginException(code, ResultUtil.createNeedLogin(message));
    }

    /**
     * 抛出没有权限异常
     *
     * @param message 提示信息
     * @throws ResultException
     */
    static void throwNoAuthorityException(String message) throws ResultException {
        throw new NoAuthorityException(0, ResultUtil.createNoAuthority(message));
    }

    /**
     * 抛出没有权限异常
     *
     * @param code    异常代号
     * @param message 提示信息
     * @throws ResultException
     */
    static void throwNoAuthorityException(Integer code, String message) throws ResultException {
        throw new NoAuthorityException(code, ResultUtil.createNoAuthority(message));
    }

    /**
     * 抛出404异常
     *
     * @param message 提示信息
     * @throws ResultException
     */
    static void throwNotFoundException(String message) throws ResultException {
        throw new NotFoundException(0, ResultUtil.createNotFound(message));
    }

    /**
     * 抛出404异常
     *
     * @param code    异常代号
     * @param message 提示信息
     * @throws ResultException
     */
    static void throwNotFoundException(Integer code, String message) throws ResultException {
        throw new NotFoundException(code, ResultUtil.createNotFound("您要查找的页面不存在"));
    }

}
