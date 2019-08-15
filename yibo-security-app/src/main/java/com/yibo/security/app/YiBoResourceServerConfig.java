package com.yibo.security.app;

import com.yibo.security.app.social.openid.OpenIdAuthenticationSecurityConfig;
import com.yibo.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.yibo.security.core.authorize.AuthorizeConfigManager;
import com.yibo.security.core.properties.SecurityConstants;
import com.yibo.security.core.properties.SecurityProperties;
import com.yibo.security.core.validate.code.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * @author: huangyibo
 * @Date: 2019/8/12 18:47
 * @Description:
 *
 * app资源安全配置
 */

@Configuration
@EnableResourceServer
public class YiBoResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    protected AuthenticationSuccessHandler yiBoAuthenticationSuccessHandler;

    @Autowired
    protected AuthenticationFailureHandler yiBoAuthenticationFailureHandler;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private SpringSocialConfigurer yiboSocialSecurityConfig;

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private OpenIdAuthenticationSecurityConfig openIdAuthenticationSecurityConfig;

    /**
     * 权限配置管理器
     */
    @Autowired
    private AuthorizeConfigManager authorizeConfigManager;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //浏览器登录页面的配置
        http.formLogin()
                .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
                .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
                .successHandler(yiBoAuthenticationSuccessHandler)//登录成功后使用自己实现的登录成功处理器
                .failureHandler(yiBoAuthenticationFailureHandler);//登录失败后使用自己实现的登录失败处理器

        http.apply(validateCodeSecurityConfig)//跟校验码相关的配置
                .and()
                .apply(smsCodeAuthenticationSecurityConfig)//短信登录相关的配置
                .and()
                .apply(yiboSocialSecurityConfig)//在当前的过滤器链上添加SocialAuthenticationFilter
                .and()
                .apply(openIdAuthenticationSecurityConfig)
                .and()
                //下面这段注释掉的代码就被提取到了authorizeConfigManager中的方法中去了
                /*.authorizeRequests()//对请求做授权，浏览器特有的配置
                .antMatchers(
                        SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                        SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
                        securityProperties.getBrowser().getLoginPage(),
                        SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX+"/*",
                        securityProperties.getBrowser().getSignUpUrl(),
                        securityProperties.getBrowser().getSignOutUrl(),
                        securityProperties.getBrowser().getSession().getSessionInvalidUrl(),
                        "/user/regist","/social/signUp")
                .permitAll()//访问上面这些url的时候不需要身份认证
                .anyRequest()//任何请求
                .authenticated()//都需要身份认证
                .and()*/
                .authorizeRequests()//对请求做授权
                .antMatchers("/user/regist","/social/signUp")
                .permitAll()
                .and()
                .csrf().disable();//关闭csrf功能

        authorizeConfigManager.config(http.authorizeRequests());
    }
}
