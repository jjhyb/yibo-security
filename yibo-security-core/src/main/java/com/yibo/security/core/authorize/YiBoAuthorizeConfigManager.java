package com.yibo.security.core.authorize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: huangyibo
 * @Date: 2019/8/15 0:22
 * @Description:
 *
 * 授权配置管理器接口实现类
 */

@Component
public class YiBoAuthorizeConfigManager implements AuthorizeConfigManager {

    @Autowired
    private List<AuthorizeConfigProvider> authorizeConfigProviders;

    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        for (AuthorizeConfigProvider authorizeConfigProvider : authorizeConfigProviders) {
            authorizeConfigProvider.config(config);
        }
        //除了AuthorizeConfigProvider中配置的请求外，其他的所有请求都需要身份认证才能访问
//        config.anyRequest().authenticated();
    }
}
