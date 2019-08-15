package com.yibo.security.core.authentication.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * @author: huangyibo
 * @Date: 2019/7/31 1:43
 * @Description:
 */

@Component
public class SmsCodeAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain,HttpSecurity> {

    @Autowired
    private AuthenticationSuccessHandler yiBoAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler yiBoAuthenticationFailureHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        SmsCodeAuthenticationFilter smsCodeAuthenticationFilter = new SmsCodeAuthenticationFilter();
        //设置AuthenticationManager
        smsCodeAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        //设置成功处理器
        smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(yiBoAuthenticationSuccessHandler);
        //设置失败处理器
        smsCodeAuthenticationFilter.setAuthenticationFailureHandler(yiBoAuthenticationFailureHandler);

        SmsCodeAuthenticationProvider smsCodeAuthenticationProvider = new SmsCodeAuthenticationProvider();
        //将自己实现的UserDetailsService设置进去
        smsCodeAuthenticationProvider.setUserDetailsService(userDetailsService);

        http.authenticationProvider(smsCodeAuthenticationProvider)//将smsCodeAuthenticationProvider添加到AuthenticationManager管理的Provider集合中去
                .addFilterAfter(smsCodeAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);
    }
}
