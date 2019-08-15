package com.yibo.security.core.authentication.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author: huangyibo
 * @Date: 2019/7/31 1:22
 * @Description:
 *
 * SmsCodeAuthenticationProvider用于处理短信登录的校验逻辑
 */
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    /**
     * 实际开发中，这里注入的是自己实现UserDetailsService接口的对象，通过手机号去数据库查找用户信息
     * 注入类仿照MyUserDetailsService进行实现
     */
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * 进行身份认证的逻辑
     *
     * 用UserDetailsService获取用户信息，获取到了之后拿用户信息重新组装一个已认证的Authentication返回
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //未认证SmsCodeAuthenticationToken
        SmsCodeAuthenticationToken authenticationToken = (SmsCodeAuthenticationToken)authentication;

        UserDetails user = userDetailsService.loadUserByUsername((String)authenticationToken.getPrincipal());
        if(user == null){
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }
        //已认证SmsCodeAuthenticationToken
        SmsCodeAuthenticationToken authenticationResult = new SmsCodeAuthenticationToken(user,user.getAuthorities());
        //将未认证的SmsCodeAuthenticationToken的Details信息设置到已认证SmsCodeAuthenticationToken中去
        authenticationResult.setDetails(authenticationToken.getDetails());
        return authenticationResult;
    }

    /**
     * 在AuthenticationManager里面会在provider集合中挑一个provider来处理传进去的Token，挑选的依据就是调用supports方法来判断的
     * @param aClass
     * @return
     */
    @Override
    public boolean supports(Class<?> aClass) {
        //判断aClass是否是SmsCodeAuthenticationToken类型
        return SmsCodeAuthenticationToken.class.isAssignableFrom(aClass);
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
