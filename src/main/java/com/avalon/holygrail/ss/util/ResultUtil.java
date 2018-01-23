package com.avalon.holygrail.ss.util;

import com.avalon.holygrail.ss.conf.ResultConf;
import com.avalon.holygrail.ss.exception.NotFoundException;
import com.avalon.holygrail.ss.exception.NeedLoginException;
import com.avalon.holygrail.ss.exception.NoAuthorityException;
import com.avalon.holygrail.ss.exception.ResultException;
import com.avalon.holygrail.ss.model.ResultCodeEnum;
import com.avalon.holygrail.ss.model.ResultInfoRealization;
import com.avalon.holygrail.ss.norm.ResultInfo;

/**
 * 系统结果工具类
 */
public class ResultUtil {

	/**
	 * 创建失败结果集
	 * @param messageCode 提示信息key
	 * @param params 格式化参数
	 */
	public static ResultInfo createFail(int messageCode, Object[] params) {
		String message = params == null ? ResourceUtil.getValue(ResultConf.MESSAGE, messageCode + "")
				: ResourceUtil.getValue(ResultConf.MESSAGE, messageCode + "", params);
		return new ResultInfoRealization(ResultCodeEnum.FAIL, messageCode, message);
	}
	/**
	 * 创建失败结果集
	 * @param message 提示信息
	 */
	public static ResultInfo createFail(String message) {
		return new ResultInfoRealization(ResultCodeEnum.FAIL, message);
	}
	/**
	 * 创建失败结果集
	 * @param message 提示信息
	 * @param exceptionMessage 异常信息
	 */
	public static ResultInfo createFail(String message, String exceptionMessage) {
		return new ResultInfoRealization(ResultCodeEnum.FAIL, message, exceptionMessage);
	}
	/**
	 * 创建成功结果集
	 * @param messageCode 提示信息key
	 * @param params 格式化参数
	 */
	public static ResultInfo createSuccess(int messageCode, Object[] params) {
		String message = params == null ? ResourceUtil.getValue(ResultConf.MESSAGE, messageCode + "")
				: ResourceUtil.getValue(ResultConf.MESSAGE, messageCode + "", params);
		return new ResultInfoRealization(ResultCodeEnum.SUCCESS, messageCode, message);
	}
	/**
	 * 创建成功结果集
	 * @param message 提示信息
	 */
	public static ResultInfo createSuccess(String message) {
		return new ResultInfoRealization(ResultCodeEnum.SUCCESS, message);
	}
	/**
	 * 创建警告结果集
	 * @param messageCode 提示信息key
	 * @param params 格式化参数
	 */
	public static ResultInfo createWarn(int messageCode, Object[] params) {
		String message = params == null ? ResourceUtil.getValue(ResultConf.MESSAGE, messageCode + "")
				: ResourceUtil.getValue(ResultConf.MESSAGE, messageCode + "", params);
		return new ResultInfoRealization(ResultCodeEnum.WARN, messageCode, message);
	}
	/**
	 * 创建警告结果集
	 * @param message 提示信息
	 */
	public static ResultInfo createWarn(String message) {
		return new ResultInfoRealization(ResultCodeEnum.WARN, message);
	}
	/**
	 * 创建信息结果集
	 * @param messageCode 提示信息key
	 * @param params 格式化参数
	 */
	public static ResultInfo createInfo(int messageCode, Object[] params) {
		String message = params == null ? ResourceUtil.getValue(ResultConf.MESSAGE, messageCode + "")
				: ResourceUtil.getValue(ResultConf.MESSAGE, messageCode + "", params);
		return new ResultInfoRealization(ResultCodeEnum.INFO, messageCode, message);
	}
	/**
	 * 创建信息结果集
	 * @param message 提示信息
	 */
	public static ResultInfo createInfo(String message) {
		return new ResultInfoRealization(ResultCodeEnum.INFO, message);
	}
	
	/**
	 * 创建错误结果集
	 * @param messageCode 提示信息key
	 * @param params 格式化参数
	 */
	public static ResultInfo createError(int messageCode, Object[] params) {
		String message = params == null ? ResourceUtil.getValue(ResultConf.MESSAGE, messageCode + "")
				: ResourceUtil.getValue(ResultConf.MESSAGE, messageCode + "", params);
		return new ResultInfoRealization(ResultCodeEnum.ERROR, messageCode, message);
	}
	/**
	 * 创建错误结果集
	 * @param message 提示信息
	 */
	public static ResultInfo createError(String message) {
		return new ResultInfoRealization(ResultCodeEnum.ERROR, message);
	}
	
	/**
	 * 创建需要登录结果集
	 * @param messageCode 提示信息key
	 * @param params 格式化参数
	 */
	public static ResultInfo createNeedLogin(int messageCode, Object[] params) {
		String message = params == null ? ResourceUtil.getValue(ResultConf.MESSAGE, messageCode + "")
				: ResourceUtil.getValue(ResultConf.MESSAGE, messageCode + "", params);
		return new ResultInfoRealization(ResultCodeEnum.NEED_LOGIN, messageCode, message);
	}
	/**
	 * 创建需要登录结果集
	 * @param message 提示信息
	 */
	public static ResultInfo createNeedLogin(String message) {
		return new ResultInfoRealization(ResultCodeEnum.NEED_LOGIN, message);
	}
	
	/**
	 * 创建需要权限结果集
	 * @param messageCode 提示信息key
	 * @param params 格式化参数
	 */
	public static ResultInfo createNoAuthority(int messageCode, Object[] params) {
		String message = params == null ? ResourceUtil.getValue(ResultConf.MESSAGE, messageCode + "")
				: ResourceUtil.getValue(ResultConf.MESSAGE, messageCode + "", params);
		return new ResultInfoRealization(ResultCodeEnum.NO_AUTHORITY, messageCode, message);
	}
	/**
	 * 创建需要权限结果集
	 * @param message 提示信息
	 */
	public static ResultInfo createNoAuthority(String message) {
		return new ResultInfoRealization(ResultCodeEnum.NO_AUTHORITY, message);
	}
	
	/**
	 * 创建404结果集
	 * @param messageCode 提示信息key
	 * @param params 格式化参数
	 */
	public static ResultInfo createNotFound(int messageCode, Object[] params) {
		String message = params == null ? ResourceUtil.getValue(ResultConf.MESSAGE, messageCode + "")
				: ResourceUtil.getValue(ResultConf.MESSAGE, messageCode + "", params);
		return new ResultInfoRealization(ResultCodeEnum.NOT_FOUND, messageCode, message);
	}
	/**
	 * 创建需要权限结果集
	 * @param message 提示信息
	 */
	public static ResultInfo createNotFound(String message) {
		return new ResultInfoRealization(ResultCodeEnum.NOT_FOUND, message);
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
