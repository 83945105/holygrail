package com.avalon.holygrail.ss.shiro.plugins;

import com.avalon.holygrail.ss.shiro.exception.NullPasswordException;
import com.avalon.holygrail.ss.shiro.exception.NullUsernameException;
import com.avalon.holygrail.ss.shiro.exception.ShiroException;
import com.avalon.holygrail.ss.shiro.norm.ShiroCertificate;
import com.avalon.holygrail.ss.util.ResultUtil;
import com.avalon.holygrail.ss.view.ExceptionView;
import com.avalon.holygrail.util.HttpUtil;
import com.avalon.holygrail.util.StringUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * <p>Title: 用于实现Shiro相关操作</p>
 * <p>Description: </p>
 *
 * @author 白超
 * @date 2017年3月16日 下午5:15:40
 * @版本 V 1.0
 */
public interface ShiroHandler<T> {

    /**
     * JSON转换器
     */
    HttpMessageConverter JSON_MESSAGE_CONVERTER = new MappingJackson2HttpMessageConverter();

    /**
     * 用户名为空提示
     */
    String NULL_USERNAME_MESSAGE = "用户名不能为空";
    /**
     * 密码为空提示
     */
    String NULL_PASSWORD_MESSAGE = "密码不能为空";
    /**
     * 账号不存在提示
     */
    String UNKNOWN_ACCOUNT_MESSAGE = "账号不存在";
    /**
     * 凭据不正确提示
     */
    String INCORRECT_CREDENTIALS_MESSAGE = "用户名或密码错误";
    /**
     * 账户锁定提示
     */
    String LOCKED_ACCOUNT_MESSAGE = "您的账户已被冻结";
    /**
     * 无权提示
     */
    String NO_AUTHENTICATION_MESSAGE = "您没有权限做此操作";
    /**
     * 需要登录提示
     */
    String NEED_LOGIN_MESSAGE = "您需要登录后才能进行该操作";
    /**
     * 404提示
     */
    String NOT_FOUND_MESSAGE = "您要访问的资源不存在";

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     */
    T login(String username, String password) throws AuthenticationException;

    /**
     * 查询用户拥有的角色
     *
     * @param certificate 证书
     * @return 用户拥有的角色集合
     * @throws Exception
     */
    Set<String> findRoles(ShiroCertificate certificate) throws Exception;

    /**
     * 查询用户可以访问的urls
     *
     * @param certificate 证书
     * @param roles       用户拥有的角色信息
     * @return 用户可以访问的urls
     * @throws Exception
     */
    Set<String> findUrls(ShiroCertificate certificate, Set<String> roles) throws Exception;

    /**
     * 查询用户可见的资源
     * @param certificate 证书
     * @param roles       用户拥有的角色信息
     * @return 用户可见的资源
     * @throws Exception
     */
    Set<String> findVisibles(ShiroCertificate certificate, Set<String> roles) throws Exception;

    /**
     * subject登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 是否登录成功
     * @throws ShiroException
     */
    static boolean subjectLogin(String username, String password) throws ShiroException {

        if (StringUtil.isEmpty(username)) {
            throw new NullUsernameException(NULL_USERNAME_MESSAGE);
        }
        if (StringUtil.isEmpty(password)) {
            throw new NullPasswordException(NULL_PASSWORD_MESSAGE);
        }
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(new UsernamePasswordToken(username, password));
        } catch (UnknownAccountException ex) {
            throw new com.avalon.holygrail.ss.shiro.exception.UnknownAccountException(UNKNOWN_ACCOUNT_MESSAGE, ex);
        } catch (IncorrectCredentialsException ex) {
            throw new com.avalon.holygrail.ss.shiro.exception.IncorrectCredentialsException(INCORRECT_CREDENTIALS_MESSAGE, ex);
        } catch (LockedAccountException ex) {
            throw new com.avalon.holygrail.ss.shiro.exception.LockedAccountException(LOCKED_ACCOUNT_MESSAGE, ex);
        } catch (AuthenticationException ex) {
            throw new com.avalon.holygrail.ss.shiro.exception.AuthenticationException(NO_AUTHENTICATION_MESSAGE, ex);
        }
        return true;
    }

    /**
     * 登录成功操作
     *
     * @param request  请求
     * @param response 响应
     */
    void loginSuccess(ServletRequest request, ServletResponse response);

    /**
     * 登录失败操作
     *
     * @param request  请求
     * @param response 响应
     * @param ex       失败异常
     */
    void loginFail(ServletRequest request, ServletResponse response, ShiroException ex);

    /**
     * 表单登录成功
     *
     * @param request  请求
     * @param response 响应
     */
    default void formLoginSuccess(ServletRequest request, ServletResponse response) {
        this.loginSuccess(request, response);
    }

    /**
     * 表单登录失败
     *
     * @param request  请求
     * @param response 响应
     * @param ex       失败异常
     */
    default void formLoginFail(ServletRequest request, ServletResponse response, ShiroException ex) {
        this.loginFail(request, response, ex);
    }

    /**
     * ajax登录成功
     *
     * @param request  请求
     * @param response 响应
     */
    default void ajaxLoginSuccess(ServletRequest request, ServletResponse response) {
        this.loginSuccess(request, response);
    }

    /**
     * ajax登录失败
     *
     * @param request  请求
     * @param response 响应
     * @param ex       失败异常
     */
    default void ajaxLoginFail(ServletRequest request, ServletResponse response, ShiroException ex) {
        this.loginFail(request, response, ex);
    }

    /**
     * 游客访问被拒绝
     *
     * @param request  请求
     * @param response 响应
     * @throws Exception
     */
    default boolean onVisitorAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpOutputMessage outputMessage = new ServletServerHttpResponse((HttpServletResponse) response);
        JSON_MESSAGE_CONVERTER.write(new ExceptionView(ResultUtil.createNeedLogin(NEED_LOGIN_MESSAGE), "view/login"), MediaType.APPLICATION_JSON, outputMessage);
        return false;
    }

    /**
     * 用户访问被拒绝
     *
     * @param request  请求
     * @param response 响应
     * @throws Exception
     */
    default boolean onUserAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpOutputMessage outputMessage = new ServletServerHttpResponse(res);
        if (HttpUtil.isAjax(req)) {
            JSON_MESSAGE_CONVERTER.write(new ExceptionView(ResultUtil.createNoAuthority(NO_AUTHENTICATION_MESSAGE), "view/noAuthority"), MediaType.APPLICATION_JSON, outputMessage);
        } else {
            req.getRequestDispatcher("/view/noAuthority?exceptionMessage=" + NO_AUTHENTICATION_MESSAGE).forward(request, response);
        }
        return false;
    }
}
