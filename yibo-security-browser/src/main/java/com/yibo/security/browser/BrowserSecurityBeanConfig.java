package com.yibo.security.browser;

import com.yibo.security.browser.logout.YiBoLogoutSuccessHandler;
import com.yibo.security.browser.session.YiBoExpiredSessionStrategy;
import com.yibo.security.browser.session.YiBoInvalidSessionStrategy;
import com.yibo.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

/**
 * @author: huangyibo
 * @Date: 2019/8/12 1:14
 * @Description:
 */
@Configuration
public class BrowserSecurityBeanConfig {

    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    @ConditionalOnMissingBean(InvalidSessionStrategy.class)
    public InvalidSessionStrategy invalidSessionStrategy(){
        return new YiBoInvalidSessionStrategy(securityProperties.getBrowser().getSession().getSessionInvalidUrl());
    }

    @Bean
    @ConditionalOnMissingBean(SessionInformationExpiredStrategy.class)
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy(){
        return new YiBoExpiredSessionStrategy(securityProperties.getBrowser().getSession().getSessionInvalidUrl());
    }

    @Bean
    @ConditionalOnMissingBean(LogoutSuccessHandler.class)
    public LogoutSuccessHandler logoutSuccessHandler(){
        return new YiBoLogoutSuccessHandler(securityProperties.getBrowser().getSignOutUrl());
    }

}
