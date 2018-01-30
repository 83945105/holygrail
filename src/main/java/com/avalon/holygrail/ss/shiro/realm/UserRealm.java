package com.avalon.holygrail.ss.shiro.realm;

import com.avalon.holygrail.ss.shiro.norm.ShiroCertificate;
import com.avalon.holygrail.ss.shiro.plugins.ShiroHandler;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;
import java.util.Set;

/**
 * 用于用户登录授权的Realm
 */
public class UserRealm extends AuthorizingRealm {

    private ShiroHandler<ShiroCertificate> shiroHandler;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //获得用户

        ShiroCertificate certificate = (ShiroCertificate) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        try {
            Set<String> roles = this.shiroHandler.findRoles(certificate);
            Set<String> urls = this.shiroHandler.findUrls(certificate, roles);
            info.setRoles(roles);
            info.setStringPermissions(urls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = token.getPrincipal().toString();
        String password = new String((char[]) token.getCredentials());
        //调用定制业务层的登录方法进行验证
        ShiroCertificate certificate = this.shiroHandler.login(username, password);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(certificate,
                password, this.getName());
        info.setCredentialsSalt(ByteSource.Util.bytes(certificate.getSalt()));
        return info;
    }

    // 清除授权的缓存
    @Override
    protected void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    // 清除认证的缓存
    @Override
    protected void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        ShiroCertificate certificate = (ShiroCertificate) principals.getPrimaryPrincipal();
        SimplePrincipalCollection spc = new SimplePrincipalCollection(
                certificate, this.getName());
        super.clearCachedAuthenticationInfo(spc);
    }

    public ShiroHandler<ShiroCertificate> getShiroHandler() {
        return shiroHandler;
    }

    public void setShiroHandler(ShiroHandler<ShiroCertificate> shiroHandler) {
        this.shiroHandler = shiroHandler;
    }
}
