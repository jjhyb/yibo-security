package com.yibo.security.core.authorize;

import com.yibo.security.core.properties.SecurityConstants;
import com.yibo.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * @author: huangyibo
 * @Date: 2019/8/15 0:02
 * @Description:
 *
 * 授权配置接口实现类
 */

@Component
@Order(Integer.MIN_VALUE)//优先级靠前
public class YiBoAuthorizeConfigProvider implements AuthorizeConfigProvider {

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 安全模块中浏览器和app中不需要验证就可以访问的url配置
     * @param config
     */
    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config.antMatchers(
                SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
                securityProperties.getBrowser().getLoginPage(),
                SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX+"/*",
                securityProperties.getBrowser().getSignUpUrl(),
                securityProperties.getBrowser().getSignOutUrl(),
                securityProperties.getBrowser().getSession().getSessionInvalidUrl())
                .permitAll();

    }
}
