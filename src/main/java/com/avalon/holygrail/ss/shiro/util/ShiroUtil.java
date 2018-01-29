package com.avalon.holygrail.ss.shiro.util;

import com.avalon.holygrail.ss.shiro.norm.ShiroCertificate;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import java.io.Serializable;

/**
 * Shiro工具
 * Created by 白超 on 2018/1/29.
 */
public class ShiroUtil {

    /**
     * 获取证书
     */
    public static ShiroCertificate getShiroCertificate() {
        Subject subject = SecurityUtils.getSubject();
        if (subject == null) {
            return null;
        }
        PrincipalCollection principalCollection = subject.getPrincipals();
        if(principalCollection == null) {
            return null;
        }
        return (ShiroCertificate) principalCollection.getPrimaryPrincipal();
    }

    /**
     * 获取Session
     */
    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    /**
     * 获取sessionId
     */
    public static Serializable getSessionId() {
        Session session = getSession();
        if(session == null) {
            return null;
        }
        return  session.getId();
    }

    /**
     * 验证登录状态
     * @throws Exception 未登录时
     */
    public static boolean validateLogin() throws Exception {
        if(!SecurityUtils.getSubject().isAuthenticated()) {
            return false;
        }
        ShiroCertificate certificate = getShiroCertificate();
        if(certificate == null) {
            return false;
        }
        return true;
    }
}
