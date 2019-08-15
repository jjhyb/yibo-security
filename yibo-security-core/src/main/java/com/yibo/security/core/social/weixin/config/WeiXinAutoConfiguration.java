package com.yibo.security.core.social.weixin.config;

import com.yibo.security.core.properties.SecurityProperties;
import com.yibo.security.core.properties.WeiXinProperties;
import com.yibo.security.core.social.YiBoConnectView;
import com.yibo.security.core.social.weixin.connect.WeiXinConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.web.servlet.View;

/**
 * @author: huangyibo
 * @Date: 2019/8/11 22:53
 * @Description:
 */

@Configuration
@ConditionalOnProperty(prefix = "yibo.security.social.weixin", name = "app-id")
public class WeiXinAutoConfiguration extends SocialAutoConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    /*
     * (non-Javadoc)
     *
     * @see
     * org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter
     * #createConnectionFactory()
     */
    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        WeiXinProperties weixinConfig = securityProperties.getSocial().getWeixin();
        return new WeiXinConnectionFactory(weixinConfig.getProviderId(), weixinConfig.getAppId(),
                weixinConfig.getAppSecret());
    }

    @Bean({"connect/weixinConnect","connect/weixinConnected"})
    @ConditionalOnMissingBean(name="weixinConnectedView")
    public View weixinConnectedView(){
        return new YiBoConnectView();
    }

}
