package com.yibo.security.core.social;

import com.yibo.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * @author: huangyibo
 * @Date: 2019/8/11 15:52
 * @Description:
 */

@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private ConnectionFactoryLocator connectionFactoryLocator;

    @Autowired(required = false)
    private ConnectionSignUp connectionSignUp;

    @Autowired(required = false)
    private SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessor;

    /**
     *
     * @param connectionFactoryLocator 作用为根据条件查找当前应该用哪一个ConnectionFactory
     * @return
     */
    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        //Encryptors.noOpText()，对存进去的数据不做加解密操作
        JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource,connectionFactoryLocator, Encryptors.noOpText());
        //如果表名UserConnection加了前缀变成了yibo_userConnection，通过下面的方法进行设置，我没有改变，不做设置
        //repository.setTablePrefix("yibo_");
        if(connectionSignUp != null){
            repository.setConnectionSignUp(connectionSignUp);
        }
        return repository;
    }

    /**
     * 社交登录配置类
     * @return
     */
    @Bean
    public SpringSocialConfigurer yiboSocialSecurityConfig(){
        String filterProcessesUrl = securityProperties.getSocial().getFilterProcessesUrl();
        YiBoSpringSocialConfigurer configurer = new YiBoSpringSocialConfigurer(filterProcessesUrl);
        configurer.signupUrl(securityProperties.getBrowser().getSignUpUrl());
        configurer.setSocialAuthenticationFilterPostProcessor(socialAuthenticationFilterPostProcessor);
        return configurer;
    }

    /**
     * 处理注册流程的工具类
     * @return
     */
    @Bean
    public ProviderSignInUtils providerSignInUtils(){
        return new ProviderSignInUtils(connectionFactoryLocator,getUsersConnectionRepository(connectionFactoryLocator));
    }

}
