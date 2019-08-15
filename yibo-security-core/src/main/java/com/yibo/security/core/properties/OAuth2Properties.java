package com.yibo.security.core.properties;

/**
 * @author: huangyibo
 * @Date: 2019/8/13 18:06
 * @Description:
 */
public class OAuth2Properties {

    /**
     * jwt的accessToken的密钥，发出去的token用这个签名，验token的时候用这个验签
     */
    private String jwtSigningKey = "yibo";

    private OAuth2ClientProperties[] clients = {};

    public OAuth2ClientProperties[] getClients() {
        return clients;
    }

    public void setClients(OAuth2ClientProperties[] clients) {
        this.clients = clients;
    }

    public String getJwtSigningKey() {
        return jwtSigningKey;
    }

    public void setJwtSigningKey(String jwtSigningKey) {
        this.jwtSigningKey = jwtSigningKey;
    }
}
