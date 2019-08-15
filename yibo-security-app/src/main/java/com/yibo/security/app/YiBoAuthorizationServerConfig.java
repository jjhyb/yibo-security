package com.yibo.security.app;

import com.yibo.security.core.properties.OAuth2ClientProperties;
import com.yibo.security.core.properties.SecurityProperties;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: huangyibo
 * @Date: 2019/8/12 17:27
 * @Description:
 */

@Configuration
@EnableAuthorizationServer
public class YiBoAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 注入redisTokenStore
     */
    @Autowired
    private TokenStore tokenStore;

    @Autowired(required = false)
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired(required = false)
    private TokenEnhancer jwtTokenEnhancer;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        endpoints.tokenStore(tokenStore)//将token存储到redis中
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);
        if(jwtAccessTokenConverter != null && jwtTokenEnhancer != null){
            TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
            List<TokenEnhancer> enhancers = new ArrayList<>();
            enhancers.add(jwtTokenEnhancer);
            enhancers.add(jwtAccessTokenConverter);
            tokenEnhancerChain.setTokenEnhancers(enhancers);
            endpoints.tokenEnhancer(tokenEnhancerChain)
                    .accessTokenConverter(jwtAccessTokenConverter);
        }
    }

    /**
     * 跟客户端相关的配置，即认证服务器会给哪些第三方应用(clients)去发令牌
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //目前的场景是在自己的服务，自己的app，自己的前端之间去做通讯，
        // 不会像微信等开放平台允许第三方应用来注册，所以用的内存clients.inMemory()
        // 如果是做开放平台让第三方应用接入，那么使用clients.jdbc()，当然自己公司内部也可以使用clients.jdbc()
        InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
        OAuth2ClientProperties[] oauth2Clients = securityProperties.getOauth2().getClients();
        if(ArrayUtils.isNotEmpty(oauth2Clients)){
            for (OAuth2ClientProperties oauth2Client : oauth2Clients) {
                builder.withClient(oauth2Client.getClientId())
                        .secret(oauth2Client.getClientSecret())
                        .authorizedGrantTypes("refresh_token","password")//针对"yibo"这个应用所支持的授权模式是哪些
                        .accessTokenValiditySeconds(oauth2Client.getAccessTokenValiditySeconds())//发出去的accessToken有效时间是多少，单位为秒
                        .refreshTokenValiditySeconds(2592000)//refreshToken的有效时间，这个时间设置长一点，一个星期或一个月都可以
                        .scopes("all","read","write");
            }
        }

    }
}
