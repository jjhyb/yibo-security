package com.yibo.security.core.properties;

/**
 * @author: huangyibo
 * @Date: 2019/7/29 21:26
 * @Description:
 *
 * 短信验证码配置类
 */
public class SmsCodeProperties {

    private int length = 6;

    private int expireIn = 60;

    private String url;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(int expireIn) {
        this.expireIn = expireIn;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
