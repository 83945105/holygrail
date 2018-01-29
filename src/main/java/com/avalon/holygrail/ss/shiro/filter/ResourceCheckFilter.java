package com.avalon.holygrail.ss.shiro.filter;

import com.avalon.holygrail.ss.shiro.plugins.ShiroHandler;
import com.avalon.holygrail.util.HttpUtil;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 资源请求校验器
 */
public class ResourceCheckFilter extends AccessControlFilter {

    /**
     * 项目所有的控制器请求
     */
    public static Map<String, Boolean> AllRequestMappingUrl = new HashMap<>();

    private ShiroHandler shiroHandler;

    //重写该方法使用自定义权限认证,也就是走UrlPermission来验证是否有访问权限
    @Override
    protected boolean isAccessAllowed(ServletRequest request,
                                      ServletResponse response, Object mappedValue) throws Exception {
        Subject subject = getSubject(request, response);
        String url = getPathWithinApplication(request);
        return subject.isPermitted(url);
    }

    //进入此方法说明报错
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {

        HttpServletRequest hReq = (HttpServletRequest) request;

        //String url = getPathWithinApplication(request);
        if (HttpUtil.isAjax(hReq)) {
            this.shiroHandler.onAjaxAccessDenied(request, response);
        } else {
            this.shiroHandler.onAccessDenied(request, response);
        }
        return false;
    }

    public void setShiroHandler(ShiroHandler shiroHandler) {
        this.shiroHandler = shiroHandler;
    }
}
