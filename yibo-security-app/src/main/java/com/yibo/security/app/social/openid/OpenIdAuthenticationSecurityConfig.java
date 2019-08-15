package com.yibo.security.app.social.openid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

/**
 * @author: huangyibo
 * @Date: 2019/8/13 14:28
 * @Description:
 */

@Component
public class OpenIdAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private AuthenticationSuccessHandler yiBoAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler yiBoAuthenticationFailureHandler;

    @Autowired
    private SocialUserDetailsService userDetailsService;

    @Autowired
    private UsersConnectionRepository usersConnectionRepository;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        OpenIdAuthenticationFilter OpenIdAuthenticationFilter = new OpenIdAuthenticationFilter();
        OpenIdAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        OpenIdAuthenticationFilter.setAuthenticationSuccessHandler(yiBoAuthenticationSuccessHandler);
        OpenIdAuthenticationFilter.setAuthenticationFailureHandler(yiBoAuthenticationFailureHandler);

        OpenIdAuthenticationProvider OpenIdAuthenticationProvider = new OpenIdAuthenticationProvider();
        OpenIdAuthenticationProvider.setUserDetailsService(userDetailsService);
        OpenIdAuthenticationProvider.setUsersConnectionRepository(usersConnectionRepository);

        http.authenticationProvider(OpenIdAuthenticationProvider)
                .addFilterAfter(OpenIdAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    }
}
