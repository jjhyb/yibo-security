package com.yibo.security.core.social.qq.connect;

import com.yibo.security.core.social.qq.api.QQ;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * @author: huangyibo
 * @Date: 2019/8/11 15:47
 * @Description:
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {

    public QQConnectionFactory(String providerId, String appId,String appSecret) {
        super(providerId, new QQServiceProvider(appId,appSecret), new QQAdapter());
    }
}
