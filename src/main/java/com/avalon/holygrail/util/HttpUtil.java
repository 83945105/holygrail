package com.avalon.holygrail.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Http工具
 * Created by 白超 on 2018/1/29.
 */
public class HttpUtil {

    /**
     * 判断是否是AJAX请求
     * @param request 请求
     * @return
     */
    public static boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }
}
