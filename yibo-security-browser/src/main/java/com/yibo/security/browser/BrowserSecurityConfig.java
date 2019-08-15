package com.yibo.security.browser;

import com.yibo.security.core.authentication.AbstractChannelSecurityConfig;
import com.yibo.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.yibo.security.core.authorize.AuthorizeConfigManager;
import com.yibo.security.core.properties.SecurityProperties;
import com.yibo.security.core.validate.code.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * @author: huangyibo
 * @Date: 2019/7/28 0:33
 * @Description:
 */

@Configuration
public class BrowserSecurityConfig extends AbstractChannelSecurityConfig {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private DataSource dataSource;

    //记住我功能，通过token查找到用户之后用于登录
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Autowired
    private SpringSocialConfigurer yiboSocialSecurityConfig;

    @Autowired
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    @Autowired
    private InvalidSessionStrategy invalidSessionStrategy;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    /**
     * 权限配置管理器
     */
    @Autowired
    private AuthorizeConfigManager authorizeConfigManager;

    /**
     * 对密码加盐加密
     *
     * 这个bean声明到了SecurityCoreConfig里面了
     * @return
     */
   /* @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }*/


    /**
     * 配置记住我功能的tokenRepository
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){

        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        //设置数据源
        tokenRepository.setDataSource(dataSource);
        //在首次启动的时候创建表,后面启动需要注释掉不然会报错
        //tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //浏览器登录页面的配置
        applyPasswordAuthenticationConfig(http);

        http.apply(validateCodeSecurityConfig)//跟校验码相关的配置
                .and()
                .apply(smsCodeAuthenticationSecurityConfig)//短信登录相关的配置
                .and()
                .apply(yiboSocialSecurityConfig)//在当前的过滤器链上添加SocialAuthenticationFilter
                .and()
                .rememberMe()//浏览器特有的记住我功能的配置
                    .tokenRepository(persistentTokenRepository())
                    .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())//记住我选项的过期时间
                    .userDetailsService(userDetailsService)//记住我功能，通过token查找到用户之后用于登录
                .and()
                .sessionManagement()
                    .invalidSessionStrategy(invalidSessionStrategy)
                    //.invalidSessionUrl("/session/invalid")//session过期后跳转的地址，这句代码被封装到了AbstractSessionStrategy中的onSessionInvalid方法了
                    .maximumSessions(securityProperties.getBrowser().getSession().getMaximumSessions())//后面登录的session会将之前登录的session给失效掉
                    //.maxSessionsPreventsLogin(securityProperties.getBrowser().getSession().isMaxSessionsPreventsLogin())//当session达到上面设置的最大值后(当前用户已经有一个登录行为了)，阻止后面的登录行为
                    .expiredSessionStrategy(sessionInformationExpiredStrategy)
                    .and()
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessHandler(logoutSuccessHandler)
                //注意logoutSuccessUrl()和logoutSuccessHandler()只会有一个生效，所以只配置logoutSuccessHandler，在logoutSuccessHandler中去处理
                    //.logoutSuccessUrl("/yibo-logout.html")
                    .deleteCookies("JSESSIONID")//退出的时候删除cookies中的信息
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
                        "/user/regist")
                .permitAll()//访问上面这些url的时候不需要身份认证
                .antMatchers(HttpMethod.GET,"/user/vip/*").hasRole("VIP")//示例：只有拥有vip角色的用户才能访问/user/vip/*这些请求
                .antMatchers(HttpMethod.GET,"/user/*").hasAnyAuthority("VIP")//hasAnyAuthority的权限是绝对匹配，hasRole会在字符串前加一个ROLE_前缀
                .anyRequest()//任何请求
                .authenticated()//都需要身份认证
                .and()*/
                .authorizeRequests()//对请求做授权
                .antMatchers("/user/regist")
                .permitAll()
                .and()
                .csrf().disable();//关闭csrf功能

        authorizeConfigManager.config(http.authorizeRequests());

        /*ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        validateCodeFilter.setAuthenticationFailureHandler(yiBoAuthenticationFailureHandler);
        validateCodeFilter.setSecurityProperties(securityProperties);
        validateCodeFilter.afterPropertiesSet();

        SmsCodeFilter smsCodeFilter = new SmsCodeFilter();
        smsCodeFilter.setAuthenticationFailureHandler(yiBoAuthenticationFailureHandler);
        smsCodeFilter.setSecurityProperties(securityProperties);
        smsCodeFilter.afterPropertiesSet();

        http.addFilterBefore(smsCodeFilter,UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(validateCodeFilter,UsernamePasswordAuthenticationFilter.class)
        .formLogin()        //表单登录
            .loginPage("/authentication/require")
            .loginProcessingUrl("/authentication/form")
            .successHandler(yiBoAuthenticationSuccessHandler)//登录成功后使用自己实现的登录成功处理器
            .failureHandler(yiBoAuthenticationFailureHandler)//登录失败后使用自己实现的登录失败处理器
            .and()
        .rememberMe()   //记住我功能的配置
            .tokenRepository(persistentTokenRepository())
            .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())//记住我选项的过期时间
            .userDetailsService(userDetailsService)//记住我功能，通过token查找到用户之后用于登录
        //http.httpBasic()    //弹出对话框登录
        .and()  //
        .authorizeRequests()//对请求做授权
            .antMatchers("/authentication/require",
                    securityProperties.getBrowser().getLoginPage(),
                    "/code/*").permitAll()   //访问这个url的时候不需要身份认证
            .anyRequest()       //任何请求
            .authenticated()    //都需要身份认证
        .and()
        .csrf().disable()  //关闭csrf功能
        .apply(smsCodeAuthenticationSecurityConfig);//将SmsCodeAuthenticationSecurityConfig的配置添加进来*/
    }
}
