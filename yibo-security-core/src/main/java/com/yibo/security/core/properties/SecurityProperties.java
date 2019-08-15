package com.yibo.security.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: huangyibo
 * @Date: 2019/7/28 21:39
 * @Description:
 */

@ConfigurationProperties(prefix = "yibo.security")
//上面注解会读取"yibo.security"开头的配置文件
public class SecurityProperties {

    /**
     * 浏览器特有模式配置
     */
    private BrowserProperties browser = new BrowserProperties();

    /**
     * 验证码(图片验证码，手机验证码)配置
     */
    private ValidateCodeProperties code = new ValidateCodeProperties();

    /**
     * 社交登录配置
     */
    private SocialProperties social = new SocialProperties();

    private OAuth2Properties oauth2 = new OAuth2Properties();

    public BrowserProperties getBrowser() {
        return browser;
    }

    public void setBrowser(BrowserProperties browser) {
        this.browser = browser;
    }

    public ValidateCodeProperties getCode() {
        return code;
    }

    public void setCode(ValidateCodeProperties code) {
        this.code = code;
    }

    public SocialProperties getSocial() {
        return social;
    }

    public void setSocial(SocialProperties social) {
        this.social = social;
    }

    public OAuth2Properties getOauth2() {
        return oauth2;
    }

    public void setOauth2(OAuth2Properties oauth2) {
        this.oauth2 = oauth2;
    }
}
