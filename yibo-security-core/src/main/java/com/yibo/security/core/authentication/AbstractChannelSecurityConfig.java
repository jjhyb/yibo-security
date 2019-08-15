package com.yibo.security.core.authentication;

import com.yibo.security.core.properties.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @author: huangyibo
 * @Date: 2019/8/10 20:33
 * @Description:
 */
public class AbstractChannelSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    protected AuthenticationSuccessHandler yiBoAuthenticationSuccessHandler;

    @Autowired
    protected AuthenticationFailureHandler yiBoAuthenticationFailureHandler;

    /**
     * 密码登录相关的配置
     * @param http
     * @throws Exception
     */
    protected void applyPasswordAuthenticationConfig(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
                .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
                .successHandler(yiBoAuthenticationSuccessHandler)//登录成功后使用自己实现的登录成功处理器
                .failureHandler(yiBoAuthenticationFailureHandler);//登录失败后使用自己实现的登录失败处理器
    }
}
