package com.yibo.security.core.properties;

/**
 * @author: huangyibo
 * @Date: 2019/8/13 18:06
 * @Description:
 *
 * client的属性，不局限这三个数据，可以按需要进行多配置
 */
public class OAuth2ClientProperties {


    private String clientId;

    private String clientSecret;

    /**
     * accessToken默认有效期为7200秒
     */
    private int accessTokenValiditySeconds = 7200;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public int getAccessTokenValiditySeconds() {
        return accessTokenValiditySeconds;
    }

    public void setAccessTokenValiditySeconds(int accessTokenValiditySeconds) {
        this.accessTokenValiditySeconds = accessTokenValiditySeconds;
    }
}
