package com.yibo.security.core.authorize;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * @author: huangyibo
 * @Date: 2019/8/14 23:59
 * @Description:
 *
 * 授权配置接口
 */
public interface AuthorizeConfigProvider {

    void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config);
}
