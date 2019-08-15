package com.yibo.security.app;

import com.yibo.security.app.jwt.YiBoJwtTokenEnhancer;
import com.yibo.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @author: huangyibo
 * @Date: 2019/8/13 18:27
 * @Description:
 *
 * RedisTokenStore配置类，因为要将token存储到redis中
 */

@Configuration
public class TokenStoreConfig {


    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    @ConditionalOnProperty(prefix = "yibo.security.oauth2", name = "storeType", havingValue = "redis")
    //当application.properties配置文件中前缀为yibo.security.oauth2.storeType的属性的值为redis的时候，这个方法里面的配置才会生效
    public TokenStore redisTokenStore(){
        return new RedisTokenStore(redisConnectionFactory);
    }

    @Configuration
    @ConditionalOnProperty(prefix = "yibo.security.oauth2", name = "storeType", havingValue = "jwt", matchIfMissing = true)
    //当application.properties配置文件中前缀为yibo.security.oauth2.storeType的属性的值为jwt的时候，这个类里面的所有的配置都会生效
    //matchIfMissing = true意思为，当配置文件中没有yibo.security.oauth2.storeType的属性的时候，静态类中的配置也生效
    public static class JwtTokenConfig{

        @Autowired
        private SecurityProperties securityProperties;

        @Bean
        public TokenStore jwtTokenStore(){
            return new JwtTokenStore(jwtAccessTokenConverter());
        }

        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter(){
            JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
            jwtAccessTokenConverter.setSigningKey(securityProperties.getOauth2().getJwtSigningKey());
            return jwtAccessTokenConverter;
        }

        /**
         * 增强jwt返回accessToken信息的处理类
         * @return
         */
        @Bean
        @ConditionalOnMissingBean(name = "jwtTokenEnhancer")
        //不同的业务系统可以使用自定义的jwtTokenEnhancer来覆盖掉当前这个jwtTokenEnhancer
        public TokenEnhancer jwtTokenEnhancer(){
            return new YiBoJwtTokenEnhancer();
        }
    }
}
