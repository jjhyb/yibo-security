package com.yibo.security;

import com.yibo.security.core.authorize.AuthorizeConfigProvider;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * @author: huangyibo
 * @Date: 2019/8/15 0:35
 * @Description:
 */

@Component
@Order(Integer.MAX_VALUE)//优先级靠后，会被最后读取出来配置
public class DemoAuthorizeConfigProvider implements AuthorizeConfigProvider {

    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
//        config.antMatchers("/user/*").hasRole("USER");
        config.anyRequest().access("@rbacService.hasPermission(request,authentication)");
//                .and()
//                .csrf().disable();

    }
}
