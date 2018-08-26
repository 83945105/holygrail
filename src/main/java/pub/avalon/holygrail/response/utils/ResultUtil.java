package pub.avalon.holygrail.response.utils;

import pub.avalon.holygrail.response.config.ResultConf;
import pub.avalon.holygrail.response.exception.NotFoundException;
import pub.avalon.holygrail.response.exception.NeedLoginException;
import pub.avalon.holygrail.response.exception.NoAuthorityException;
import pub.avalon.holygrail.response.exception.ResultException;
import pub.avalon.holygrail.response.beans.ResultCode;
import pub.avalon.holygrail.response.beans.ResultInfoRealization;
import pub.avalon.holygrail.response.beans.ResultInfo;

/**
 * 系统结果工具类
 *
 * @author 白超
 */
public class ResultUtil {

    /**
     * 创建失败结果集
     *
     * @param messageCode 提示信息key
     * @param params      格式化参数
     */
    public static ResultInfo createFail(int messageCode, Object[] params) {
        String message = params == null ? ResourceUtil.getValue(ResultConf.MESSAGE, messageCode + "")
                : ResourceUtil.getValue(ResultConf.MESSAGE, messageCode + "", params);
        return new ResultInfoRealization(ResultCode.FAIL, messageCode, message);
    }

    /**
     * 创建失败结果集
     *
     * @param message 提示信息
     */
    public static ResultInfo createFail(String message) {
        return new ResultInfoRealization(ResultCode.FAIL, message);
    }

    /**
     * 创建失败结果集
     *
     * @param message          提示信息
     * @param exceptionMessage 异常信息
     */
    public static ResultInfo createFail(String message, String exceptionMessage) {
        return new ResultInfoRealization(ResultCode.FAIL, message, exceptionMessage);
    }

    /**
     * 创建成功结果集
     *
     * @param messageCode 提示信息key
     * @param params      格式化参数
     */
    public static ResultInfo createSuccess(int messageCode, Object[] params) {
        String message = params == null ? ResourceUtil.getValue(ResultConf.MESSAGE, messageCode + "")
                : ResourceUtil.getValue(ResultConf.MESSAGE, messageCode + "", params);
        return new ResultInfoRealization(ResultCode.SUCCESS, messageCode, message);
    }

    /**
     * 创建成功结果集
     *
     * @param message 提示信息
     */
    public static ResultInfo createSuccess(String message) {
        return new ResultInfoRealization(ResultCode.SUCCESS, message);
    }

    /**
     * 创建警告结果集
     *
     * @param messageCode 提示信息key
     * @param params      格式化参数
     */
    public static ResultInfo createWarn(int messageCode, Object[] params) {
        String message = params == null ? ResourceUtil.getValue(ResultConf.MESSAGE, messageCode + "")
                : ResourceUtil.getValue(ResultConf.MESSAGE, messageCode + "", params);
        return new ResultInfoRealization(ResultCode.WARN, messageCode, message);
    }

    /**
     * 创建警告结果集
     *
     * @param message 提示信息
     */
    public static ResultInfo createWarn(String message) {
        return new ResultInfoRealization(ResultCode.WARN, message);
    }

    /**
     * 创建信息结果集
     *
     * @param messageCode 提示信息key
     * @param params      格式化参数
     */
    public static ResultInfo createInfo(int messageCode, Object[] params) {
        String message = params == null ? ResourceUtil.getValue(ResultConf.MESSAGE, messageCode + "")
                : ResourceUtil.getValue(ResultConf.MESSAGE, messageCode + "", params);
        return new ResultInfoRealization(ResultCode.INFO, messageCode, message);
    }

    /**
     * 创建信息结果集
     *
     * @param message 提示信息
     */
    public static ResultInfo createInfo(String message) {
        return new ResultInfoRealization(ResultCode.INFO, message);
    }

    /**
     * 创建错误结果集
     *
     * @param messageCode 提示信息key
     * @param params      格式化参数
     */
    public static ResultInfo createError(int messageCode, Object[] params) {
        String message = params == null ? ResourceUtil.getValue(ResultConf.MESSAGE, messageCode + "")
                : ResourceUtil.getValue(ResultConf.MESSAGE, messageCode + "", params);
        return new ResultInfoRealization(ResultCode.ERROR, messageCode, message);
    }

    /**
     * 创建错误结果集
     *
     * @param message 提示信息
     */
    public static ResultInfo createError(String message) {
        return new ResultInfoRealization(ResultCode.ERROR, message);
    }

    /**
     * 创建需要登录结果集
     *
     * @param messageCode 提示信息key
     * @param params      格式化参数
     */
    public static ResultInfo createNeedLogin(int messageCode, Object[] params) {
        String message = params == null ? ResourceUtil.getValue(ResultConf.MESSAGE, messageCode + "")
                : ResourceUtil.getValue(ResultConf.MESSAGE, messageCode + "", params);
        return new ResultInfoRealization(ResultCode.NEED_LOGIN, messageCode, message);
    }

    /**
     * 创建需要登录结果集
     *
     * @param message 提示信息
     */
    public static ResultInfo createNeedLogin(String message) {
        return new ResultInfoRealization(ResultCode.NEED_LOGIN, message);
    }

    /**
     * 创建需要权限结果集
     *
     * @param messageCode 提示信息key
     * @param params      格式化参数
     */
    public static ResultInfo createNoAuthority(int messageCode, Object[] params) {
        String message = params == null ? ResourceUtil.getValue(ResultConf.MESSAGE, messageCode + "")
                : ResourceUtil.getValue(ResultConf.MESSAGE, messageCode + "", params);
        return new ResultInfoRealization(ResultCode.NO_AUTHORITY, messageCode, message);
    }

    /**
     * 创建需要权限结果集
     *
     * @param message 提示信息
     */
    public static ResultInfo createNoAuthority(String message) {
        return new ResultInfoRealization(ResultCode.NO_AUTHORITY, message);
    }

    /**
     * 创建404结果集
     *
     * @param messageCode 提示信息key
     * @param params      格式化参数
     */
    public static ResultInfo createNotFound(int messageCode, Object[] params) {
        String message = params == null ? ResourceUtil.getValue(ResultConf.MESSAGE, messageCode + "")
                : ResourceUtil.getValue(ResultConf.MESSAGE, messageCode + "", params);
        return new ResultInfoRealization(ResultCode.NOT_FOUND, messageCode, message);
    }

    /**
     * 创建需要权限结果集
     *
     * @param message 提示信息
     */
    public static ResultInfo createNotFound(String message) {
        return new ResultInfoRealization(ResultCode.NOT_FOUND, message);
    }

    /**
     * 构建异常
     */
    public static ResultException createResultException(ResultInfo resultInfo) {
        return new ResultException(resultInfo);
    }

    /**
     * 构建需要登录异常
     */
    public static NeedLoginException createNeedLoginException(ResultInfo resultInfo) {
        return new NeedLoginException(resultInfo);
    }

    /**
     * 构建没有权限异常
     */
    public static NoAuthorityException createNoAuthorityException(ResultInfo resultInfo) {
        return new NoAuthorityException(resultInfo);
    }

    /**
     * 构建404异常
     */
    public static NotFoundException createNotFoundException(ResultInfo resultInfo) {
        return new NotFoundException(resultInfo);
    }

}
