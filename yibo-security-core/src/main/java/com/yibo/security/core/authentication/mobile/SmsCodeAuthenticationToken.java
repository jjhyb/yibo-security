package com.yibo.security.core.authentication.mobile;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author: huangyibo
 * @Date: 2019/7/31 1:00
 * @Description:
 *
 * SmsCodeAuthenticationToken用于封装手机登录的登录信息
 * 在身份认证之前，里面封装的是前端传过来的手机号
 * 在身份认证成功之后，里面放的是认证成功之后的用户信息
 */
public class SmsCodeAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = 420L;

    /**
     * 用于存放认证信息的
     * 在身份认证之前，里面封装的是前端传过来的手机号
     * 在身份认证成功之后，里面放的是认证成功之后的用户信息
     */
    private final Object principal;

    public SmsCodeAuthenticationToken(String telephone) {
        super((Collection)null);
        this.principal = telephone;
        this.setAuthenticated(false);
    }

    public SmsCodeAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true);
    }

    public Object getCredentials() {
        return null;
    }

    public Object getPrincipal() {
        return this.principal;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        } else {
            super.setAuthenticated(false);
        }
    }

    public void eraseCredentials() {
        super.eraseCredentials();
    }
}
