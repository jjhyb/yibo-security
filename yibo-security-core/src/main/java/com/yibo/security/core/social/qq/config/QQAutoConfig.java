package com.yibo.security.core.social.qq.config;

import com.yibo.security.core.properties.QQProperties;
import com.yibo.security.core.properties.SecurityProperties;
import com.yibo.security.core.social.qq.connect.QQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;

/**
 * @author: huangyibo
 * @Date: 2019/8/11 16:31
 * @Description:
 */

@Configuration
//当配置文件中yibo.security.social.qq.app-id 有值，这个配置才生效
@ConditionalOnProperty(prefix = "yibo.security.social.qq", name = "app-id")
public class QQAutoConfig extends SocialAutoConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        QQProperties qqConfig = securityProperties.getSocial().getQq();
        return new QQConnectionFactory(qqConfig.getProviderId(),qqConfig.getAppId(),qqConfig.getAppSecret());
    }
}
