package com.yibo.security.core.properties;

import org.springframework.boot.autoconfigure.social.SocialProperties;

/**
 * @author: huangyibo
 * @Date: 2019/8/11 16:24
 * @Description:
 */
public class QQProperties extends SocialProperties {

    /**
     * 服务提供商的一个标识
     */
    private String providerId = "qq";

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
}
