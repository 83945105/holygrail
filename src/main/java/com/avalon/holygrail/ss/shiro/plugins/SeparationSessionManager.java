package com.avalon.holygrail.ss.shiro.plugins;

import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.servlet.ShiroHttpSession;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * 前后端分离Shiro Session 管理器
 * Created by 白超 on 2017/12/18.
 */
public class SeparationSessionManager extends DefaultWebSessionManager {

    private Cookie sessionIdCookie;

    private static final Logger log = LoggerFactory.getLogger(SeparationSessionManager.class);

    /**
     * 改写获取sessionId的方式
     */
    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        return this.getReferencedSessionId(request, response);
    }

    private Serializable getReferencedSessionId(ServletRequest request, ServletResponse response) {
        String id = this.getSessionIdCookieValue(request, response);
        if (id != null) {
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE,
                    ShiroHttpServletRequest.COOKIE_SESSION_ID_SOURCE);
        } else {
            //根据配置文件设置的Cookie Key 也就是前后端分离用于获取token的key
            //走到这一步说明cookie中没有,那么采取第二方案从请求头中获取
            //id = WebUtils.toHttp(request).getHeader(this.sessionIdCookie.getName());
            if(id == null) {
                //从Get请求的地址栏参数中获取id,要求格式为?JSESSIONID=XX
                id = this.getUriPathSegmentParamValue(request, ShiroHttpSession.DEFAULT_SESSION_ID_NAME);
            }
            if (id == null) {
                //根据配置文件设置的Cookie Key 也就是前后端分离用于获取token的key
                //走到这一步说明cookie中没有,那么采取第二方案从请求头中获取
                String name = getSessionIdName();
                id = request.getParameter(name);
                if (id == null) {
                    id = request.getParameter(name.toLowerCase());
                }
            }
            if (id != null) {
                request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE,
                        ShiroHttpServletRequest.URL_SESSION_ID_SOURCE);
            }
        }
        if (id != null) {
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
        }
        return id;
    }

    private String getSessionIdCookieValue(ServletRequest request, ServletResponse response) {
        if (!isSessionIdCookieEnabled()) {
            log.debug("Session ID cookie is disabled - session id will not be acquired from a request cookie.");
            return null;
        }
        if (!(request instanceof HttpServletRequest)) {
            log.debug("Current request is not an HttpServletRequest - cannot get session ID cookie.  Returning null.");
            return null;
        }
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        return getSessionIdCookie().readValue(httpRequest, WebUtils.toHttp(response));
    }

    public Cookie getSessionIdCookie() {
        return sessionIdCookie;
    }

    private String getUriPathSegmentParamValue(ServletRequest servletRequest, String paramName) {

        if (!(servletRequest instanceof HttpServletRequest)) {
            return null;
        }
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String uri = request.getRequestURI();
        if (uri == null) {
            return null;
        }

        int queryStartIndex = uri.indexOf('?');
        if (queryStartIndex >= 0) { //get rid of the query string
            uri = uri.substring(0, queryStartIndex);
        }

        int index = uri.indexOf(';'); //now check for path segment parameters:
        if (index < 0) {
            //no path segment params - return:
            return null;
        }

        //there are path segment params, let's get the last one that may exist:

        final String TOKEN = paramName + "=";

        uri = uri.substring(index + 1); //uri now contains only the path segment params

        //we only care about the last JSESSIONID param:
        index = uri.lastIndexOf(TOKEN);
        if (index < 0) {
            //no segment param:
            return null;
        }

        uri = uri.substring(index + TOKEN.length());

        index = uri.indexOf(';'); //strip off any remaining segment params:
        if (index >= 0) {
            uri = uri.substring(0, index);
        }

        return uri;
    }

    private String getSessionIdName() {
        String name = this.sessionIdCookie != null ? this.sessionIdCookie.getName() : null;
        if (name == null) {
            name = ShiroHttpSession.DEFAULT_SESSION_ID_NAME;
        }
        return name;
    }

    @Override
    public void setSessionIdCookie(Cookie sessionIdCookie) {
        this.sessionIdCookie = sessionIdCookie;
    }

}
