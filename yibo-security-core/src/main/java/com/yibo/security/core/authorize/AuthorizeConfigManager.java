package com.yibo.security.core.authorize;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * @author: huangyibo
 * @Date: 2019/8/15 0:21
 * @Description:
 *
 * 授权配置管理器接口
 */
public interface AuthorizeConfigManager {

    void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config);
}
