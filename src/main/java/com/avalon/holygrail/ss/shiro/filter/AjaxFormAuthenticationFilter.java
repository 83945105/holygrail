package com.avalon.holygrail.ss.shiro.filter;

import com.avalon.holygrail.ss.shiro.exception.ShiroException;
import com.avalon.holygrail.ss.shiro.plugins.ShiroHandler;
import com.avalon.holygrail.util.HttpUtil;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>Title: 自定义Form过滤器,用于实现表单和AJAX登录</p>
 * <p>Description: </p>
 * @author 白超
 * @date 2017年6月16日 上午8:55:17
 * @版本 V 1.0
 */
public class AjaxFormAuthenticationFilter extends FormAuthenticationFilter {

    /**
     * 用于获取request中username的Key
     */
    private String usernameKey = "username";
    /**
     * 用于获取request中password的Key
     */
    private String passwordKey = "password";

    private ShiroHandler shiroHandler;

    @Override
    public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        /**
         * 如果用户请求URL不是该表单验证器配置的loginUrl,则使用原始{@link FormAuthenticationFilter} 的onPreHandle方法<br/>
         * 该方法会调用isAccessAllowed(本类已重写),如果返回false则调用onAccessDenied(本类已重写),若还是返回false则禁止用户访问<br/>
         *
         * 如果用户请求URL是该表单验证器配置的loginUrl,则继续判断是否是POST请求<br/>
         * 如果不是POST请求,则放行,在这里,GET请求的loginUrl用于进入登录页面,该页面的GET请求需要根据项目单独编写<br/>
         *
         * 如果是POST请求,则表示该请求用于本地登录校验,首先判断该POST请求是Form表单请求还是AJAX请求<br/>
         * 如果是Form请求则执行formToLogin<br/>
         * 如果是Ajax请求则执行ajaxTologin<br/>
         */
        return !isLoginRequest(request, response) ? super.onPreHandle(request, response, mappedValue)
                : !"post".equalsIgnoreCase(req.getMethod()) ? true
                : HttpUtil.isAjax(req) ? ajax2login(req, resp)
                : form2Login(req, resp);
    }

    //form表单登录
    private boolean form2Login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter(this.usernameKey);
        String password = request.getParameter(this.passwordKey);

        try {
            ShiroHandler.subjectLogin(username, password);
        } catch (ShiroException e) {
            this.shiroHandler.formLoginFail(request, response, e);
            /**
             * 登录失败,回到登录页
             */
            redirectToLogin(request, response);
        }

        this.shiroHandler.formLoginSuccess(request, response);

        /**
         * 登录成功,回到登录前页面
         */
        WebUtils.redirectToSavedRequest(request, response, getSuccessUrl());

        return false;//不继续执行过滤器
    }

    //ajax登录
    private boolean ajax2login(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String username = request.getParameter(this.usernameKey);
        String password = request.getParameter(this.passwordKey);

        try {
            ShiroHandler.subjectLogin(username, password);
        } catch (ShiroException e) {
            this.shiroHandler.ajaxLoginFail(request, response, e);
        }

        this.shiroHandler.ajaxLoginSuccess(request, response);

        return false;
    }

    /**
     * 以用户是否登录为条件判定是否允许访问,该方法实现我写的和超类里面一样,在这里重写是为了调试方便
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        Subject subject = getSubject(request, response);
        return subject.isAuthenticated();
    }

    /**
     * 当访问被拒绝时
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        if (HttpUtil.isAjax(httpRequest)) {
            this.shiroHandler.onAjaxAccessDenied(request, response);
            return false;
        } else {
            return super.onAccessDenied(request, response);
        }

    }

    public void setUsernameKey(String usernameKey) {
        this.usernameKey = usernameKey;
    }

    public void setPasswordKey(String passwordKey) {
        this.passwordKey = passwordKey;
    }

    public void setShiroHandler(ShiroHandler shiroHandler) {
        this.shiroHandler = shiroHandler;
    }
}
